/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render;

import juuxel.cubic.options.Options;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * {@code Screenshots} takes screenshots.
 */
public final class Screenshots
{
    private Screenshots() {}

    /**
     * Gets an image of the component.
     *
     * @param component the component
     * @return image of the component
     */
    public static BufferedImage getScreenshot(Component component)
    {
        if (!Options.captureFrame.getValue() && component instanceof JFrame)
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

    /**
     * Takes a screenshot of the game frame and saves it to the screenshots directory inside
     * the run directory.
     */
    public static void takeScreenshot()
    {
        LocalDateTime time = LocalDateTime.now();
        String file = String.format(
            "screenshot_%d-%d-%d_%d-%d-%d.png",
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

            ImageIO.write(getScreenshot(GameWindow.getFrame()), "png", path.toFile());
        }
        catch (Exception e)
        {
            System.err.println("Error in writing screenshot:");
            e.printStackTrace();
        }
    }
}
