/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.mod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines metadata for mods.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModMeta
{
    /**
     * The ID of this mod.
     *
     * @return the ID
     */
    String id();

    /**
     * The author of this mod.
     * Defaults to an empty string.
     *
     * @return the author
     */
    String author() default "";

    /**
     * The version of this mod.
     * Defaults to "1.0.0".
     *
     * @return the version
     */
    String version() default "1.0.0";
}
