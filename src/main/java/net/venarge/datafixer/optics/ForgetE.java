package net.venarge.datafixer.optics;

import net.venarge.datafixer.App;
import net.venarge.datafixer.FunctionType;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;
import net.venarge.datafixer.optics.profunctors.AffineP;
import net.venarge.datafixer.util.Pair;
import net.venarge.serialization.Either;

import java.util.function.Function;

interface ForgetE<R, A, B> extends App2<ForgetE.Mu<R>, A, B> {
    final class Mu<R> implements K2 {}

    static <R, A, B> ForgetE<R, A, B> unbox(final App2<Mu<R>, A, B> box) {
        return (ForgetE<R, A, B>) box;
    }

    Either<B, R> run(final A a);

    final class Instance<R> implements AffineP<Mu<R>, Instance.Mu<R>>, App<Instance.Mu<R>, Mu<R>> {
        static final class Mu<R> implements AffineP.Mu {}

        @Override
        public <A, B, C, D> FunctionType<App2<ForgetE.Mu<R>, A, B>, App2<ForgetE.Mu<R>, C, D>> dimap(final Function<C, A> g, final Function<B, D> h) {
            return input -> Optics.forgetE(c -> ForgetE.unbox(input).run(g.apply(c)).mapLeft(h));
        }

        @Override
        public <A, B, C> App2<ForgetE.Mu<R>, Pair<A, C>, Pair<B, C>> first(final App2<ForgetE.Mu<R>, A, B> input) {
            return Optics.forgetE(p -> ForgetE.unbox(input).run(p.getFirst()).mapLeft(b -> Pair.of(b, p.getSecond())));
        }

        @Override
        public <A, B, C> App2<ForgetE.Mu<R>, Pair<C, A>, Pair<C, B>> second(final App2<ForgetE.Mu<R>, A, B> input) {
            return Optics.forgetE(p -> ForgetE.unbox(input).run(p.getSecond()).mapLeft(b -> Pair.of(p.getFirst(), b)));
        }

        @Override
        public <A, B, C> App2<ForgetE.Mu<R>, Either<A, C>, Either<B, C>> left(final App2<ForgetE.Mu<R>, A, B> input) {
            return Optics.forgetE(e -> e.map(l -> ForgetE.unbox(input).run(l).mapLeft(Either::left), r -> Either.left(Either.right(r))));
        }

        @Override
        public <A, B, C> App2<ForgetE.Mu<R>, Either<C, A>, Either<C, B>> right(final App2<ForgetE.Mu<R>, A, B> input) {
            return Optics.forgetE(e -> e.map(l -> Either.left(Either.left(l)), r -> ForgetE.unbox(input).run(r).mapLeft(Either::right)));
        }
    }
}
