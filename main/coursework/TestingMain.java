package coursework;


public class TestingMain  {

	public static void main(String args[]) {
		LevelControl control = new LevelControl();
		 control.setLevel(LevelFileReader.createFromFile("D:/Documents/leveldef4.txt",control));
		System.out.println(LevelFileReader.levelToString(control.getLevel()));
		control.timeProgression();
		
		
		

	}


}
