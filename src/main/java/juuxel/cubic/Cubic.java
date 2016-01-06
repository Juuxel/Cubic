package juuxel.cubic;

import juuxel.cubic.enemy.*;
import juuxel.cubic.graphics.Graphics;
import juuxel.cubic.reference.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic implements KeyListener
{
    public static Cubic game;
    public static Player player;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<AbstractEnemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static int score = 0, deaths = 0, level = 1, lives = 8;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();

    private final GameFrame gameFrame;
    private static byte selectedButton = 0;

    private Cubic()
    {
        (gameFrame = new GameFrame(this, "Cubic")).setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        Translator.initialize();
        EnemyLists.initializeLists();
        game = new Cubic();
        player = new Player(Images.PLAYER);
        ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        while (inStartScreen)
        {
            game.repaint();
        }

        run();
    }

    public static void run() throws Exception
    {
        while (running)
        {
            CREATURES.forEach(Creature::logic);
            game.repaint();
            Thread.sleep(5);
        }
    }

    public void paint(Graphics g)
    {
        if (inStartScreen)
        {
            int dx = getWidth() / 2 - 100, dy = getHeight() / 2 - 50, offset = 85;

            drawSky(g);
            drawGround(g);

            g.drawImage(Images.CURSOR, dx, dy + selectedButton * 25, 16, 16);
            g.drawString(Translator.translate("mainMenu.play"), dx + 16, dy + 12);
            g.drawString(Translator.translate("mainMenu.exit"), dx + 16, dy + 12 + 25);

            g.drawImage(Images.LOGO, 10, 10, 128, 64);
            g.drawString(Translator.format("info.version", Reference.VERSION), 10, 95);
            g.drawString(Translator.translate("controls.title"), 10, getHeight() - (offset + 60));
            g.drawString(Translator.format("controls.moveLeft", "A"), 10, getHeight() - (offset + 40));
            g.drawString(Translator.format("controls.moveRight", "D"), 10, getHeight() - (offset + 20));
            g.drawString(Translator.format("controls.jump", Translator.translate("controls.keys.space")), 10, getHeight() - offset);
        }
        else
        {
            drawSky(g);
            drawScore(g);
            drawGround(g);

            CREATURES.forEach(creature -> creature.draw(g));

            if (lives <= 0)
            {
                int dx = getWidth() / 2, dy = getHeight() / 2;
                g.getGraphics2D().drawImage(Images.GAME_OVER, dx - 64, dy - 32, dx + 64, dy + 32, 0, 0, 32, 16, null);
                running = false;
            }
        }
    }

    public void repaint()
    {
        gameFrame.repaint();
    }


    private void drawScore(Graphics g)
    {
        g.drawString(Translator.format("game.level", level), 10, 30);
        g.drawString(Translator.format("game.scoreToLevelUp", ENEMIES.size()), 10, 50);
        g.drawString(Translator.format("game.score", score), 10, 70);
        g.drawString(Translator.format("game.deaths", deaths), 10, 90);
        g.drawString(Translator.format("game.lives", lives), 10, 110);
    }

    private void drawGround(Graphics g)
    {
        for (int i = 0; i < getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) calculateY(32);

            g.drawImage(Images.GRASS, x, y - 32, 32, 32);
        }
    }

    private void drawSky(Graphics g)
    {
        g.getGraphics2D().setColor(new Color(128, 218, 235));
        g.getGraphics2D().fillRect(0, 0, getWidth(), getHeight());
        g.getGraphics2D().setColor(Color.black);
    }

    public void keyPressed(KeyEvent e)
    {
        if (inStartScreen)
        {
            switch (e.getKeyCode())
            {
                case VK_UP:
                    if (selectedButton != 0)
                        selectedButton--;
                    break;
                case VK_DOWN:
                    if (selectedButton != 1)
                        selectedButton++;
                    break;
                case VK_SPACE:
                case VK_ENTER:
                    if (selectedButton == 0)
                        inStartScreen = false;
                    else
                        exit();
                    break;
            }
        }
        else
            switch (e.getKeyCode())
            {
                case VK_A:
                    player.xSpeed = Math.max(-1.75, player.xSpeed - 1.5);
                    moveKeyDown = true;
                    break;
                case VK_D:
                    player.xSpeed = Math.min(1.75, player.xSpeed + 1.5);
                    moveKeyDown = true;
                    break;
                case VK_SPACE:
                    jumpKeyDown = true;
                    break;
            }
    }

    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case VK_D:
            case VK_A:
                moveKeyDown = false;
                break;
            case VK_SPACE:
                jumpKeyDown = false;
                break;
        }
    }

    public void keyTyped(KeyEvent e)
    {}

    public int getHeight()
    {
        return gameFrame.getHeight();
    }

    public int getWidth()
    {
        return gameFrame.getWidth();
    }

    public static double calculateY(double y1)
    {
        return game.getHeight() - y1;
    }

    public static void exit()
    {
        System.exit(0);
    }

    public static final class GameFrame extends JFrame
    {
        GameFrame(Cubic game, String title)
        {
            super(title);
            setContentPane(new WindowPane(game));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            SwingUtilities.invokeLater(() -> setSize(640, 480));
            addKeyListener(game);
        }
    }

    private static class WindowPane extends JPanel
    {
        private final Cubic game;

        WindowPane(Cubic game)
        {
            this.game = game;
        }

        @Override
        public void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);
            game.paint(Graphics.fromGraphics2D((Graphics2D) g));
        }
    }
}
