
public class TestingMain {

	public static void main(String args[]) {
		Tile[][] loltester = LevelFileReader
				.createFromFile("D:/Documents/leveldef.txt");
		System.out.println(LevelFileReader.gridToString(loltester));

	}
}
