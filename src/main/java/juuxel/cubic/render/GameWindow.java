/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;
import juuxel.cubic.menu.*;
import juuxel.cubic.options.Options;
import juuxel.cubic.world.World;
import juuxel.cubic.world.WorldGrassyLands;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public final class GameWindow
{
    private static boolean hasInit = false;
    private GameWindow() {}

    private static GamePane gamePane;
    private static WindowPane windowPane;
    private static GameFrame frame;

    public static void init()
    {
        if (hasInit)
            throw new IllegalStateException("The game window has already been initialized");

        CubicLookAndFeel.init();

        gamePane = new GamePane();
        windowPane = new WindowPane();
        frame = new GameFrame(String.format("%s %s", GameInfo.NAME, GameInfo.VERSION));

        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
            gamePane.requestFocusInWindow();
        });

        hasInit = true;
    }

    public static void repaint()
    {
        frame.repaint();
    }

    public static int getHeight()
    {
        return frame.getHeight();
    }

    public static int getWidth()
    {
        return frame.getWidth();
    }

    public static GameFrame getFrame()
    {
        return frame;
    }

    public static WindowPane getWindowPane()
    {
        return windowPane;
    }

    private static final class GameFrame extends JFrame
    {
        private GameFrame(String title)
        {
            super(title);
            setContentPane(windowPane);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(640, 480);
            setIconImage(Images.ICON);
        }
    }

    public static class WindowPane extends JPanel
    {
        private final CardLayout layout;

        private WindowPane()
        {
            layout = new CardLayout();
            setLayout(layout);
            add(new MainMenu(), "MainMenu");
            add(new OptionsMenu(), "OptionsMenu");
            add(new WorldMenu(), "WorldMenu");
            add(new AboutScreen(), "AboutScreen");
            add(new NewsScreen(), "NewsScreen");
            add(gamePane, "Game");
            layout.show(this, "MainMenu");
            addKeyListener(new Keyboard());
            setFocusable(true);
        }

        @Override
        public CardLayout getLayout()
        {
            return layout;
        }

        @Override
        protected void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);

            World world = Cubic.world;

            if (!world.isValidMenuBackground())
            {
                Cubic.world = World.getInstance(WorldGrassyLands.class);
            }

            Cubic.world.drawSky(Graphics.fromAWTGraphics(g));
            RenderEngine.drawLevel(Graphics.fromAWTGraphics(g));

            Cubic.world = world;
        }
    }

    public static class GamePane extends JPanel
    {
        private GamePane()
        {
        }

        @Override
        public void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);
            // TODO Resolution code here?
            RenderEngine.repaint(Graphics.fromAWTGraphics(g));
        }
    }

    private static class Keyboard extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == Options.takeScreenshot.getValue())
                Screenshots.takeScreenshot();
            else if (!Cubic.inStartScreen)
            {
                int key = e.getKeyCode();

                if (key == Options.moveLeft.getValue())
                {
                    Cubic.player.moveLeft();
                    Cubic.moveKeyDown = true;
                }
                else if (key == Options.moveRight.getValue())
                {
                    Cubic.player.moveRight();
                    Cubic.moveKeyDown = true;
                }
                else if (key == Options.jump.getValue())
                    Cubic.jumpKeyDown = true;
                else if (key == VK_ESCAPE)
                {
                    Cubic.inStartScreen = true;
                    MainMenu.continueButton.setVisible(true);
                    Cubic.selectScreen("MainMenu");
                }
            }
        }

        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == Options.moveLeft.getValue() || e.getKeyCode() == Options.moveRight.getValue())
                Cubic.moveKeyDown = false;
            else if (e.getKeyCode() == Options.jump.getValue())
                Cubic.jumpKeyDown = false;
        }

    }
}
