package juuxel.cubic.util;

import juuxel.cubic.creature.Creature;

/**
 * A handler interface for {@link Creature} creations.
 */
public interface ICreatureListener
{
    /**
     * Handles a creature being created.
     *
     * @param creature the creature
     */
    void onCreatureCreated(Creature creature);
}
