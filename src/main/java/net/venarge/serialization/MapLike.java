package net.venarge.serialization;

import net.venarge.datafixer.util.Pair;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

public interface MapLike<T> {
    @Nullable
    T get(final T key);

    @Nullable
    T get(final String key);

    Stream<Pair<T, T>> entries();

    static <T> MapLike<T> forMap(final Map<T, T> map, final DynamicOps<T> ops) {
        return new MapLike<T>() {
            @Nullable
            @Override
            public T get(final T key) {
                return map.get(key);
            }

            @Nullable
            @Override
            public T get(final String key) {
                return get(ops.createString(key));
            }

            @Override
            public Stream<Pair<T, T>> entries() {
                return map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue()));
            }

            @Override
            public String toString() {
                return "MapLike[" + map + "]";
            }
        };
    }
}
