package net.venarge.datafixer.optics;

import net.venarge.datafixer.util.Pair;

public final class Proj1<F, G, F2> implements Lens<Pair<F, G>, Pair<F2, G>, F, F2> {
    @Override
    public F view(final Pair<F, G> pair) {
        return pair.getFirst();
    }

    @Override
    public Pair<F2, G> update(final F2 newValue, final Pair<F, G> pair) {
        return Pair.of(newValue, pair.getSecond());
    }

    @Override
    public String toString() {
        return "π1";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Proj1<?, ?, ?>;
    }
}
