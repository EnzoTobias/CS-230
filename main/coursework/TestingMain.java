package coursework;

public class TestingMain  {

	public static void main(String args[]) {
		LevelControl control = new LevelControl();
		control.setLevel(LevelFileReader.createFromFile("C:\\Users\\andos\\Desktop\\leveldef.txt",control));
		System.out.println(LevelFileReader.levelToString(control.getLevel()));
		control.timeProgression();
		
		
		

	}


}
