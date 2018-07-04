/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import javax.sound.sampled.*;
import java.io.IOException;

public final class Sounds
{
    private Sounds() {}

    public static final Clip UI_CLICK = load("ui_click");
    public static final Clip UI_BACK = load("ui_back");
    public static final Clip MAIN_LOOP = load("main_loop");

    private static Clip load(String path)
    {
        try
        {
            var fullPath = String.format("/data/sounds/%s.wav", path);
            var stream = AudioSystem.getAudioInputStream(Sounds.class.getResource(fullPath));
            var clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(event -> {
                if (event.getType().equals(LineEvent.Type.STOP))
                    clip.setFramePosition(0);
            });

            return clip;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            System.err.printf("Error loading audio file '%s'%n", path);
            throw new RuntimeException(e);
        }
    }
}
