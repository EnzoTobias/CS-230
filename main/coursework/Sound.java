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
	private static MediaPlayer secondaryPlayer;

	/**
	 * Class from which all sounds are called statically
	 *
	 * @author Andrew Drewett 2114095
	 */
	public static class StaticSound {

		/**
		 * Plays the sound from the file - this will be background music
		 *
		 * @param fileName The path to the sound file
		 */

		private static final String pathToSound = "main/sounds/";

		private static void playSound(String fileName) {
			try {
				Media media = new Media(
						new File(pathToSound + fileName).toURI().toString());
				mediaPlayer = new MediaPlayer(media);
				mediaPlayer.play();
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		/**
		 * Plays the secondary sound from the file - this will be played on top of the background music
		 *
		 * @param fileName The path to the sound file
		 */
		private static void playSecondarySound(String fileName) {
			try {
				Media media = new Media(
						new File(pathToSound + fileName).toURI().toString());
				secondaryPlayer = new MediaPlayer(media);
				secondaryPlayer.play();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		/**
		 * Mutes the background music
		 *
		 * @param a True or False depending on if you want the sound muted
		 */
		public static void muteSound(Boolean a) {
			mediaPlayer.setMute(a);
		}

		/**
		 * Stop the background music playing
		 */
		public static void stopSound() {
			mediaPlayer.stop();
		}

		/**
		 * Pauses the music so it can resume from same point
		 */
		public static void pauseSound() {
			mediaPlayer.pause();
		}

		/**
		 * Resumes the music
		 */
		public static void resumeSound() {
			mediaPlayer.play();
		}

		/**
		 * Plays menu music
		 */
		public static void menuMusic() {
			playSound("Kinetic-ambience.mp3");
		}

		/**
		 * Plays the music in game music if not muted
		 */
		public static void playInGameMusic() {
			if (!mediaPlayer.isMute()) {
				playSound("Cyber-Stream-ambience.mp3");
			}
		}

		/**
		 * Sound played when an item is picked up
		 */
		public static void collectableSound() {
			playSecondarySound("item-noise.wav");
		}

		/**
		 * Sound played for bomb tick
		 */
		public static void bombTick() {
			playSecondarySound("bomb-tick.wav");
		}

		/**
		 * Sound played for bomb explosion
		 */
		public static void bombExplosion() {
			playSecondarySound("bomb-explosion.wav");
		}

		/**
		 * Sound played when using the lever
		 */
		public static void leverSound() {
			playSecondarySound("lever.wav");
		}

		/**
		 * Sound played when using the gun
		 */
		public static void gunSound() {
			playSecondarySound("gun-shot.wav");
		}

		/**
		 * Sound played if you've won the game
		 */
		public static void winSound() {
			playSecondarySound("win.wav");
		}

		/**
		 * Sound played if you've lost the game
		 */
		public static void lossSound() {
			playSecondarySound("loss.wav");
		}
	}
}
