import java.io.*;
import java.util.Scanner;

public class Profile {
    private String playerName;
    private int currentLevel;

    public Profile(String playerName, int currentLevel){
        this.currentLevel = currentLevel;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void  createProfile(String playerName) throws IOException {

        String profiles = "/Users/marcussiziba/IdeaProjects/PlayerProfile/src/profiles.txt";
        String userName = getPlayerName();
        boolean flag = false;
        int count = 0;
        //Reading the contents of the file
        Scanner user = new Scanner(new FileInputStream("/Users/marcussiziba/IdeaProjects/PlayerProfile/src/profiles.txt"));
        while(user.hasNextLine()) {
            String line = user.nextLine();
            if(line.indexOf(userName)!=-1) {
                flag = true;
                count = count+1;
            }
        }
        if(flag) {
            Profile profile = new Profile("",getCurrentLevel());
            
        } else {
            FileWriter profile = new FileWriter(profiles,true);
            profile.write(getPlayerName()  + getCurrentLevel());
            profile.close();

        }

    }


    public void deleteProfile(String playerName) throws IOException {
        String a = null;
        File input_file = new File("/Users/marcussiziba/IdeaProjects/PlayerProfile/src/profiles.txt");
        File temp_file = new File("/Users/marcussiziba/IdeaProjects/PlayerProfile/src/tempprofile.txt");
        BufferedReader my_reader = new BufferedReader(new FileReader(input_file));
        BufferedWriter my_writer = new BufferedWriter(new FileWriter(temp_file));
        String lineToRemove = getPlayerName();
        String current_line;
        while((current_line = my_reader.readLine()) != null) {
            String trimmedLine = current_line.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            my_writer.write(current_line + System.getProperty("line.separator"));

        }
        my_writer.close();
        my_reader.close();
        boolean is_success = temp_file.renameTo(input_file);
    }
//
    public void loadProfile(String playerName) throws FileNotFoundException {
        String userProfile = playerName;
        File file = new File("/Users/marcussiziba/IdeaProjects/PlayerProfile/src/profiles.txt");
        Scanner profile = new Scanner(file);
        while (profile.hasNextLine()){
            String lineFromFile = profile.nextLine();
            if(lineFromFile.contains(userProfile)) {
                // a match!
                Profile user = new Profile("",getCurrentLevel());
            }

        }

    }

}
