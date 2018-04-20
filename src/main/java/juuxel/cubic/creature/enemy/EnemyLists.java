/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import java.util.*;
import java.util.function.Supplier;

public final class EnemyLists
{
    private EnemyLists()
    {}

    private static final Map<EnemyType, List<Supplier<Enemy>>> ENEMY_LISTS = new HashMap<>();
    private static final Random RANDOM = new Random();

    public static void initLists()
    {
        List<Supplier<Enemy>> normalEnemies = new ArrayList<>();
        List<Supplier<Enemy>> strangeEnemies = new ArrayList<>();

        normalEnemies.add(EnemyNormal::new);
        normalEnemies.add(EnemyBird::new);
        strangeEnemies.add(EnemyBouncing::new);
        strangeEnemies.add(EnemyBird::new);

        ENEMY_LISTS.put(EnemyType.NORMAL, normalEnemies);
        ENEMY_LISTS.put(EnemyType.STRANGE, strangeEnemies);
    }

    public static void registerEnemy(EnemyType type, Supplier<Enemy> supplier)
    {
        ENEMY_LISTS.get(type).add(supplier);
    }

    public static Enemy createEnemy(EnemyType type)
    {
        List<Supplier<Enemy>> list = ENEMY_LISTS.get(type);

        return list.get(RANDOM.nextInt(list.size())).get();
    }
}
