package juuxel.cubic.enemy;

import juuxel.cubic.reference.Creator;

import java.util.*;
import java.util.List;

public final class EnemyLists
{
    private EnemyLists()
    {}

    private static final Map<EnemyType, List<Creator<Enemy>>> ENEMY_LISTS = new HashMap<>();
    private static final Random RANDOM = new Random();

    public static void initializeLists()
    {
        List<Creator<Enemy>> normalEnemies = new ArrayList<>();
        List<Creator<Enemy>> strangeEnemies = new ArrayList<>();

        normalEnemies.add(NormalEnemy::new);
        strangeEnemies.add(BouncingEnemy::new);

        ENEMY_LISTS.put(EnemyType.NORMAL, normalEnemies);
        ENEMY_LISTS.put(EnemyType.STRANGE, strangeEnemies);
    }

    public static Enemy createEnemy(EnemyType type)
    {
        List<Creator<Enemy>> list = ENEMY_LISTS.get(type);

        return list.get(RANDOM.nextInt(list.size())).create();
    }
}
