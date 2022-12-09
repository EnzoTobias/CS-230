package coursework;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.InputStream;

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
         //* @param pathToFile The path to the sound file
         */
        private static void playSound(String fileName) {
            try {
                String pathToSound = "src/sounds/";
                Media media = new Media(new File(pathToSound + fileName).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        public static void muteSound(Boolean a) {
            mediaPlayer.setMute(a);
        }

        public static void stopSound() {
            mediaPlayer.stop();
        }


        public static void menuMusic() {
            playSound("Kinetic-ambience.mp3");
        }

        public static void playInGameMusic() {
            playSound("Cyber-Stream-ambience.mp3");
        }

        public static void collectableSound() {
            playSound("item-noise.wav");
        }

        public static void bombTick() {
            playSound("bomb-tick.wav");
        }

        public static void bombExplosion() {
            playSound("bomb-explosion.wav");
        }

        public static void leverSound() {
            playSound("lever.wav");
        }

        public static void gunSound() {
            playSound("gun-shot.wav");
        }

        public static void winSound() {
            playSound("win.wav");
        }

        public static void lossSound() {
            playSound("loss.wav");
        }
    }
}


