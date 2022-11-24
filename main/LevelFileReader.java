import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 * Creates a tile grid with required components from a level definition text
 * file and reads size and time data
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelFileReader {
	private static String ERROR = "Malformed level file error";
	private static String NO_FILE_ERROR = "error loading file or file not found";;
	public LevelFileReader() {
		// TODO Auto-generated constructor stub
	}

	public static Level createFromFile(String path, LevelControl control) {
		try {
			File f = new File(path);
			Scanner in = new Scanner(f);
			return processLines(in, control);
		} catch (FileNotFoundException E) {
			System.out.println(NO_FILE_ERROR);
			return null;
		}
	}

	private static Level processLines(Scanner in, LevelControl control) {
		Tile[][] tileGrid;
		int tileX = 0;
		int tileY = 0;
		int currentX = 0;
		int currentY = 0;
		String line = null;
		int initialTime;
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
		for (int i = 0; i < tileY; i++) {
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
				tileGrid[j][i] = createTile(tiles[j], control, currentX,
						currentY);
				if (tileGrid[j][i] == null) {
					System.out.println(ERROR);
					return null;
				}
				currentX += 1;
			}
			currentX = 0;
			currentY += 1;
		}
		try {
			initialTime = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException | NoSuchElementException E) {
			System.out.println(ERROR);
			return null;
		}
		in.close();
		Level level = new Level();
		level.setTileGrid(tileGrid);
		level.setInitialTime(initialTime);
		return level;

	}

	private static Tile createTile(String info, LevelControl control, int x,
			int y) {
		char chars[] = info.toCharArray();
		Colour[] colours = new Colour[4];
		if (chars.length < 4) {
			System.out.println(ERROR);
			return null;
		}
		for (int i = 0; i <= 3; i++) {
			switch (chars[i]) {
				case 'R' :
					colours[i] = Colour.RED;
					break;
				case 'G' :
					colours[i] = Colour.GREEN;
					break;
				case 'B' :
					colours[i] = Colour.BLUE;
					break;
				case 'Y' :
					colours[i] = Colour.YELLOW;
					break;
				case 'C' :
					colours[i] = Colour.CYAN;
					break;
				case 'M' :
					colours[i] = Colour.MAGENTA;
					break;
				default :
					System.out.println(ERROR);
					return null;
			}

		}
		int expectedDefSize = 0;
		Tile tile = new Tile(colours);
		tile.setX(x);
		tile.setY(y);
		if (info.contains("(") && info.contains(")")) {
			String tileContent = info.substring(info.indexOf("(") + 1,
					info.lastIndexOf(")"));
			String[] contentDefinitions = tileContent.split(",");
			switch (contentDefinitions[0]) {
				case "P" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return null;
					}
					Player p = new Player();
					p.setLevelControl(control);
					p.setDirection(getDirection(contentDefinitions[1]));
					tile.setContainedEntity(p);
					control.setPlayer(p);
					expectedDefSize = 2;
					break;
				case "ST" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return null;
					}
					SmartThief s = new SmartThief();
					s.setDirection(getDirection(contentDefinitions[1]));
					s.setLevelControl(control);
					tile.setContainedEntity(s);
					expectedDefSize = 2;
					break;
				case "FA" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return null;
					}
					FlyingAssassin f = new FlyingAssassin();
					f.setLevelControl(control);
					f.setDirection(getDirection(contentDefinitions[1]));
					tile.setContainedEntity(f);
					expectedDefSize = 2;
					break;
				case "FT" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return null;
					}
					if (getColour(contentDefinitions[2]) == null) {
						System.out.println(ERROR);
						return null;
					}
					FloorFollowingThief ff = new FloorFollowingThief();
					ff.setDirection(getDirection(contentDefinitions[1]));
					ff.setColour(getColour(contentDefinitions[2]));
					ff.setLevelControl(control);
					tile.setContainedEntity(ff);
					expectedDefSize = 3;
					break;
				case "C" :
					Collectable c = new Collectable(CollectableType.CENT);
					tile.setContainedItem(c);
					expectedDefSize = 1;
					break;
				case "S" :
					Collectable dollar = new Collectable(
							CollectableType.DOLLAR);
					tile.setContainedItem(dollar);
					expectedDefSize = 1;
					break;
				case "R" :
					Collectable r = new Collectable(CollectableType.RUBY);
					tile.setContainedItem(r);
					expectedDefSize = 1;
					break;
				case "D" :
					Collectable d = new Collectable(CollectableType.DIAMOND);
					tile.setContainedItem(d);
					expectedDefSize = 1;
					break;
				case "CL" :
					Clock cl = new Clock();
					tile.setContainedItem(cl);
					expectedDefSize = 1;
					break;
				case "G" :
					if (getColour(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return null;
					}
					Gate g = new Gate();
					g.setColour(getColour(contentDefinitions[1]));
					tile.setContainedItem(g);
					expectedDefSize = 2;
					break;
				case "L" :
					if (getColour(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return null;
					}
					Lever lv = new Lever();
					lv.setColour(getColour(contentDefinitions[1]));
					tile.setContainedItem(lv);
					expectedDefSize = 2;
					break;
				case "B" :
					Bomb b = new Bomb();
					tile.setContainedItem(b);
					expectedDefSize = 1;
					break;
				case "DR" :
					Door dr = new Door();
					tile.setContainedItem(dr);
					expectedDefSize = 1;
					break;
				default :
					System.out.println(ERROR);
					return null;
			}
			if (contentDefinitions.length != expectedDefSize) {
				System.out.println(ERROR);
				return null;
			}
		} else {
			if (chars.length > 4) {
				System.out.println(ERROR);
				return null;
			}
		}
		tile.setLevelControl(control);
		return tile;
	}

	private static Colour getColour(String colour) {
		switch (colour) {
			case "R" :
				return Colour.RED;
			case "G" :
				return Colour.GREEN;
			case "B" :
				return Colour.BLUE;
			case "Y" :
				return Colour.YELLOW;
			case "C" :
				return Colour.CYAN;
			case "M" :
				return Colour.MAGENTA;
			default :
				return null;

		}
	}

	private static Direction getDirection(String direction) {
		switch (direction) {
			case "U" :
				return Direction.UP;
			case "D" :
				return Direction.DOWN;
			case "L" :
				return Direction.LEFT;
			case "R" :
				return Direction.RIGHT;
			default :
				return null;
		}
	}

	public static String levelToString(Level level) {
		String output = "";
		Tile[][] tileGrid = level.getTileGrid();
		for (int j = 0; j < tileGrid[0].length; j++) {
			for (int i = 0; i < tileGrid.length; i++) {
				Tile tileToHandle = tileGrid[i][j];
				for (Colour col : tileToHandle.getColours()) {
					switch (col) {
						case RED :
							output += "R";
							break;
						case GREEN :
							output += "G";
							break;
						case BLUE :
							output += "B";
							break;
						case YELLOW :
							output += "Y";
							break;
						case CYAN :
							output += "C";
							break;
						case MAGENTA :
							output += "M";
							break;
						default :
							break;
					}
				}
				if (tileToHandle.hasEntity()) {
					output += "(";
					WalkingEntity entity = tileToHandle.getContainedEntity();
					if (entity instanceof Player) {
						output += "P";
						output += ",";
						output += dirToString(entity.getDirection());
					} else if (entity instanceof SmartThief) {
						output += "ST";
						output += ",";
						output += dirToString(entity.getDirection());
					} else if (entity instanceof FlyingAssassin) {
						output += "FA";
						output += ",";
						output += dirToString(entity.getDirection());
					} else if (entity instanceof FloorFollowingThief) {
						output += "FT";
						output += ",";
						output += dirToString(entity.getDirection());
						output += ",";
						output += colToString(
								((FloorFollowingThief) entity).getColour());
					}
					output += ")";
				} else {
					if (tileToHandle.hasItem()) {
						output += "(";
						Item item = tileToHandle.getContainedItem();
						if (item instanceof Collectable) {
							switch (((Collectable) item).getCollectableType()) {
								case CENT :
									output += "C";
									break;
								case DOLLAR :
									output += "S";
									break;
								case RUBY :
									output += "R";
									break;
								case DIAMOND :
									output += "D";
									break;
							}
						} else if (item instanceof Gate) {
							output += "G";
							output += ",";
							output += colToString(((Gate) item).getColour());
						} else if (item instanceof Lever) {
							output += "L";
							output += ",";
							output += colToString(((Lever) item).getColour());
						} else if (item instanceof Bomb) {
							output += "B";
						} else if (item instanceof Door) {
							output += "DR";
						}
						output += ")";
					}
				}
				output += " ";
			}
			output += System.lineSeparator();
		}
		output = output.substring(0, output.length() - 1);
		return output;
	}

	private static String dirToString(Direction dir) {
		switch (dir) {
			case UP :
				return "U";
			case DOWN :
				return "D";
			case LEFT :
				return "L";
			case RIGHT :
				return "R";
		}
		return null;

	}

	private static String colToString(Colour col) {
		switch (col) {
			case RED :
				return "R";
			case GREEN :
				return "G";
			case BLUE :
				return "B";
			case YELLOW :
				return "Y";
			case CYAN :
				return "C";
			case MAGENTA :
				return "M";
		}
		return null;

	}

}
