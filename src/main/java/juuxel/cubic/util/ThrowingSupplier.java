/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

@FunctionalInterface
public interface ThrowingSupplier<T>
{
    T get() throws Exception;

    static ThrowingSupplier<Void> of(ThrowingRunnable runnable) {
        return () -> {
            runnable.run();
            return null;
        };
    }
}
