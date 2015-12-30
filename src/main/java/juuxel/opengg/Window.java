package juuxel.opengg;

import juuxel.opengg.exception.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window
{
    private boolean fullscreen = false;

    private final OpenGGFrame swingFrame;

    public Window(String title)
    {
        swingFrame = new OpenGGFrame(this, title);
        setVisible(true);
    }

    public Window()
    {
        swingFrame = new OpenGGFrame(this);
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public void setFullscreen(boolean bool) throws FeatureNotSupportedException
    {
        fullscreen = bool;

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (device.isFullScreenSupported())
            device.setFullScreenWindow(swingFrame);
        else
            throw new FeatureNotSupportedException("full screen");
    }

    public Size getSize()
    {
        return Size.fromDimension(swingFrame.getSize());
    }

    public void setSize(int width, int height)
    {
        SwingUtilities.invokeLater(() -> swingFrame.setSize(width, height));
    }

    public void setSize(Size size)
    {
        setSize(size.getWidth(), size.getHeight());
    }

    public boolean isVisible()
    {
        return swingFrame.isVisible();
    }

    public void setVisible(boolean bool)
    {
        SwingUtilities.invokeLater(() -> swingFrame.setVisible(bool));
    }

    public int getWidth()
    {
        return getSize().getWidth();
    }

    public int getHeight()
    {
        return getSize().getHeight();
    }

    public void paint(Graphics graphics)
    {
    }

    public OpenGGFrame getSwingFrame()
    {
        return swingFrame;
    }

    //TODO Write a wrapper for MouseEvent
    public void onMouseClicked(MouseEvent e)
    {}

    public void onMousePressed(MouseEvent e)
    {}

    public void onMouseReleased(MouseEvent e)
    {}

    public void onMouseEntered(MouseEvent e)
    {}

    public void onMouseExited(MouseEvent e)
    {
    }

    //TODO Write a wrapper for KeyEvent (Key codes not required)
    public void onKeyTyped(KeyEvent e)
    {}

    public void onKeyPressed(KeyEvent e)
    {}

    public void onKeyReleased(KeyEvent e)
    {
    }

    public void repaint()
    {
        getSwingFrame().repaint();
    }

    public static final class OpenGGFrame extends JFrame implements MouseListener, KeyListener
    {
        private final Window window;
        private final WindowPane pane;

        OpenGGFrame(Window window)
        {
            this(window, "OpenGG Game window");
        }

        OpenGGFrame(Window window, String title)
        {
            super(title);
            this.window = window;
            pane = new WindowPane(window);
            setContentPane(pane);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            SwingUtilities.invokeLater(() -> setSize(640, 480));
            addMouseListener(this);
            addKeyListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            window.onMouseClicked(e);
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            window.onMousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            window.onMouseReleased(e);
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            window.onMouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            window.onMouseExited(e);
        }

        @Override
        public void keyTyped(KeyEvent e)
        {
            window.onKeyTyped(e);
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            window.onKeyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            window.onKeyReleased(e);
        }
    }

    private static class WindowPane extends JPanel
    {
        private final Window window;

        WindowPane(Window window)
        {
            this.window = window;
        }

        @Override
        public void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);
            window.paint(Graphics.fromGraphics2D((Graphics2D) g));
        }
    }

    public static final class Size
    {
        private final int width, height;

        public Size(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public static Size fromDimension(Dimension d)
        {
            return new Size((int) d.getWidth(), (int) d.getHeight());
        }
    }
}
