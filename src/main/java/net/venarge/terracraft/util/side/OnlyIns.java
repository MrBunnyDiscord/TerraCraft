package net.venarge.terracraft.util.side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE})
public @interface OnlyIns {
    OnlyIn[] value();
}
