package juuxel.cubic.lib;

import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.render.sprite.SpriteLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Images
{
    private static final String IMAGE_PREFIX = "/data/images/";

    public static final Image GAME_OVER = load("game_over.png");
    public static final Image LOGO = load("logo.png");
    public static final Image CURSOR = load("cursor.png");
    public static final Image ICON = load("player.png").getScaledInstance(64, 64, Image.SCALE_FAST);

    public static Sprite player;
    public static Sprite enemy;
    public static Sprite bouncingEnemy;
    public static Sprite bird;
    public static Sprite birdPoop;
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
    public static Sprite life;
    public static Sprite levelUp;
    public static Sprite death;
    public static Sprite score;
    public static Sprite level;
    public static Sprite levelUpIcon;
    public static Sprite heart;
    public static Sprite comma;
    public static Sprite space;
    public static Sprite slash;
    public static Sprite radioButton;
    public static Sprite radioButtonSelected;
    public static Sprite grass;
    public static Sprite bricks;
    public static Sprite backButtonSprite;
    public static Image backButton;

    public static Map<Character, Sprite> numbers;

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
            System.err.printf("Error in loading image '%s', setting to null%n", file);
        }

        return image;
    }

    public static void init()
    {
        player = SpriteLoader.load("player");
        enemy = SpriteLoader.load("enemy");
        bouncingEnemy = SpriteLoader.load("bouncing_enemy");
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
        life = SpriteLoader.load("life");
        levelUp = SpriteLoader.load("level_up");
        death = SpriteLoader.load("death");
        score = SpriteLoader.load("icons/score");
        level = SpriteLoader.load("icons/level");
        levelUpIcon = SpriteLoader.load("icons/level_up");
        heart = SpriteLoader.load("icons/heart");
        comma = SpriteLoader.load("numbers/comma");
        space = SpriteLoader.load("numbers/space");
        slash = SpriteLoader.load("numbers/slash");
        radioButton = SpriteLoader.load("gui/radio_button");
        radioButtonSelected = SpriteLoader.load("gui/radio_button_selected");
        grass = SpriteLoader.load("tiles/grass");
        bricks = SpriteLoader.load("tiles/bricks");
        backButtonSprite = SpriteLoader.load("gui/back");
        backButton = backButtonSprite.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        bird = SpriteLoader.load("bird");
        birdPoop = SpriteLoader.load("bird_poop");

        initializeNumberMap();
    }

    private static void initializeNumberMap()
    {
        HashMap<Character, Sprite> numberMap = new HashMap<>();

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
        numberMap.put(',', comma);
        numberMap.put(' ', space);
        numberMap.put('\u00A0', space);
        numberMap.put('/', slash);

        numbers = Collections.unmodifiableMap(numberMap);
    }
}
