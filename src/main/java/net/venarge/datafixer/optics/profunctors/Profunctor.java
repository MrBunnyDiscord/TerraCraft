package net.venarge.datafixer.optics.profunctors;

import com.google.gson.reflect.TypeToken;
import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.kinds.Kind2;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Profunctor<P extends K2, Mu extends Profunctor.Mu> extends Kind2<P, Mu> {
    interface Mu extends Kind2.Mu {
        TypeToken<Mu> TYPE_TOKEN = new TypeToken<Mu>() {};
    }

    static <P extends K2, Proof extends Profunctor.Mu> Profunctor<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (Profunctor<P, Proof>) proofBox;
    }

    <A, B, C, D> FunctionType<App2<P, A, B>, App2<P, C, D>> dimap(final Function<C, A> g, final Function<B, D> h);

    //<A, B, C, D> FunctionType<App2<P, A, B>, App2<P, C, D>> dimap(final Function<C, A> g, final Function<B, D> h);

    default <A, B, C, D> App2<P, C, D> dimap(final App2<P, A, B> arg, final Function<C, A> g, final Function<B, D> h) {
        return dimap(g, h).apply(arg);
    }

    default <A, B, C, D> App2<P, C, D> dimap(final Supplier<App2<P, A, B>> arg, final Function<C, A> g, final Function<B, D> h) {
        return dimap(g, h).apply(arg.get());
    }

    default <A, B, C> App2<P, C, B> lmap(final App2<P, A, B> input, final Function<C, A> g) {
        return dimap(input, g, Function.identity());
    }

    default <A, B, D> App2<P, A, D> rmap(final App2<P, A, B> input, final Function<B, D> h) {
        return dimap(input, Function.identity(), h);
    }
}
