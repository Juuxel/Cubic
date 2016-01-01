package juuxel.cubic;

import juuxel.cubic.enemy.*;
import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;
import juuxel.cubic.graphics.Window;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic extends Window
{
    public static Cubic game;
    public static Player player;
    public static boolean running = true, moveKeyDown, jumpKeyDown;
    public static final List<AbstractEnemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static int score = 0, deaths = 0, level = 1, lives = 8;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();

    private Cubic() throws Exception
    {
        super("Cubic");
    }

    public static void main(String[] args) throws Exception
    {
        EnemyLists.initializeLists();
        game = new Cubic();
        player = new Player(Images.PLAYER);
        ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        while (running)
        {
            CREATURES.forEach(Creature::logic);
            game.repaint();
            Thread.sleep(5);
        }
    }

    @Override
    public void paint(Graphics g)
    {
        g.getGraphics2D().setColor(new Color(0.5F, 0.9F, 1.0F));
        g.getGraphics2D().fillRect(0, 0, getSize().getWidth(), getSize().getHeight());
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

    private void drawScore(Graphics g)
    {
        g.getGraphics2D().drawString("Level: " + level, 10, 30);
        g.getGraphics2D().drawString("Score until level up: " + ENEMIES.size(), 10, 50);
        g.getGraphics2D().drawString("Score: " + score, 10, 70);
        g.getGraphics2D().drawString("Deaths: " + deaths, 10, 90);
        g.getGraphics2D().drawString("Lives: " + lives, 10, 110);
    }

    private void drawGround(Graphics g)
    {
        for (int i = 0; i < getWidth() / 32; i++)
        {
            int x = i * 32, y = (int) calculateY(32);

            g.getGraphics2D().drawImage(Images.GRASS, x + 32, y - 32, x, y, 0, 0, 8, 8, null);
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e)
    {
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

    @Override
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

    public static double calculateY(double y1)
    {
        return game.getHeight() - y1;
    }
}
