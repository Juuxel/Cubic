/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util.tuple;

public final class Tuple3<A, B, C>
{
    public final A first;
    public final B second;
    public final C third;

    public Tuple3(A first, B second, C third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
