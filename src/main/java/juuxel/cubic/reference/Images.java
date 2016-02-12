package juuxel.cubic.reference;

import juuxel.cubic.graphics.Sprite;
import juuxel.cubic.graphics.SpriteLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public final class Images
{
    private static final String IMAGE_PREFIX = "/assets/images/";
    public static final Sprite PLAYER = SpriteLoader.load("player");
    public static final Image GRASS = load("grass.png");
    public static final Image ENEMY = load("enemy.png");
    public static final Image BOUNCING_ENEMY = load("bouncing_enemy.png");
    public static final Image LIFE = load("life.png");
    public static final Image LEVEL_UP = load("level_up.png");
    public static final Image DEATH = load("death.png");
    public static final Image GAME_OVER = load("game_over.png");
    public static final Image LOGO = load("logo.png");
    public static final Image CURSOR = load("cursor.png");
    public static final Image BUTTON = load("button.png");
    public static final Image SELECTED_BUTTON = load("button_selected.png");

    public static Image load(String file)
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
