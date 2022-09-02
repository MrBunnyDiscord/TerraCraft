package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.App;
import net.venarge.datafixer.K1;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.Functor;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.optics.TraversalP;

public interface Mapping<P extends K2, Mu extends Mapping.Mu> extends TraversalP<P, Mu> {
    static <P extends K2, Proof extends Mapping.Mu> Mapping<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (Mapping<P, Proof>) proofBox;
    }

    interface Mu extends TraversalP.Mu {}

    <A, B, F extends K1> App2<P, App<F, A>, App<F, B>> mapping(final Functor<F, ?> functor, final App2<P, A, B> input);
}
