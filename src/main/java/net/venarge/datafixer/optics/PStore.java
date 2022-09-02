package net.venarge.datafixer.optics;

import net.venarge.datafixer.App;
import net.venarge.datafixer.K1;
import net.venarge.datafixer.kinds.Functor;

import java.util.function.Function;

interface PStore<I, J, X> extends App<PStore.Mu<I, J>, X> {
    final class Mu<I, J> implements K1 {}

    static <I, J, X> PStore<I, J, X> unbox(final App<Mu<I, J>, X> box) {
        return (PStore<I, J, X>) box;
    }

    X peek(final J j);

    I pos();

    final class Instance<I, J> implements Functor<Mu<I, J>, Instance.Mu<I, J>> {
        public static final class Mu<I, J> implements Functor.Mu {}

        @Override
        public <T, R> App<PStore.Mu<I, J>, R> map(final Function<? super T, ? extends R> func, final App<PStore.Mu<I, J>, T> ts) {
            final PStore<I, J, T> input = PStore.unbox(ts);
            return Optics.pStore(func.compose(input::peek)::apply, input::pos);
        }
    }
}

