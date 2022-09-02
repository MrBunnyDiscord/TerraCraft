package net.venarge.serialization.key;

import net.venarge.serialization.DynamicOps;

public interface Compressable extends Keyable {
    <T> KeyCompressor<T> compressor(final DynamicOps<T> ops);
}

