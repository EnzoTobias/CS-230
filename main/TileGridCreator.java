import java.util.Scanner;

public class TileGridCreator {
	private Tile[][] tileGrid; 
	private int tileX = 0;
	private int tileY = 0;
	private Boolean hasError = false;
	public TileGridCreator() {
		// TODO Auto-generated constructor stub
	}
	
	private void processLines(Scanner in) {
		
			try {
				String line = in.nextLine();
				String[] size = line.split("x");
				tileX = Integer.parseInt(size[0]);
				tileY = Integer.parseInt(size[1]);
				tileGrid = new Tile[tileX][tileY];
				
			} catch (Exception e) {
				System.out.println("Malformed level file error");
				hasError = true;
			}
			
		for (int i = 1; i <= tileY && hasError == false; i++) {
			try {
				String line = in.nextLine();
				String[] tiles = line.split("");
				for (int j = 0; i <= tiles.length; j++) {
					
				}
			} catch (Exception E) {
				System.out.println("Malformed level file error");
				hasError = true;
			}
		}

		
		
	}
	
	private Tile createTile(String info) {
		char chars[] = info.toCharArray();
		Colour[] colours = new Colour[4];
		if (chars.length < 4) {
			System.out.println("Malformed level file error");
		}
		for (int i = 0; i <= chars.length; i++) {
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
			}
			if (colours[i] == null) {
				return null;
			}
		}
		Tile tile = new Tile(colours);
		
		return tile;
	}
	
	

}
