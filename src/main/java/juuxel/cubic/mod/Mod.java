package juuxel.cubic.mod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines metadata for mods.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Mod
{
    String id();
    String name() default "";
    String author() default "";
    String version() default "1.0.0";
}
