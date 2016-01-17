package juuxel.cubic.enemy;

import juuxel.cubic.util.Creator;

import java.util.*;
import java.util.List;

public final class EnemyLists
{
    private EnemyLists()
    {}

    private static final Map<EnemyType, List<Creator<AbstractEnemy>>> ENEMY_LISTS = new HashMap<>();
    private static final Random RANDOM = new Random();

    public static void initializeLists()
    {
        List<Creator<AbstractEnemy>> normalEnemies = new ArrayList<>();
        List<Creator<AbstractEnemy>> strangeEnemies = new ArrayList<>();

        normalEnemies.add(NormalEnemy::new);
        strangeEnemies.add(BouncingEnemy::new);
        strangeEnemies.add(BlockEnemy::new);

        ENEMY_LISTS.put(EnemyType.NORMAL, normalEnemies);
        ENEMY_LISTS.put(EnemyType.STRANGE, strangeEnemies);
    }

    public static AbstractEnemy createEnemy(EnemyType type)
    {
        List<Creator<AbstractEnemy>> list = ENEMY_LISTS.get(type);

        return list.get(RANDOM.nextInt(list.size())).create();
    }
}
