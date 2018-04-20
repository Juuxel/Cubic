/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import juuxel.cubic.creature.Creature;

/**
 * A handler interface for {@link Creature} creations.
 */
public interface CreatureListener
{
    /**
     * Handles a creature being created.
     *
     * @param creature the creature
     */
    void onCreatureCreated(Creature creature);
}
