package net.venarge.datafixer.optics.profunctors;

import com.google.common.reflect.TypeToken;
import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;

public interface Closed<P extends K2, Mu extends Closed.Mu> extends Profunctor<P, Mu> {
    static <P extends K2, Proof extends Closed.Mu> Closed<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (Closed<P, Proof>) proofBox;
    }

    interface Mu extends Profunctor.Mu {
        TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>() {};
    }

    <A, B, X> App2<P, FunctionType<X, A>, FunctionType<X, B>> closed(final App2<P, A, B> input);
}

