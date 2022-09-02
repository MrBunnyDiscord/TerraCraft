package net.venarge.terracraft.util.side;

import java.lang.annotation.*;

@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Repeatable (OnlyIns.class)
public @interface OnlyIn {
    Dist value();

    Class<?> _interface() default Object.class;
}
