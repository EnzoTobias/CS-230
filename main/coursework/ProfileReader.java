package coursework;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Reads and writes player profiles.
 * 
 * @author Enzo Tobias 2117781
 *
 */
public class ProfileReader {
	private static Profile profileStorage;
	public static Profile createProfile(String userName) throws IOException {
		String profilePath = "profiles/";
		File f = new File(profilePath + userName + ".txt");
		if (f.exists()) {
			return null;
		} else {
			f.createNewFile();
			PrintWriter out = new PrintWriter(f);
			out.write("1");
			Profile profile = new Profile(userName, 1);
			out.close();
			return profile;
		}
	}
	/**
	 * Delete a profile from the game data.
	 * 
	 * @param userName
	 *            The name of the profile to delete.
	 * @return A boolean denoting if the deletion was successful.
	 * @throws IOException
	 */
	public static boolean deleteProfile(String userName) throws IOException {
		String profilePath = "profiles/";
		File f = new File(profilePath + userName + ".txt");
		if (f.exists()) {
			f.delete();
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Load a profile from the game data.
	 * 
	 * @param userName
	 *            The name of the profile to load.
	 * @return The loaded profile, null if the load did not succeed.
	 * @throws IOException
	 */
	public static Profile loadProfile(String userName) throws IOException {
		String profilePath = "profiles/";
		File f = new File(profilePath + userName + ".txt");
		if (f.exists()) {
			Scanner in = new Scanner(f);
			int lvl = in.nextInt();
			in.close();
			return new Profile(userName, lvl);
		} else {
			return null;
		}

	}
	/**
	 * Save a profile to the game data.
	 * 
	 * @param profile
	 *            The profile to save to game data.
	 * @return A boolean denoting if the saving was successful.
	 * @throws IOException
	 */
	public static boolean saveProfile(Profile profile) throws IOException {
		String profilePath = "profiles/";
		File f = new File(profilePath + profile.getPlayerName() + ".txt");
		if (f.exists()) {
			f.delete();
			f.createNewFile();
			PrintWriter out = new PrintWriter(f);
			out.write(Integer.toString(profile.getCurrentLevel()));
			out.close();
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Returns the stored profile.
	 * 
	 * @return The stored profile to return.
	 */
	public static Profile getProfileStorage() {
		if (profileStorage == null) {
			return null;
		}
		return profileStorage;
	}
	/**
	 * Store a profile.
	 * 
	 * @param profileStorage
	 *            Profile to store.
	 */
	public static void setProfileStorage(Profile profileStorage) {
		ProfileReader.profileStorage = profileStorage;
	}
}
