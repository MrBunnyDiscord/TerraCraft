package net.venarge.datafixer.optics;

import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.optics.profunctors.Profunctor;

import java.util.function.Function;

public interface Adapter<S, T, A, B> extends App2<Adapter.Mu<A, B>, S, T>, Optic<Profunctor.Mu, S, T, A, B> {
    final class Mu<A, B> implements K2 {}

    static <S, T, A, B> Adapter<S, T, A, B> unbox(final App2<Mu<A, B>, S, T> box) {
        return (Adapter<S, T, A, B>) box;
    }

    A from(final S s);

    T to(final B b);

    @Override
    default <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(final App<? extends Profunctor.Mu, P> proofBox) {
        final Profunctor<P, ? extends Profunctor.Mu> proof = Profunctor.unbox(proofBox);
        return a -> proof.dimap(
                a,
                this::from,
                this::to
        );
    }

    final class Instance<A2, B2> implements Profunctor<Mu<A2, B2>, Profunctor.Mu> {
        @Override
        public <A, B, C, D> FunctionType<App2<Adapter.Mu<A2, B2>, A, B>, App2<Adapter.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g, final Function<B, D> h) {
            return a -> Optics.adapter(
                    c -> Adapter.unbox(a).from(g.apply(c)),
                    b2 -> h.apply(Adapter.unbox(a).to(b2))
            );
        }
    }
}
