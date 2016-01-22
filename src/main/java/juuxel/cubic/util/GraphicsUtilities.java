package juuxel.cubic.util;

import juuxel.cubic.Cubic;
import juuxel.cubic.options.Options;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.*;
import java.time.LocalDateTime;

public class GraphicsUtilities
{
    public static BufferedImage getScreenshot(Component component)
    {
        if (!Options.captureFrame && component instanceof JFrame)
            return makeScreenshot(((JFrame) component).getContentPane());

        return makeScreenshot(component);
    }

    private static BufferedImage makeScreenshot(Component component)
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        component.printAll(graphics);
        graphics.dispose();

        return image;
    }

    public static void takeScreenshot()
    {
        LocalDateTime time = LocalDateTime.now();
        String file = String.format(
            "screenshot_%d-%d-%d_%d.%d.%d.png",
            time.getYear(),
            time.getMonthValue(),
            time.getDayOfMonth(),
            time.getHour(),
            time.getMinute(),
            time.getSecond()
        );

        try
        {
            Path path = Paths.get("screenshots", file);

            if (!Files.exists(Paths.get("screenshots")))
                Files.createDirectory(Paths.get("screenshots"));

            Files.createFile(path);

            ImageIO.write(getScreenshot(Cubic.game.getGameFrame()), "png", path.toFile());
        }
        catch (Exception e)
        {
            System.err.println("Error in writing screenshot:");
            e.printStackTrace();
        }
    }
}