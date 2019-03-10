/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util.tuple;

public final class Tuple4<A, B, C, D>
{
    public final A first;
    public final B second;
    public final C third;
    public final D fourth;

    public Tuple4(A first, B second, C third, D fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }
}
