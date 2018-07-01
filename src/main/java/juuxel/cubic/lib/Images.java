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
import java.awt.Image;
import java.awt.image.BufferedImage;
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
    public static Sprite backButtonSprite;
    public static Sprite levelUpEffect;
    public static Sprite cloud;
    public static Sprite building;
    public static Image backButton;
    public static Sprite fireMonster;
    public static Sprite stone;
    public static Sprite caveWall;
    public static Sprite fireball;
    public static Sprite fly;
    public static Sprite crystal;
    public static Sprite backgroundCrystal;

    public static BufferedImage load(String file)
    {
        BufferedImage image;

        try
        {
            image = ImageIO.read(Images.class.getResource(IMAGE_PREFIX + file));
        }
        catch (IOException e)
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
        backButtonSprite = SpriteLoader.load("gui/back");
        backButton = backButtonSprite.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        bird = SpriteLoader.load("bird");
        birdPoop = SpriteLoader.load("bird_poop");
        levelUpEffect = SpriteLoader.load("level_up");
        cloud = SpriteLoader.load("decorations/cloud");
        building = SpriteLoader.load("decorations/building");
        fireMonster = SpriteLoader.load("fire_monster");
        stone = SpriteLoader.load("stone");
        caveWall = SpriteLoader.load("cave_wall");
        fireball = SpriteLoader.load("fireball");
        fly = SpriteLoader.load("fly");
        crystal = SpriteLoader.load("decorations/crystal");
        backgroundCrystal = SpriteLoader.load("decorations/background_crystal");
    }
}
