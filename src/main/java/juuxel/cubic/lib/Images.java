/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.lib;

import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.render.sprite.SpriteLoader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Images
{
    private static final String IMAGE_PREFIX = "/data/images/";

    public static final Image LOGO = load("logo.png");
    public static final Image ICON = load("player.png").getScaledInstance(64, 64, Image.SCALE_FAST);

    public static Sprite player;
    public static Sprite enemy;
    public static Sprite bouncingEnemy;
    public static Sprite bird;
    public static Sprite birdPoop;
    public static Sprite life;
    public static Sprite death;
    public static Sprite score;
    public static Sprite level;
    public static Sprite levelUpIcon;
    public static Sprite heart;
    public static Sprite grass;
    public static Sprite bricks;
    public static Sprite levelUpEffect;
    public static Sprite cloud;
    public static Sprite building;
    public static BufferedImage backButton;
    public static Sprite fireMonster;
    public static Sprite stone;
    public static Sprite caveWall;
    public static Sprite fireball;
    public static Sprite fly;
    public static Sprite crystal;
    public static Sprite backgroundCrystal;
    public static Sprite smallGrass;
    public static Sprite smoke;

    public static BufferedImage load(String file)
    {
        BufferedImage image;

        try
        {
            var url = Images.class.getResource(IMAGE_PREFIX + file);

            if (url == null)
            {
                throw new FileNotFoundException(String.format("Image file %s not found", file));
            }

            image = ImageIO.read(url);
        }
        catch (Exception e)
        {
            image = null;
            System.err.printf("Exception thrown while loading image '%s', setting to null%n", file);
            System.err.println("Stack trace of the exception:");
            e.printStackTrace();
        }

        return image;
    }

    public static void init()
    {
        player = SpriteLoader.load("player");
        enemy = SpriteLoader.load("enemy");
        bouncingEnemy = SpriteLoader.load("bouncing_enemy");
        life = SpriteLoader.load("life");
        death = SpriteLoader.load("death");
        score = SpriteLoader.load("icons/score");
        level = SpriteLoader.load("icons/level");
        levelUpIcon = SpriteLoader.load("icons/level_up");
        heart = SpriteLoader.load("icons/heart");
        grass = SpriteLoader.load("tiles/grass");
        bricks = SpriteLoader.load("tiles/bricks");
        bird = SpriteLoader.load("bird");
        birdPoop = SpriteLoader.load("bird_poop");
        levelUpEffect = SpriteLoader.load("level_up");
        cloud = SpriteLoader.load("decorations/cloud");
        building = SpriteLoader.load("decorations/building");
        fireMonster = SpriteLoader.load("fire_monster");
        stone = SpriteLoader.load("stone");
        caveWall = SpriteLoader.load("tiles/cave_wall");
        fireball = SpriteLoader.load("fireball");
        fly = SpriteLoader.load("fly");
        crystal = SpriteLoader.load("decorations/crystal");
        backgroundCrystal = SpriteLoader.load("decorations/background_crystal");
        smallGrass = SpriteLoader.load("decorations/grass");
        smoke = SpriteLoader.load("smoke");

        backButton = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);

        var graphics = backButton.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.BLACK);
        graphics.fillPolygon(new int[] { 11, 11, 0 }, new int[] { 4, 20, 12 }, 3);
        graphics.fillRect(11, 11, 12, 3);
        graphics.dispose();
    }
}
