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
    private static MediaPlayer secondaryPlayer;

    public static class StaticSound {

        /**
         * Plays the sound from the file
         //* @param fileName The path to the sound file
         */
        private static void playSound(String fileName) {
            try {
                String pathToSound = "main/sounds/";
                Media media = new Media(new File(pathToSound + fileName).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        private static void playSecondarySound(String fileName) {
        	try {
                String pathToSound = "main/sounds/";
                Media media = new Media(new File(pathToSound + fileName).toURI().toString());
                secondaryPlayer = new MediaPlayer(media);
                secondaryPlayer.play();
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

        public static void pauseSound() {
            mediaPlayer.pause();
        }

        public static void resumeSound() {
            mediaPlayer.play();
        }


        public static void menuMusic() {
            playSound("Kinetic-ambience.mp3");
        }

        public static void playInGameMusic() {
            if (!mediaPlayer.isMute()) {
                playSound("Cyber-Stream-ambience.mp3");
            }
        }

        public static void collectableSound() {
        	playSecondarySound("item-noise.wav");
        }

        public static void bombTick() {
        	playSecondarySound("bomb-tick.wav");
        }

        public static void bombExplosion() {
        	playSecondarySound("bomb-explosion.wav");
        }

        public static void leverSound() {
        	playSecondarySound("lever.wav");
        }

        public static void gunSound() {
        	playSecondarySound("gun-shot.wav");
        }

        public static void winSound() {
        	playSecondarySound("win.wav");
        }

        public static void lossSound() {
        	playSecondarySound("loss.wav");
        }
    }
}


