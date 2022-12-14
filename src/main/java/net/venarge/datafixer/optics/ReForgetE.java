package net.venarge.datafixer.optics;

import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.optics.profunctors.AffineP;
import net.venarge.datafixer.optics.profunctors.Cocartesian;
import net.venarge.datafixer.util.Pair;
import net.venarge.serialization.Either;

import java.util.function.Function;

interface ReForgetE<R, A, B> extends App2<ReForgetE.Mu<R>, A, B> {
    final class Mu<R> implements K2 {}

    static <R, A, B> ReForgetE<R, A, B> unbox(final App2<Mu<R>, A, B> box) {
        return (ReForgetE<R, A, B>) box;
    }

    B run(final Either<A, R> r);

    final class Instance<R> implements Cocartesian<Mu<R>, Instance.Mu<R>>, App<Instance.Mu<R>, Mu<R>> {
        static final class Mu<R> implements Cocartesian.Mu {}

        @Override
        public <A, B, C, D> FunctionType<App2<ReForgetE.Mu<R>, A, B>, App2<ReForgetE.Mu<R>, C, D>> dimap(final Function<C, A> g, final Function<B, D> h) {
            return input -> Optics.reForgetE("dimap", e -> {
                final Either<A, R> either = e.mapLeft(g);
                final B b = ReForgetE.unbox(input).run(either);
                final D d = h.apply(b);
                return d;
            });
        }

        @Override
        public <A, B, C> App2<ReForgetE.Mu<R>, Either<A, C>, Either<B, C>> left(final App2<ReForgetE.Mu<R>, A, B> input) {
            final ReForgetE<R, A, B> reForgetE = ReForgetE.unbox(input);
            return Optics.reForgetE("left",
                    e -> e.map(
                            e2 -> e2.map(
                                    a -> Either.left(reForgetE.run(Either.left(a))),
                                    Either::right
                            ),
                            r -> Either.left(reForgetE.run(Either.right(r)))
                    )
            );
        }

        @Override
        public <A, B, C> App2<ReForgetE.Mu<R>, Either<C, A>, Either<C, B>> right(final App2<ReForgetE.Mu<R>, A, B> input) {
            final ReForgetE<R, A, B> reForgetE = ReForgetE.unbox(input);
            return Optics.reForgetE("right",
                    e -> e.map(
                            e2 -> e2.map(
                                    Either::left,
                                    a -> Either.right(reForgetE.run(Either.left(a)))
                            ),
                            r -> Either.right(reForgetE.run(Either.right(r)))
                    )
            );
        }
    }
}
