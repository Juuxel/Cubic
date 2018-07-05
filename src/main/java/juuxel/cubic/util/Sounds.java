/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Sounds
{
    private Sounds() {}

    private static final List<Clip> clips = new ArrayList<>();
    public static final Clip UI_CLICK = load("ui_click");
    public static final Clip UI_BACK = load("ui_back");
    public static final Clip MAIN_LOOP = load("main_loop");
    public static final Clip GRASSY_LANDS_LOOP = load("grassy_lands_loop");
    public static final Clip ENEMY_KILLED = load("enemy_killed");
    public static final Clip PLAYER_KILLED = load("player_killed");
    public static final Clip FIREBALL = load("fireball");
    public static final Clip CRYSTAL_CAVE_LOOP = load("crystal_cave_loop");
    public static float minVolume;
    public static float maxVolume;
    private static float volume = 0;
    private static boolean hasVolumeBeenSet = false;

    /**
     * Loads a {@code .wav} sound file from {@code /data/sounds/path.wav}.
     *
     * @param path the sound file name without the extension
     * @return a sound clip
     * @throws RuntimeException if sound cannot be loaded
     */
    private static Clip load(String path)
    {
        try
        {
            var fullPath = String.format("/data/sounds/%s.wav", path);
            var url = Sounds.class.getResource(fullPath);

            if (url == null)
                throw new FileNotFoundException(String.format("Sound clip %s not found", fullPath));

            var stream = AudioSystem.getAudioInputStream(url);
            var clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(event -> {
                if (event.getType().equals(LineEvent.Type.STOP))
                    clip.setFramePosition(0);
            });

            if (!hasVolumeBeenSet)
            {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                minVolume = control.getMinimum();
                maxVolume = control.getMaximum();
                hasVolumeBeenSet = true;
            }

            clips.add(clip);

            return clip;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            throw new RuntimeException(String.format("Error loading audio file '%s'%n", path), e);
        }
    }

    public static void setVolume(float volume)
    {
        for (Clip c : clips)
            setVolume(c, volume);
    }

    public static float getVolume()
    {
        return volume;
    }

    private static void setVolume(Clip c, float volume)
    {
        Sounds.volume = volume;
        FloatControl control = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(Utils.clamp(volume, control.getMinimum(), control.getMaximum()));
    }
}
