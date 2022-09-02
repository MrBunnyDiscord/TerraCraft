package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.kinds.Kind2;

import java.util.function.Function;
import java.util.function.Supplier;

interface Bicontravariant<P extends K2, Mu extends Bicontravariant.Mu> extends Kind2<P, Mu> {
    static <P extends K2, Proof extends Bicontravariant.Mu> Bicontravariant<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (Bicontravariant<P, Proof>) proofBox;
    }

    interface Mu extends Kind2.Mu {}

    <A, B, C, D> FunctionType<Supplier<App2<P, A, B>>, App2<P, C, D>> cimap(final Function<C, A> g, final Function<D, B> h);

    default <A, B, C, D> App2<P, C, D> cimap(final Supplier<App2<P, A, B>> arg, final Function<C, A> g, final Function<D, B> h) {
        return cimap(g, h).apply(arg);
    }
}
