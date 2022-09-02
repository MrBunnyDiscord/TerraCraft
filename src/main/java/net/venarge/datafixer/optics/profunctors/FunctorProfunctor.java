package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.App;
import net.venarge.datafixer.K1;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.kinds.Kind2;

public interface FunctorProfunctor<T extends K1, P extends K2, Mu extends FunctorProfunctor.Mu<T>> extends Kind2<P, Mu> {
    static <T extends K1, P extends K2, Mu extends FunctorProfunctor.Mu<T>> FunctorProfunctor<T, P, Mu> unbox(final App<Mu, P> proofBox) {
        return (FunctorProfunctor<T, P, Mu>) proofBox;
    }

    interface Mu<T extends K1> extends Kind2.Mu {}

    <A, B, F extends K1> App2<P, App<F, A>, App<F, B>> distribute(final App<? extends T, F> proof, final App2<P, A, B> input);
}
