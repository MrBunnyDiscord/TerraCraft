package net.venarge.datafixer.types.templates;

import net.venarge.datafixer.optics.FamilyOptic;
import net.venarge.datafixer.optics.OpticParts;
import net.venarge.datafixer.types.Type;

import java.util.function.IntFunction;

public interface TypeFamily {
    Type<?> apply(final int index);

    static <A, B> FamilyOptic<A, B> familyOptic(final IntFunction<OpticParts<A, B>> optics) {
        return optics::apply;
    }
}
