package net.venarge.terracraft.start;

import net.venarge.terracraft.util.jarhandling.JarModuleFinder;
import net.venarge.terracraft.util.jarhandling.ModuleClassLoader;
import net.venarge.terracraft.util.jarhandling.SecureJar;
import net.venarge.terracraft.util.side.Dist;
import net.venarge.terracraft.util.side.OnlyIn;
import net.venarge.terracraft.util.windows.IWindowEventListener;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class RunGame {
    private static final boolean DEBUG = System.getProperties ().containsKey ("bsl.debug");

    @SuppressWarnings ("unchecked")
    public static void main(String... args) {
        var legacyClasshpath = loadLegacyClasspath();
        System.getProperty ("legacyClassPath",String.join (File.pathSeparator, legacyClasshpath));

        var ignoreList = System.getProperty ("ignoreList","asm,securejarhandler");
        var ignores = ignoreList.split (",");
        var previousPackages = new HashSet<String> (  );
        var jars = new ArrayList<SecureJar> (  );
        var pathLookup = new HashMap<Path, String> (  );
        var filenameMap = getMergeFilenameMap ();
        var mergeMap = new LinkedHashMap<String, List<Path>> (  );
        var order = new ArrayList<String> (  );

        outer:
        for (var legacy : legacyClasshpath){
            var path = Paths.get (legacy);
            var filename = path.getFileName ().toString ();

            for(var filter : ignores){
                if(filename.startsWith (filter)) {
                    if(DEBUG) {
                        System.out.println ( "bsl: file '" + legacy + "' ignored because filename starts with '" + filter +"'");
                    }
                    continue outer;
                }
            }

            if(DEBUG) {
                System.out.println ( "bsl: encountered path '" + legacy + "'" );
            }

            if(Files.notExists (path)) continue;

            var jar = SecureJar.from (path);
            if("".equals (jar.name ())) continue;

            var jarname = pathLookup.computeIfAbsent (path,k -> filenameMap.getOrDefault (filename,jar.name ()));
            order.add (jarname);
            mergeMap.computeIfAbsent (jarname, k -> new ArrayList<> (  )).add (path);
        }

        mergeMap.entrySet ().stream ().sorted (Comparator.comparing(e->order.indexOf (e.getKey ()))).forEach (e->{
            var name = e.getKey ();
            var paths = e.getValue ();
            if(paths.size () == 1 && Files.notExists (paths.get (0))) return;
            var pathsArray = paths.toArray (Path[]::new);
            var jar = SecureJar.from (new PackageTracker(Set.copyOf (previousPackages), pathsArray),pathsArray);
            var packages = jar.getPackages ();

            if(DEBUG) {
                System.out.println ("bsl: the following paths are merged together in module " + name);
                paths.forEach (path-> System.out.println ( "bsl:       " + path ));
                System.out.println ("bsl: list of packages for module " + name );
                packages.forEach (p -> System.out.println ( "bsl: " + p ));
            }

            previousPackages.addAll (packages);
            jars.add (jar);
        });

        var secureJarsArray = jars.toArray (SecureJar[]::new);

        var allTargets = Arrays.stream (secureJarsArray).map (SecureJar::name).toList ();
        var jarModuleFinder = JarModuleFinder.of (secureJarsArray);
        var bootModuleConfiguration = ModuleLayer.boot ().configuration ();
        var bootstrapConfigurations = bootModuleConfiguration.resolveAndBind (jarModuleFinder, ModuleFinder.ofSystem ( ),allTargets);
        var moduleClassLoader = new ModuleClassLoader ("TC-BOOTSTRAP", bootstrapConfigurations,List.of ( ModuleLayer.boot () ));
        var layer = ModuleLayer.defineModules (bootstrapConfigurations, List.of ( ModuleLayer.boot ()),m -> moduleClassLoader);

        Thread.currentThread ().setContextClassLoader (moduleClassLoader);

        final var loader = ServiceLoader.load (layer.layer (), Consumer.class);

        ((Consumer<String[]>) loader.stream ().findFirst ().orElseThrow ().get ()).accept (args);
    }



    private static Map<String, String> getMergeFilenameMap() {
        var mergeModules = System.getProperty ("mergeModules");

        if(mergeModules == null)
            return Map.of ();

        Map<String,String> filenamemap = new HashMap<> (  );
        int i = 0;

        for(var merge : mergeModules.split (";")) {
            var targets = merge.split (";");
            for(String target : targets) {
                filenamemap.put (target, String.valueOf (i));
            }
            i++;
        }

        return filenamemap;
    }

    private record PackageTracker(Set<String> packages, Path... paths) implements BiPredicate<String, String> {
        @Override
        public boolean test(String s, String s2) {
            if(packages.isEmpty () || s2.startsWith ("META-INF/"))
                return true;

            int idx = s2.lastIndexOf ("/");
            return idx < 0 || idx == s2.length () -1 || !packages.contains (s2.substring (0, idx).replace ("/","."));
        }
    }

    private static List<String> loadLegacyClasspath(){
        var legacyCpPath = System.getProperty ("legacyClassPath.file");

        if (legacyCpPath != null) {
            var legacyCPFileCandidatePath = Paths.get(legacyCpPath);
            if (Files.exists(legacyCPFileCandidatePath) && Files.isRegularFile(legacyCPFileCandidatePath)) {
                try {
                    return Files.readAllLines(legacyCPFileCandidatePath);
                }
                catch (IOException e) {
                    throw new IllegalStateException("Failed to load the legacy class path from the specified file: " + legacyCpPath, e);
                }
            }
        }

        var legacyClasshPath = System.getProperty ("legacyClassPath",System.getProperty ("java.class.path"));
        Objects.requireNonNull (legacyClasshPath,"Missing LegacyClasspath, cannot bootstrap");
        if(legacyClasshPath.length () == 0){
            return List.of ();
        } else {
            return Arrays.asList (legacyClasshPath.split (File.pathSeparator) );
        }
    }
}
