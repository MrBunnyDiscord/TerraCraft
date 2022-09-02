package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.App;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.serialization.Either;

public interface ReCocartesian<P extends K2, Mu extends ReCocartesian.Mu> extends Profunctor<P, Mu> {
    static <P extends K2, Proof extends ReCocartesian.Mu> ReCocartesian<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (ReCocartesian<P, Proof>) proofBox;
    }

    interface Mu extends Profunctor.Mu {}

    <A, B, C> App2<P, A, B> unleft(final App2<P, Either<A, C>, Either<B, C>> input);

    <A, B, C> App2<P, A, B> unright(final App2<P, Either<C, A>, Either<C, B>> input);
}
