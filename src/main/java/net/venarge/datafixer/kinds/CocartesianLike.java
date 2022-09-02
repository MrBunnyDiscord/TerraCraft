package net.venarge.datafixer.kinds;

import net.venarge.datafixer.App;
import net.venarge.datafixer.K1;
import net.venarge.serialization.Either;

import java.util.function.Function;

public interface CocartesianLike<T extends K1, C, Mu extends CocartesianLike.Mu> extends Functor<T, Mu>, Traversable<T, Mu> {
    static <F extends K1, C, Mu extends CocartesianLike.Mu> CocartesianLike<F, C, Mu> unbox(final App<Mu, F> proofBox) {
        return (CocartesianLike<F, C, Mu>) proofBox;
    }

    interface Mu extends Functor.Mu, Traversable.Mu {}

    <A> App<Either.Mu<C>, A> to(final App<T, A> input);

    <A> App<T, A> from(final App<Either.Mu<C>, A> input);

    @Override
    default <F extends K1, A, B> App<F, App<T, B>> traverse(final Applicative<F, ?> applicative, final Function<A, App<F, B>> function, final App<T, A> input) {
        return applicative.map(this::from, new Either.Instance<C>().traverse(applicative, function, to(input)));
    }
}
