package coursework;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Adds the scores of each level to a file
 * 
 * @author Alex Rendell 211331
 */

public class LevelSelection {

	LevelControl control = new LevelControl();
	private static int levelNumberStorage;

	/**
	 * Creates a new file or adds the score to previous level file.
	 * 
	 * @param level
	 *            Current player level.
	 * @param user
	 *            Username of the player.
	 * @param score
	 *            score achieved when level is complete.
	 */
	static public void score(int level, String user, int score)
			throws IOException {
		try {
			File f = new File("scores/" + "level" + level + "_score.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			Scanner in = new Scanner(f);
			addScore(level, user, score);
			in.close();
		} catch (FileNotFoundException E) {
			try {
				newLevelFile(level, user, score);
			} catch (IOException I) {
				System.out.println("score catch");
			}
		}
	}

	/**
	 * Creates a new file and adds user score to it.
	 * 
	 * @param level
	 *            Current player level.
	 * @param user
	 *            Username of the player.
	 * @param score
	 *            score achieved when level is complete.
	 */
	public static void newLevelFile(int level, String user, int score)
			throws IOException {
		File f = new File("level" + level + "_score.txt");
		f.createNewFile();
		addScore(level, user, score);
	}

	/**
	 * Adds username and score to the level file.
	 * 
	 * @param level
	 *            Current player level.
	 * @param user
	 *            Username of the player.
	 * @param score
	 *            score achieved when level is complete.
	 */
	static public void addScore(int level, String user, int score) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(
					"scores/" + "level" + level + "_score.txt", true));
			out.write(user + "�" + score + "\n");
			out.close();
		} catch (IOException I) {
			System.out.println("addScore catch");
		}
	}
	/**
	 * Reads the score inside the level .txt file.
	 * 
	 * @param level
	 *            The level scoreboard wanting to be displayed.
	 */
	static public void readScore(int level) {

		File myObj = new File("scores/" + "level" + level + "_score.txt");
		try {
			if (myObj.exists()) {
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					System.out.println(data);
				}
				myReader.close();
			}

		} catch (FileNotFoundException E) {
			System.out.println("File not found at readScore");
		}
	}

	/**
	 * Sorts all user score into an array list.
	 * 
	 * @param level
	 *            The level wanting to be sorted.
	 */
	static public ArrayList<ScoreEntry> readScoreList(int level) {
		ArrayList<ScoreEntry> scoreList = new ArrayList<ScoreEntry>();
		try {
			File myObj = new File("scores/" + "level" + level + "_score.txt");
			if (myObj.exists()) {
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String[] nameScore = data.split("�");
					ScoreEntry thisEntry = new ScoreEntry();
					thisEntry.setProfileName(nameScore[0]);
					thisEntry.setScore(Integer.parseInt(nameScore[1]));
					scoreList.add(thisEntry);

				}
				myReader.close();

			}

		} catch (FileNotFoundException E) {
			System.out.println("File not found at readScoreList");
		}
		return scoreList;
	}

	public static void main(String args[]) throws IOException {

		LevelSelection l = new LevelSelection();
		l.score(1, "Alex", 400);
		l.score(1, "John", 350);
		l.score(1, "Paul", 300);
		l.score(2, "Pete", 600);

		l.readScore(1);
		l.readScore(2);
	}

	/**
	 * Gets the level in storage.
	 * 
	 * @return return the level in storage.
	 */
	public static int getLevelNumberStorage() {
		return levelNumberStorage;
	}

	/**
	 * Set the current level in storage.
	 * 
	 * @param levelNumberStorage
	 *            New number in storage.
	 */
	public static void setLevelNumberStorage(int levelNumberStorage) {
		LevelSelection.levelNumberStorage = levelNumberStorage;
	}
}