package coursework;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 * Creates a tile grid with required components from a level definition text
 * file and reads size and time data.
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelFileReader {
	private static String ERROR = "Malformed level file error";
	private static String NO_FILE_ERROR = "error loading file or file not found";
	public LevelFileReader() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Create a level instance from a level definition file.
	 * @param path The path to the level definition file.
	 * @param control The level control instance this level will be assigned to.
	 * @return The level created and returned.
	 */
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
	/**
	 * Process an individual line of the level definition file and deal with the contents.
	 * @param in The scanner for reading from the file.
	 * @param control The level control instance this level will be assigned to.
	 * @return
	 */
	private static Level processLines(Scanner in, LevelControl control) {
		Tile[][] tileGrid;
		int tileX = 0;
		int tileY = 0;
		int currentX = 0;
		int currentY = 0;
		String line = null;
		int initialTime;
		int initialScore;
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
			initialScore = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException | NoSuchElementException E) {
			System.out.println(ERROR);
			return null;
		}
		in.close();
		Level level = new Level();
		level.setTileGrid(tileGrid);
		level.setInitialTime(initialTime);
		control.getPlayer().setScore(initialScore);
		return level;

	}
	/**
	 * Handles creating a tile from the given tile information.
	 * @param info The information for tile creation.
	 * @param control The level control instance this level will be assigned to.
	 * @param x The X coordinate in the tilegrid of this tile;
	 * @param y The Y coordinate in the tilegrid of this tile;
	 * @return The tile created from the information.
	 */
	private static Tile createTile(String info, LevelControl control, int x,
			int y) {
		char chars[] = info.toCharArray();
		Colour[] colours = new Colour[4];
		if (chars.length < 4) {
			System.out.println(ERROR);
			return null;
		}
		for (int i = 0; i <= 3; i++) {
			if (getColour(Character.toString(chars[i])) == null) {
				System.out.println(ERROR);
				return null;
			}
			colours[i] = getColour(Character.toString(chars[i]));

		}
		Tile tile = new Tile(colours);
		tile.setX(x);
		tile.setY(y);
		if (info.contains("(") && info.contains(")")) {
			String tileContent = info.substring(info.indexOf("(") + 1,
					info.lastIndexOf(")"));
			String[] contentDefinitions = tileContent.split(",");
			if (processEntity(contentDefinitions, control, tile) == false) {
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
	/**
	 * Process the information of an entity.
	 * @param contentDefinitions Array containing the information for the entity.
	 * @param control The level control instance this level will be assigned to.
	 * @param tile The tile this entity will be assigned to.
	 * @return A boolean denoting if the entity was successfully created or not.
	 */
	private static boolean processEntity(String[] contentDefinitions, LevelControl control, Tile tile) {
		int expectedDefSize = 0;
		try {
			switch (contentDefinitions[0]) {
				case "P" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
					}
					Player p = new Player();
					p.setThisTile(tile);
					p.setLevelControl(control);
					p.setDirection(getDirection(contentDefinitions[1]));
					tile.setContainedEntity(p);
					control.setPlayer(p);
					expectedDefSize = 2;
					break;
				case "ST" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
					}
					SmartThief s = new SmartThief();
					s.setDirection(getDirection(contentDefinitions[1]));
					s.setLevelControl(control);
					s.setThisTile(tile);
					tile.setContainedEntity(s);
					expectedDefSize = 2;
					break;
				case "FA" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
					}
					FlyingAssassin f = new FlyingAssassin();
					f.setLevelControl(control);
					f.setDirection(getDirection(contentDefinitions[1]));
					tile.setContainedEntity(f);
					f.setThisTile(tile);
					expectedDefSize = 2;
					break;
				case "FT" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
					}
					if (getColour(contentDefinitions[2]) == null) {
						System.out.println(ERROR);
						return false;
					}
					FloorFollowingThief ff = new FloorFollowingThief();
					ff.setDirection(getDirection(contentDefinitions[1]));
					ff.setColour(getColour(contentDefinitions[2]));
					ff.setLevelControl(control);
					ff.setThisTile(tile);
					tile.setContainedEntity(ff);
					expectedDefSize = 3;
					break;
				case "H" :
					if (getDirection(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
					}
					Hitman h = new Hitman();
					h.setDirection(getDirection(contentDefinitions[1]));
					h.setLevelControl(control);
					h.setThisTile(tile);
					tile.setContainedEntity(h);
					expectedDefSize = 2;
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
				case "FR" :
					Freezer fr = new Freezer();
					tile.setContainedItem(fr);
					expectedDefSize = 1;
					break;
				case "G" :
					if (getColour(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
					}
					Gate g = new Gate();
					g.setColour(getColour(contentDefinitions[1]));
					tile.setContainedItem(g);
					expectedDefSize = 2;
					break;
				case "L" :
					if (getColour(contentDefinitions[1]) == null) {
						System.out.println(ERROR);
						return false;
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
				case "GN" :
					Gun gn = new Gun();
					tile.setContainedItem(gn);
					expectedDefSize = 1;
					break;
				default :
					System.out.println(ERROR);
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(ERROR);
			return false;
		}
		
		if (contentDefinitions.length != expectedDefSize) {
			System.out.println(ERROR);
			return false;
		}
		return true;
	}
	

	/**
	 * Return a colour from a String defining the colour.
	 * @param colour The string with colour information.
	 * @return The returned colour.
	 */
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
			case "V" :
				return Colour.VOID;
			default :
				return null;

		}
	}
	/**
	 * Return a direction from a String defining the direction.
	 * @param direction The string with direction information.
	 * @return The returned direction.
	 */
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
	/**
	 *  Return a level as a string.
	 * @param control The level control which will have its level returned as a string.
	 * @return The level represented as a string, returned.
	 */
	public static String levelToString(LevelControl control) {
		String output = "";
		Level level = control.getLevel();
		Tile[][] tileGrid = level.getTileGrid();
		output += (tileGrid.length) + "x" + (tileGrid[0].length);
		output += System.lineSeparator();
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
						case VOID :
							output += "V";
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
					} else if (entity instanceof Hitman) {
						output += "H";
						output += ",";
						output += dirToString(entity.getDirection());
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
						} else if (item instanceof Gun) {
							output += "GN";
						} else if (item instanceof Clock) {
							output += "CL";
						} else if (item instanceof Freezer) {
							output += "FR";
						}
						output += ")";
					}
				}
				output += " ";
			}
			output += System.lineSeparator();
		}
		output = output.substring(0, output.length() - 1);
		output += control.getTimeLeft();
		output += System.lineSeparator();
		output += control.getPlayer().getScore();
		return output;
	}
	/**
	 * Converts a direction to its string representation.
	 * @param dir The direction being converted.
	 * @return The string representation returned.
	 */
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
	/**
	 * Converts a colour to its string representation.
	 * @param col The colour being converted.
	 * @return The string representation returned.
	 */
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
			case VOID :
				return "V";
		}
		return null;

	}

}
