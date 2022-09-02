package net.venarge.datafixer.optics;

public interface FamilyOptic<A, B> {
    OpticParts<A, B> apply(final int index);
}
