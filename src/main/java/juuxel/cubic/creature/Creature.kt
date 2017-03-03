package juuxel.cubic.creature

import juuxel.cubic.Cubic
import juuxel.cubic.lib.GameValues
import juuxel.cubic.lib.Images
import juuxel.cubic.render.Graphics
import juuxel.cubic.render.sprite.Sprite
import juuxel.cubic.util.Direction
import juuxel.cubic.util.IBasicFunctions
import java.awt.Image
import java.util.*

abstract class Creature : IBasicFunctions
{
    public val id: Int = Cubic.CREATURES.size
    public var x: Double = 0.toDouble()
        protected set
    public var y: Double = 0.toDouble()
        protected set
    public var xSpeed = 0.0
    public var ySpeed = 0.0
    public var spriteWidth = 32
    public var spriteHeight = 32
    public var sprite: Sprite = Images.bricks // Just a placeholder, should probably use some proper missing texture thing
    public var isCollisionEnabled: Boolean = false
    public var isFlippingEnabled: Boolean = false
    public var direction = Direction.RIGHT

    protected val random = Random()
    protected var speedModifierX = 1
    protected var speedModifierY = 1

    init
    {
        Cubic.CREATURES.add(this)
        Cubic.CREATURE_LISTENERS.forEach { it.onCreatureCreated(this) }
    }

    fun calculateY(): Double
    {
        return calculateY(y)
    }

    open fun kill()
    {
    }

    private fun drawCreature(g: Graphics, image: Image, width: Int, height: Int)
    {
        val dx = x.toInt()
        val dy = calculateY().toInt()

        if (direction == Direction.RIGHT)
            g.drawImage(image, dx - width / 2, dy - height / 2, width, height)
        else
            g.drawFlippedImage(image, dx - width / 2, dy - height / 2, width, height)
    }

    protected fun drawCreature(g: Graphics, sprite: Sprite)
    {
        drawCreature(g, sprite.getImage(this), spriteWidth, spriteHeight)
    }

    fun executeLogic()
    {
        moveX(xSpeed / speedModifierX)

        if (isCollisionEnabled)
        {
            var yNew = y + ySpeed / speedModifierY

            if (yNew < GameValues.GROUND)
                yNew = GameValues.GROUND

            y = yNew

            if (onGround())
                ySpeed = 0.0
        }
        else
            y += ySpeed / speedModifierY

        logic()
    }

    protected abstract fun logic()

    open fun draw(g: Graphics)
    {
        drawCreature(g, sprite)
    }

    fun onGround(): Boolean
    {
        return y <= GameValues.GROUND
    }

    fun moveX(xOffset: Double)
    {
        if (isFlippingEnabled)
        {
            direction = when {
                xOffset > 0 -> Direction.RIGHT
                xOffset < 0 -> Direction.LEFT
                else -> direction
            }
        }

        x += xOffset
    }

    fun moveY(yOffset: Double)
    {
        y += yOffset
    }
}
