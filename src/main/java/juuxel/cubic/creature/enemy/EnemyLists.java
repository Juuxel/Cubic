package juuxel.cubic.creature.enemy;

import juuxel.cubic.util.Creator;

import java.util.*;
import java.util.List;

public final class EnemyLists
{
    private EnemyLists()
    {}

    private static final Map<EnemyType, List<Creator<CreatureEnemy>>> ENEMY_LISTS = new HashMap<>();
    private static final Random RANDOM = new Random();

    public static void initializeLists()
    {
        List<Creator<CreatureEnemy>> normalEnemies = new ArrayList<>();
        List<Creator<CreatureEnemy>> strangeEnemies = new ArrayList<>();

        normalEnemies.add(CreatureEnemyNormal::new);
        strangeEnemies.add(CreatureEnemyBouncing::new);

        ENEMY_LISTS.put(EnemyType.NORMAL, normalEnemies);
        ENEMY_LISTS.put(EnemyType.STRANGE, strangeEnemies);
    }

    public static void registerEnemy(EnemyType type, Creator<CreatureEnemy> creator)
    {
        ENEMY_LISTS.get(type).add(creator);
    }

    public static CreatureEnemy createEnemy(EnemyType type)
    {
        List<Creator<CreatureEnemy>> list = ENEMY_LISTS.get(type);

        return list.get(RANDOM.nextInt(list.size())).create();
    }
}
