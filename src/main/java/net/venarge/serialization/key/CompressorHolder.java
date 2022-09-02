package net.venarge.serialization.key;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.venarge.serialization.DynamicOps;

import java.util.Map;

public abstract class CompressorHolder implements Compressable {
    private final Map<DynamicOps<?>, KeyCompressor<?>> compressors = new Object2ObjectArrayMap<> ();

    @SuppressWarnings("unchecked")
    @Override
    public <T> KeyCompressor<T> compressor(final DynamicOps<T> ops) {
        return (KeyCompressor<T>) compressors.computeIfAbsent(ops, k -> new KeyCompressor<>(ops, keys(ops)));
    }
}
