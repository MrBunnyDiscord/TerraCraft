package net.venarge.serialization.codecs;

import net.venarge.datafixer.util.Pair;
import net.venarge.serialization.Codec;
import net.venarge.serialization.DataResult;
import net.venarge.serialization.DynamicOps;

public interface PrimitiveCodec<A> extends Codec<A> {
    <T> DataResult<A> read(final DynamicOps<T> ops, final T input);

    <T> T write(final DynamicOps<T> ops, final A value);

    @Override
    default <T> DataResult<Pair<A, T>> decode(final DynamicOps<T> ops, final T input) {
        return read(ops, input).map(r -> Pair.of(r, ops.empty()));
    }

    @Override
    default <T> DataResult<T> encode(final A input, final DynamicOps<T> ops, final T prefix) {
        return ops.mergeToPrimitive(prefix, write(ops, input));
    }
}
