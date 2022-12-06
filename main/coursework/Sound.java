package coursework;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Class from which all sounds are called
 *
 * @author Andrew Drewett 2114095
 */
public class Sound {

    private static MediaPlayer mediaPlayer;

    public static class staticSound {

        /**
         * Plays the sound from the file
         * @param pathToFile The path to the sound file
         */
        private static void playSound(String pathToFile) {
            try {
                Media media = new Media(new File(pathToFile).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        public static void muteSound() {
            mediaPlayer.setMute(true);
        }

        public static void stopSound() {
            mediaPlayer.stop();
        }

        public static void menuMusic() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\Kinetic-ambience.mp3");
        }

        public static void playInGameMusic() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\Cyber-Stream-ambience.mp3");
        }

        public static void collectableSound() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\item-noise.wav");
        }

        public static void bombTick() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\bomb-tick.wav");
        }

        public static void bombExplosion() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\bomb-explosion.wav");
        }

        public static void leverSound() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\lever.wav");
        }

        public static void gunSound() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\gun-shot.wav");
        }

        public static void winSound() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\win.wav");
        }

        public static void lossSound() {
            playSound("C:\\Users\\andos\\Desktop\\CS230-CW2\\src\\sounds\\loss.wav");
        }
    }
}


