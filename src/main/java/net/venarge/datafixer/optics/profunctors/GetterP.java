package net.venarge.datafixer.optics.profunctors;

import net.venarge.datafixer.App;
import net.venarge.datafixer.kinds.App2;
import net.venarge.datafixer.kinds.K2;

import java.util.function.Function;

public interface GetterP<P extends K2, Mu extends GetterP.Mu> extends Profunctor<P, Mu>, Bicontravariant<P, Mu> {
    static <P extends K2, Proof extends GetterP.Mu> GetterP<P, Proof> unbox(final App<Proof, P> proofBox) {
        return (GetterP<P, Proof>) proofBox;
    }

    interface Mu extends Profunctor.Mu, Bicontravariant.Mu {}

    default <A, B, C> App2<P, C, A> secondPhantom(final App2<P, C, B> input) {
        return cimap(() -> rmap(input, b -> (Void) null), Function.identity(), a -> null);
    }
}

