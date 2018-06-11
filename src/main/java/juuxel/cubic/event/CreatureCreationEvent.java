/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.event;

import juuxel.cubic.creature.Creature;

/**
 * {@code CreatureCreationEvent} events are fired when a creature is created.
 */
public final class CreatureCreationEvent implements Event
{
    private final Creature creature;

    /**
     * Creates a {@code CreatureCreationEvent}.
     * @param creature the created creature
     */
    public CreatureCreationEvent(Creature creature)
    {
        this.creature = creature;
    }

    /**
     * Gets the creature of this event.
     * @return the creature
     */
    public Creature getCreature()
    {
        return creature;
    }
}
