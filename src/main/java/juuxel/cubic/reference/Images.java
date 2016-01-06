package juuxel.cubic.reference;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public final class Images
{
    private static final String IMAGE_PREFIX = "/assets/images/";
    public static final Image PLAYER = load("player.png");
    public static final Image GRASS = load("grass.png");
    public static final Image ENEMY = load("enemy.png");
    public static final Image BOUNCING_ENEMY = load("bouncing_enemy.png");
    public static final Image LIFE = load("life.png");
    public static final Image LEVEL_UP = load("level_up.png");
    public static final Image DEATH = load("death.png");
    public static final Image GAME_OVER = load("game_over.png");
    public static final Image BLOCK = load("block.png");
    public static final Image LOGO = load("logo.png");
    public static final Image CURSOR = load("cursor.png");

    private static Image load(String file)
    {
        Image image;

        try
        {
            image = ImageIO.read(Images.class.getResource(IMAGE_PREFIX + file));
        }
        catch (IOException e)
        {
            image = null;
            System.err.printf("Error in loading image \"%s\", setting to null%n", file);
        }

        return image;
    }
}
