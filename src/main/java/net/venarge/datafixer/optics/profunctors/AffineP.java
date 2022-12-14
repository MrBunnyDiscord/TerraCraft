package net.venarge.datafixer.optics.profunctors;

import com.google.common.reflect.TypeToken;
import net.venarge.datafixer.kinds.K2;

public interface AffineP<P extends K2, Mu extends AffineP.Mu> extends Cartesian<P, Mu>, Cocartesian<P, Mu> {
    interface Mu extends Cartesian.Mu, Cocartesian.Mu {
        TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>() {};
    }
}
