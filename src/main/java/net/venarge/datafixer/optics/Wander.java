package net.venarge.datafixer.optics;

import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.K1;
import net.venarge.datafixer.kinds.Applicative;

public interface Wander<S, T, A, B> {
    <F extends K1> FunctionType<S, App<F, T>> wander(final Applicative<F, ?> applicative, final FunctionType<A, App<F, B>> input);
}