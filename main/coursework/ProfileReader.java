package coursework;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
			Profile profile = new Profile(userName,1);
			out.close();
			return profile;
		}
	}
	
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
	
	public static Profile loadProfile(String userName) throws IOException {
		String profilePath = "profiles/";
		File f = new File(profilePath + userName + ".txt");
		if (f.exists()) {
			Scanner in = new Scanner(f);
			int lvl = in.nextInt();
			in.close();
			return new Profile(userName,lvl);
		} else {
			return null;
		}
		
	}
	
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

	public static Profile getProfileStorage() {
		if (profileStorage == null) {
			return null;
		}
		return profileStorage;
	}

	public static void setProfileStorage(Profile profileStorage) {
		ProfileReader.profileStorage = profileStorage;
	}
}
