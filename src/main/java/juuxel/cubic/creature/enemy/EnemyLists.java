package juuxel.cubic.creature.enemy;

import juuxel.cubic.util.Creator;

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

        normalEnemies.add(EnemyNormal::new);
        strangeEnemies.add(EnemyBouncing::new);

        ENEMY_LISTS.put(EnemyType.NORMAL, normalEnemies);
        ENEMY_LISTS.put(EnemyType.STRANGE, strangeEnemies);
    }

    public static void registerEnemy(EnemyType type, Creator<Enemy> creator)
    {
        ENEMY_LISTS.get(type).add(creator);
    }

    public static Enemy createEnemy(EnemyType type)
    {
        List<Creator<Enemy>> list = ENEMY_LISTS.get(type);

        return list.get(RANDOM.nextInt(list.size())).create();
    }
}
