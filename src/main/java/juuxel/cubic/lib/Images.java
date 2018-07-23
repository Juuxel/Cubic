/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.lib;

import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.render.sprite.SpriteLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

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
    public static Image playButton;
    public static Image optionsButton;
    public static Image aboutButton;
    public static Image closeButton;
    public static Image languageSelection;
    public static Image keyboard;
    public static Image camera;
    public static BufferedImage jump;
    public static BufferedImage moveLeft;
    public static BufferedImage moveRight;
    public static Image newsButton;
    public static Image world;
    public static Image volume;
    public static Image cursor;

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
        stone = SpriteLoader.load("tiles/stone");
        caveWall = SpriteLoader.load("tiles/cave_wall");
        fireball = SpriteLoader.load("fireball");
        fly = SpriteLoader.load("fly");
        crystal = SpriteLoader.load("decorations/crystal");
        backgroundCrystal = SpriteLoader.load("decorations/background_crystal");
        smallGrass = SpriteLoader.load("decorations/grass");
        smoke = SpriteLoader.load("smoke");
        playButton = SpriteLoader.load("gui/play_button").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        optionsButton = SpriteLoader.load("gui/options_button").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        aboutButton = SpriteLoader.load("gui/about_button").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        closeButton = SpriteLoader.load("gui/close_button").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        languageSelection = SpriteLoader.load("gui/language").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        keyboard = SpriteLoader.load("gui/keyboard").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        camera = SpriteLoader.load("gui/camera").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        newsButton = SpriteLoader.load("gui/news").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        world = SpriteLoader.load("gui/world").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        volume = SpriteLoader.load("gui/volume").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        cursor = SpriteLoader.load("gui/cursor").getImage().getScaledInstance(32, 32, Image.SCALE_FAST);

        backButton = createBackButton();
        moveLeft = backButton;
        moveRight = flip(moveLeft);
        jump = createJumpIcon();
    }

    private static BufferedImage createBackButton()
    {
        var backButton = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);

        var graphics = backButton.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.BLACK);
        graphics.fillPolygon(new int[] { 11, 11, 0 }, new int[] { 4, 20, 12 }, 3);
        graphics.fillRect(11, 11, 12, 3);
        graphics.dispose();

        return backButton;
    }

    private static BufferedImage flip(BufferedImage image)
    {
        var output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        var g = Graphics.fromGraphics2D(output.createGraphics());
        g.drawFlippedImage(image, 0, 0, image.getWidth(), image.getHeight());
        g.getGraphics2D().dispose();

        return output;
    }

    private static BufferedImage createJumpIcon()
    {
        var jump = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        var graphics = jump.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.BLACK);

        // The line
        graphics.setStroke(new BasicStroke(3));
        graphics.drawOval(5, 12, 25, 25);

        // Clear away excess
        graphics.setComposite(AlphaComposite.Clear);
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(22, 8, 4, 10);
        graphics.setColor(Color.BLACK);
        graphics.setComposite(AlphaComposite.SrcOver);

        // The triangle
        graphics.fillPolygon(new int[] { 16, 16, 25 }, new int[] { 5, 21, 12 }, 3);
        graphics.dispose();

        return jump;
    }
}
