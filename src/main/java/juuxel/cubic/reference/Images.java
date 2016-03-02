package juuxel.cubic.reference;

import juuxel.cubic.graphics.Sprite;
import juuxel.cubic.graphics.SpriteLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Images
{
    private static final String IMAGE_PREFIX = "/assets/images/";

    public static final Image GRASS = load("grass.png");
    public static final Image LIFE = load("life.png");
    public static final Image LEVEL_UP = load("level_up.png");
    public static final Image DEATH = load("death.png");
    public static final Image GAME_OVER = load("game_over.png");
    public static final Image LOGO = load("logo.png");
    public static final Image CURSOR = load("cursor.png");
    public static final Image BUTTON = load("button.png");
    public static final Image SELECTED_BUTTON = load("button_selected.png");

    public static Sprite player;
    public static Sprite enemy;
    public static Sprite bouncingEnemy;
    public static Sprite dash;
    public static Sprite dot;
    public static Sprite number0;
    public static Sprite number1;
    public static Sprite number2;
    public static Sprite number3;
    public static Sprite number4;
    public static Sprite number5;
    public static Sprite number6;
    public static Sprite number7;
    public static Sprite number8;
    public static Sprite number9;

    public static Map<Character, Sprite> numbers;

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
            System.err.printf("Error in loading image '%s', setting to null%n", file);
        }

        return image;
    }

    public static void initialize()
    {
        player = SpriteLoader.load("player");
        enemy = SpriteLoader.load("enemy");
        bouncingEnemy = SpriteLoader.load("bouncing_enemy");
        dash = SpriteLoader.load("dash");
        dot = SpriteLoader.load("dot");
        number0 = SpriteLoader.load("numbers/number_0");
        number1 = SpriteLoader.load("numbers/number_1");
        number2 = SpriteLoader.load("numbers/number_2");
        number3 = SpriteLoader.load("numbers/number_3");
        number4 = SpriteLoader.load("numbers/number_4");
        number5 = SpriteLoader.load("numbers/number_5");
        number6 = SpriteLoader.load("numbers/number_6");
        number7 = SpriteLoader.load("numbers/number_7");
        number8 = SpriteLoader.load("numbers/number_8");
        number9 = SpriteLoader.load("numbers/number_9");

        HashMap<Character, Sprite> numberMap = new HashMap<>();

        numberMap.put('-', dash);
        numberMap.put('.', dot);
        numberMap.put('0', number0);
        numberMap.put('1', number1);
        numberMap.put('2', number2);
        numberMap.put('3', number3);
        numberMap.put('4', number4);
        numberMap.put('5', number5);
        numberMap.put('6', number6);
        numberMap.put('7', number7);
        numberMap.put('8', number8);
        numberMap.put('9', number9);

        numbers = Collections.unmodifiableMap(numberMap);
    }
}
