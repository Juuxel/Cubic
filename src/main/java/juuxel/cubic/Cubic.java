package juuxel.cubic;

import juuxel.cubic.enemy.*;
import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;
import juuxel.cubic.reference.Reference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic
{
    public static Cubic game;
    public static Player player;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<AbstractEnemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static int score = 0, deaths = 0, level = 1, lives = 8;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();

    private final GameFrame gameFrame;

    private Cubic()
    {
        (gameFrame = new GameFrame(this, "Cubic")).setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
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
            int dx = getWidth() / 2 - 100, dy = getHeight() / 2 - 6, offset = 85;

            g.getGraphics2D().setColor(new Color(0.5F, 0.9F, 1.0F));
            g.getGraphics2D().fillRect(0, 0, getWidth(), getHeight());
            g.getGraphics2D().setColor(Color.black);
            drawGround(g);

            g.getGraphics2D().drawImage(Images.START_INFO, dx - 32, dy - 32, dx + (256 - 32), dy + 32, 0, 0, 64, 16, null);
            g.getGraphics2D().drawImage(Images.LOGO, 10, 10, 10 + 128, 10 + 64, 0, 0, 32, 16, null);
            g.drawString("Version: " + Reference.VERSION, 10, 95);
            g.drawString("Controls:", 10, getHeight() - (offset + 60));
            g.drawString("Move left: A", 10, getHeight() - (offset + 40));
            g.drawString("Move right: D", 10, getHeight() - (offset + 20));
            g.drawString("Jump: Space", 10, getHeight() - offset);
        }
        else
        {
            g.getGraphics2D().setColor(new Color(0.5F, 0.9F, 1.0F));
            g.getGraphics2D().fillRect(0, 0, getWidth(), getHeight());
            g.getGraphics2D().setColor(Color.black);

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
        g.drawString("Level: " + level, 10, 30);
        g.drawString("Score until level up: " + ENEMIES.size(), 10, 50);
        g.drawString("Score: " + score, 10, 70);
        g.drawString("Deaths: " + deaths, 10, 90);
        g.drawString("Lives: " + lives, 10, 110);
    }

    private void drawGround(Graphics g)
    {
        for (int i = 0; i < getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) calculateY(32);

            g.drawImage(Images.GRASS, x, y - 32, 32, 32);
        }
    }

    public void onKeyPressed(KeyEvent e)
    {
        if (inStartScreen)
            inStartScreen = false;
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

    public void onKeyReleased(KeyEvent e)
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

    public static final class GameFrame extends JFrame implements KeyListener
    {
        private final Cubic game;

        GameFrame(Cubic game, String title)
        {
            super(title);
            this.game = game;
            setContentPane(new WindowPane(game));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            SwingUtilities.invokeLater(() -> setSize(640, 480));
            addKeyListener(this);
        }

        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            game.onKeyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            game.onKeyReleased(e);
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
