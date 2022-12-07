package coursework;

import java.io.*;
import java.util.Scanner;

public class levelSelection {

    LevelControl control = new LevelControl();

    public String score(int level, String user, int score){
        try {
            File f = new File("level" + level + "_score.txt");
            Scanner in = new Scanner(f);
            addScore(level ,user, score);
        } catch (FileNotFoundException E) {
            try {
                newLevelFile(level, user, score);
            } catch (IOException I){
                System.out.println("score catch");
            }
        }
        return null;
    }
    //creates a new file
    public String newLevelFile(int level, String user, int score) throws IOException {
        System.out.println("here");
        File f = new File("level" + level + "_score.txt");
        f.createNewFile();
        addScore(level, user, score);
        return null;
    }
    //adds score and name to file
    public String addScore(int level, String user, int score){
        try {
            PrintWriter out = new PrintWriter(new FileWriter("level" + level + "_score.txt", true));
            out.write(user + "    " + score + "\n");
            out.close();
        } catch (IOException I){
            System.out.println("addScore catch");
        }
        return null;
    }

    public String readScore(int level){
        try {
            File myObj = new File("level" + level + "_score.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException E){
            System.out.println("File not found at readScore");
        }
        return null;
    }

    public static void main(String args[]) {

        levelSelection l = new levelSelection();
        l.score(1,"Alex",400);
        l.score(1,"John",350);
        l.score(1,"Paul",300);
        l.score(2,"Pete",600);

        l.readScore(1);
        l.readScore(2);
    }
}
