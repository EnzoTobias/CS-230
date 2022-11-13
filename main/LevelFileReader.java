import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**Creates a tile grid with required components from a level definition text file and reads size and time data
 * @author Enzo Tobias 2117781
 */
public class LevelFileReader {
	private static String ERROR = "Malformed level file error";
	private static String NO_FILE_ERROR = "error loading file or file not found"; ;
	public LevelFileReader() {
		// TODO Auto-generated constructor stub
	}
	
	public static Tile[][] createFromFile(String path) {
		try {
		File f = new File(path);
		Scanner in = new Scanner(f);
		return processLines(in);
		} catch (FileNotFoundException E) {
			System.out.println(NO_FILE_ERROR);
			return null;
		}
	}
	
	private static Tile[][] processLines(Scanner in) {
		Tile[][] tileGrid;
		int tileX = 0;
		int tileY = 0;
		String line = null;
		try {
			line = in.nextLine();
			String[] size = line.split("x");
			tileX = Integer.parseInt(size[0]);
			tileY = Integer.parseInt(size[1]);
		} catch (NumberFormatException | NoSuchElementException E) {
			System.out.println(ERROR);
			return null;
		}
			tileGrid = new Tile[tileX][tileY];
		for (int i = 0; i < tileY ; i++) {
			try {
				line = in.nextLine();
			} catch (NoSuchElementException E) {
				System.out.println(ERROR);
				return null;
			}
				String[] tiles = line.split(" ");
				if (tiles.length != tileX) {
					System.out.println(ERROR);
					return null;
				}
				for (int j = 0; j < tileX; j++) {
					tileGrid[j][i] = createTile(tiles[j]);
					if (tileGrid[j][i] == null) {
						System.out.println(ERROR);
						return null;
					}
				}
		}
		try {
			Integer.parseInt(in.nextLine());
		} catch (NumberFormatException | NoSuchElementException E) {
			System.out.println(ERROR);
			return null;
		}
		
		return tileGrid;

		
		
	}
	
	private static Tile createTile(String info) {
		char chars[] = info.toCharArray();
		Colour[] colours = new Colour[4];
		if (chars.length < 4) {
			System.out.println(ERROR);
			return null;
		}
		for (int i = 0; i <= 3; i++) {
			switch (chars[i]) {
				case 'R':
					colours[i] = Colour.RED;
					break;
				case 'G':
					colours[i] = Colour.GREEN;
					break;
				case 'B':
					colours[i] = Colour.BLUE;
					break;
				case 'Y':
					colours[i] = Colour.YELLOW;
					break;
				case 'C':
					colours[i] = Colour.CYAN;
					break;
				case 'M':
					colours[i] = Colour.MAGENTA;
					break;
				default:
					System.out.println(ERROR);
					return null;
			}
			
		}
		Tile tile = new Tile(colours);
		
		return tile;
	}
	
	

}
