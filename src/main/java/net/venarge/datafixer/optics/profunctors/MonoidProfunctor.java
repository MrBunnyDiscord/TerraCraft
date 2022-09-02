package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.optics.Procompose;

import java.util.function.Supplier;

public interface MonoidProfunctor<P extends K2, Mu extends MonoidProfunctor.Mu> extends Profunctor<P, Mu> {
    interface Mu extends Profunctor.Mu {}

    <A, B> App2<P, A, B> zero(final App2<FunctionType.Mu, A, B> func);

    <A, B> App2<P, A, B> plus(final App2<Procompose.Mu<P, P>, A, B> input);

    default <A, B, C> App2<P, A, C> compose(final App2<P, B, C> first, final Supplier<App2<P, A, B>> second) {
        return plus(new Procompose<> (second, first));
    }
}

