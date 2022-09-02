package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.App;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.util.Pair;

public interface ReCartesian<P extends K2, Mu extends ReCartesian.Mu> extends Profunctor<P, Mu> {
    static <P extends K2, Proof extends ReCartesian.Mu> ReCartesian<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (ReCartesian<P, Proof>) proofBox;
    }

    interface Mu extends Profunctor.Mu {}

    <A, B, C> App2<P, A, B> unfirst(final App2<P, Pair<A, C>, Pair<B, C>> input);

    <A, B, C> App2<P, A, B> unsecond(final App2<P, Pair<C, A>, Pair<C, B>> input);
}