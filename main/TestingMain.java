
public class TestingMain {

	public static void main(String args[]) {
		Tile[][] loltester = LevelFileReader
				.createFromFile("D:/Documents/leveldef.txt");
		LevelControl level =  new LevelControl(loltester);
		loltester[0][0].setLevel(level);
		loltester[0][0].getContainedItem().itemEffect(loltester[0][0],new Player());
		System.out.println(LevelFileReader.gridToString(loltester));
		

	}
}
