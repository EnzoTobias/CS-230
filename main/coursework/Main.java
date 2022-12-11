package coursework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting
 * point to show how to draw an image on a canvas, respond to arrow key presses,
 * use a tick method that is called periodically, and use drag and drop.
 * 
 * Do not build the whole application in one file. This file should probably
 * remain very small.
 *
 * @author Liam O'Reilly, Freddie, Enzo Tobias 2117781, Bader Shalata
 */
public class Main extends Application {
	/**
	 * Returns if time is frozen by the freezer item.
	 * 
	 * @return A boolean denoting if time is frozen.
	 */
	public boolean isFrozen() {
		return isFrozen;
	}
	/**
	 * Set if time is frozen.
	 * 
	 * @param isFrozen
	 *            The boolean to set if time is frozen.
	 */
	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
	/**
	 * Returns if the game is paused.
	 * 
	 * @return A boolean denoting if the game is paused.
	 */
	public boolean isPaused() {
		return isPaused;
	}
	/**
	 * Set if the game is paused.
	 * 
	 * @param isPaused
	 *            A boolean denoting if the game is paused.
	 */
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	// The dimensions of the window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 1920;
	private static final int CANVAS_HEIGHT = 1080;

	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;

	// The width of the grid in number of cells.
	private static final int GRID_WIDTH = 12;

	// Y axis and Xaxis
	private int xAxis;
	private int yAxis;
	private Image borderY;
	private Image borderX;

	private int gameStart = 0;

	private int playerScore = 0;
	private int timeLeft;
	private Direction playerDirection = null;

	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;

	// Load entity images
	private Image playerImage;
	private Image playerImageRight;
	private Image playerImageLeft;
	private Image playerImageDown;
	private Image iconImage;
	private Image entityTile;
	private Image smartThief;
	private Image flyingAssassin;
	private Image floorFollowingThief;

	// Load tile images
	private Image redTile;
	private Image blueTile;
	private Image yellowTile;
	private Image wallTile;
	private Image magentaTile;
	private Image greenTile;
	private Image cyanTile;
	private Image tile;

	// Load item images
	private Image itemTile;
	private Image coinItem;
	private Image centItem;
	private Image dollarItem;
	private Image rubyItem;
	private Image diamondItem;
	private Image bomb;
	private Image bomb1;
	private Image bomb2;
	private Image bomb3;
	private Image bombExplode;
	private Image clock;
	private Image freezer;
	private Image door;
	private Image gateTest;
	private Image leverTest;
	private Image voidTile;
	private Image gun;
	private Image hitMan;

	// level control
	private LevelControl control;

	private WalkingEntity player;

	private Profile profile;
	private int levelNumber;
	private String fileToLoad;

	// X and Y coordinate of player on the grid.
	private int playerX = 0;
	private int playerY = 0;

	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline;

	// controller stuff
	private Parent root;
	private Stage stage;
	private Scene scene;
	@FXML
	private Button exitButton;
	@FXML
	private AnchorPane scenePane;
	@FXML
	private TextField nameField;
	@FXML
	private Label logInDisplay;
	@FXML
	private Text entry10;
	@FXML
	private Text entry11;
	@FXML
	private Text entry12;
	@FXML
	private Text entry13;
	@FXML
	private Text entry14;
	@FXML
	private Text entry15;
	@FXML
	private Text entry16;
	@FXML
	private Text entry17;
	@FXML
	private Text entry18;
	@FXML
	private Text entry19;
	@FXML
	private Button soundButton;
	@FXML
	private Text unlockedLevel;
	private boolean isPaused = false;
	private boolean isFrozen = false;
	private boolean isPausedMenu = false;
	/**
	 * Launch the main menu of the application.
	 * 
	 * @param stage
	 *            The stage.
	 */
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource("actionMenu.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Menu");
		stage.getIcons().add(new Image("Icon-Image.png"));
		stage.setScene(scene);
		Sound.StaticSound.menuMusic();
		stage.show();
		stage.setOnCloseRequest(event -> {
			event.consume();
			logout(stage);
		});

	}
	/**
	 * Event handling exit request.
	 * 
	 * @param stage
	 *            The stage.
	 */
	public void logout(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle("Quit");
		alert.setHeaderText("You're about to quit the game!");
		alert.setContentText("Are you sure you want to quit?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			System.out.println("GOOD JOB");
			stage.close();
		}

	}

	/**
	 * Setup the new application.
	 * 
	 * @param primaryStage
	 *            The stage that is to be used for the application.
	 */
	public void startLevel(Stage primaryStage) {
		// Load images. Note we use png images with a transparent background.
		playerImage = new Image("Player.png");
		playerImageRight = new Image("PlayerRight.png");
		playerImageDown = new Image("PlayerDown.png");
		playerImageLeft = new Image("PlayerLeft.png");
		redTile = new Image("Red.png");
		blueTile = new Image("Blue.png");
		yellowTile = new Image("Yellow.png");
		wallTile = new Image("Wall.png");
		magentaTile = new Image("Magenta.png");
		greenTile = new Image("Green.png");
		cyanTile = new Image("Cyan.png");
		voidTile = new Image("Wall.png");
		centItem = new Image("Cent.png");
		dollarItem = new Image("Dollar.png");
		rubyItem = new Image("Ruby.png");
		diamondItem = new Image("Diamond.png");
		smartThief = new Image("SmartThief.png");
		flyingAssassin = new Image("FlyingAssassin.png");
		floorFollowingThief = new Image("FloorFollowingThief.png");
		bomb1 = new Image("Bomb1.png");
		bomb2 = new Image("Bomb2.png");
		bomb3 = new Image("Bomb3.png");
		bomb = new Image("Bomb-Numberless.png");
		clock = new Image("Clock.png");
		freezer = new Image("Freezer.png");
		door = new Image("Door.png");
		gateTest = new Image("Gate-Red.png");
		leverTest = new Image("Lever-Red.png");
		gun = new Image("Gun.png");
		hitMan = new Image("HitMan.png");
		borderY = new Image("borderY.png");
		borderX = new Image("borderX.png");

		profile = ProfileReader.getProfileStorage();

		Sound.StaticSound.stopSound();
		Sound.StaticSound.playInGameMusic();

		// Build the GUI
		Pane root = buildGUI();

		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key
		// is pressed.
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			try {
				processKeyEvent(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method
		// every second.
		tickTimeline = new Timeline(
				new KeyFrame(Duration.millis(500), event -> tick()));
		// Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		// We start the timeline upon a button press.

		LevelControl newControl = new LevelControl();
		newControl.setLevel(
				LevelFileReader.createFromFile(fileToLoad, newControl));
		newControl.setCurrentProfile(profile);
		newControl.setLevelNumber(levelNumber);
		newControl.setMyMain(this);
		this.setControl(newControl);

		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.setFullScreen(true);
		this.tickTimeline.play();

	}
	/**
	 * Save the current game.
	 */
	public void saveCurrentGame() {
		levelNumber = LevelSelection.getLevelNumberStorage();
		try {
			PrintWriter out = new PrintWriter(
					new FileWriter("saves/" + this.profile.getPlayerName()
							+ "-level" + this.levelNumber + ".txt", false));
			out.write(LevelFileReader.levelToString(control));
			out.close();
		} catch (IOException I) {
			System.out.println("IOException");
		}

	}

	/**
	 * Process a key event due to a key being pressed, e.g., to move the player.
	 * 
	 * @param event
	 *            The key event that was pressed.
	 * @throws IOException
	 */
	public void processKeyEvent(KeyEvent event) throws IOException {
		// We change the behaviour depending on the actual key that was pressed.

		if (!control.isGameOver()) {
			Tile[][] Tilegrid = control.getTileGrid();

			switch (event.getCode()) {

				case RIGHT :
					control.getPlayer().moveInDirection(Direction.RIGHT);
					drawGame();
					break;
				case LEFT :
					control.getPlayer().moveInDirection(Direction.LEFT);
					drawGame();
					break;
				case UP :
					control.getPlayer().moveInDirection(Direction.UP);
					drawGame();
					break;
				case DOWN :
					control.getPlayer().moveInDirection(Direction.DOWN);
					drawGame();
					break;
				case ESCAPE :
					this.quitChoices();
					break;
				default :
					// Do nothing for all other keys.
					break;
			}

			// Redraw game as the player may have moved.
			drawGame();
		}

		// Consume the event. This means we mark it as dealt with. This stops
		// other GUI nodes (buttons etc) responding to it.
		event.consume();
	}

	/**
	 * Draw the game on the canvas.
	 */

	/**
	 * Draw the game on the canvas.
	 */
	public void drawGame() {

		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		GraphicsContext gc1 = canvas.getGraphicsContext2D();
		GraphicsContext gc2 = canvas.getGraphicsContext2D();

		// Clear canvas
		// gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Set the background to gray.
		// gc.setFill(Color.GRAY);
		// gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		playerScore = control.getPlayer().getScore();
		timeLeft = control.getTimeLeft();

		String gridTile = LevelFileReader.levelToString(control);
		int counter = 0;

		if (gameStart == 0) {
			gc.drawImage(new Image("stars.png"), 0, 0, CANVAS_WIDTH,
					CANVAS_HEIGHT);
		}

		Tile[][] Tilegrid = control.getTileGrid();
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 20, Tilegrid.length, Tilegrid[0].length);

		for (int j = 0; j < Tilegrid[0].length; j++) {
			for (int i = 0; i < Tilegrid.length; i++) {
				Tile tileToHandle = Tilegrid[i][j];
				for (Colour col : tileToHandle.getColours()) {
					switch (col) {
						case RED :
							tile = redTile;
							break;
						case GREEN :
							tile = greenTile;
							break;
						case BLUE :
							tile = blueTile;;
							break;
						case YELLOW :
							tile = yellowTile;;
							break;
						case CYAN :
							tile = cyanTile;
							break;
						case MAGENTA :
							tile = magentaTile;
							break;
						case VOID :
							tile = voidTile;
							break;
						default :
							break;
					}

					if (counter == 0) {
						gc.drawImage(tile, 50 * i, 50 * j + 20,
								(GRID_CELL_WIDTH / 2), (GRID_CELL_HEIGHT / 2));
					}
					if (counter == 1) {
						gc.drawImage(tile, (50 * i) + 25, 50 * j + 20,
								(GRID_CELL_WIDTH / 2), (GRID_CELL_HEIGHT / 2));
					}
					if (counter == 2) {
						gc.drawImage(tile, 50 * i, (50 * j) + 25 + 20,
								(GRID_CELL_WIDTH / 2), (GRID_CELL_HEIGHT / 2));
					}
					if (counter > 2) {
						gc.drawImage(tile, (50 * i) + 25, (50 * j) + 25 + 20,
								(GRID_CELL_WIDTH / 2), (GRID_CELL_HEIGHT / 2));
						counter = -1;
					}
					counter = counter + 1;

					gc.drawImage(borderY, 50 * i, 50 * j + 20, (1),
							(GRID_CELL_HEIGHT));
					gc.drawImage(borderX, 50 * i, 50 * j + 20,
							(GRID_CELL_WIDTH), (1));
					gc.drawImage(borderY, 50 * i + 49, 50 * j + 20, (1),
							(GRID_CELL_HEIGHT));
					gc.drawImage(borderX, 50 * i, 50 * j + 49 + 20,
							(GRID_CELL_WIDTH), (1));

				}
				if (tileToHandle.hasEntity()) {
					WalkingEntity entity = tileToHandle.getContainedEntity();
					String entityDirection = null;
					String entityName = null;
					switch (entity.getDirection()) {
						case RIGHT :
							entityDirection = "Right";
							break;
						case LEFT :
							entityDirection = "Left";
							break;
						case DOWN :
							entityDirection = "Down";
							break;
						case UP :
							entityDirection = "";
							break;
					}
					if (entity instanceof Player) {
						playerX = i;
						playerY = j;
						gameStart = 1;

					} else if (entity instanceof SmartThief) {
						entityName = "SmartThief";

					} else if (entity instanceof FlyingAssassin) {
						entityName = "FlyingAssassin";

					} else if (entity instanceof FloorFollowingThief) {
						entityName = "FloorFollowingThief";

					} else if (entity instanceof Hitman) {
						entityName = "Hitman";
					}

					if (!(entity instanceof Player)) {
						entityTile = new Image(
								entityName + entityDirection + ".png");
						gc.drawImage(entityTile, (50 * i), (50 * j) + 20,
								(GRID_CELL_WIDTH), (GRID_CELL_HEIGHT));
					}

				} else {
					if (tileToHandle.hasItem()) {
						Item item = tileToHandle.getContainedItem();
						if (item instanceof Collectable) {
							switch (((Collectable) item).getCollectableType()) {
								case CENT :
									itemTile = centItem;
									break;
								case DOLLAR :
									itemTile = dollarItem;
									break;
								case RUBY :
									itemTile = rubyItem;
									break;
								case DIAMOND :
									itemTile = diamondItem;
									break;
							}
						} else if (item instanceof Gate) {
							String itemColour = null;
							switch (((Gate) item).getColour()) {
								case RED :
									itemColour = "-Red";
									break;
								case BLUE :
									itemColour = "-Blue";
									break;
								case GREEN :
									itemColour = "-Green";
									break;
								case MAGENTA :
									itemColour = "-Magenta";
									break;
								case CYAN :
									itemColour = "-Cyan";
									break;
								case YELLOW :
									itemColour = "-Yellow";
									break;
							}
							itemTile = new Image("Gate" + itemColour + ".png");

						} else if (item instanceof Lever) {
							String itemColour = null;
							switch (((Lever) item).getColour()) {
								case RED :
									itemColour = "-Red";
									break;
								case BLUE :
									itemColour = "-Blue";
									break;
								case GREEN :
									itemColour = "-Green";
									break;
								case MAGENTA :
									itemColour = "-Magenta";
									break;
								case CYAN :
									itemColour = "-Cyan";
									break;
								case YELLOW :
									itemColour = "-Yellow";
									break;
							}
							itemTile = new Image("Lever" + itemColour + ".png");

						} else if (item instanceof Bomb) {

							String bombStage = null;

							switch (((Bomb) item).getCurrentStage()) {
								case 1 :
									bombStage = "1";
									break;
								case 2 :
									bombStage = "2";
									break;
								case 3 :
									bombStage = "3";
									break;
								case 4 :
									bombStage = "-Numberless";
									break;
								default :
									bombStage = "-Numberless";
									break;

							}
							itemTile = new Image("Bomb" + bombStage + ".png");

						} else if (item instanceof Door) {
							itemTile = door;

						} else if (item instanceof Clock) {
							itemTile = clock;
						} else if (item instanceof Gun) {
							itemTile = gun;
						} else if (item instanceof Freezer) {
							itemTile = freezer;
						}
						gc.drawImage(itemTile, (50 * i) + 10,
								(50 * j) + 10 + 20, (GRID_CELL_WIDTH / 1.5),
								(GRID_CELL_HEIGHT / 1.5));
					}
				}
			}
		}

		// Draw player at current location
		playerDirection = this.getControl().getPlayer().getDirection();
		switch (playerDirection) {
			case RIGHT :
				gc.drawImage(playerImageRight, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
			case LEFT :
				gc.drawImage(playerImageLeft, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
			case UP :
				gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
			case DOWN :
				gc.drawImage(playerImageDown, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
		}

		gc1.setFill(Color.BLACK);
		gc1.fillRect(0, 0, Tilegrid.length * 50, 20);
		gc1.setFill(Color.WHITE);
		// playerScore = WalkingEntity.getScore();
		gc1.fillText("Score: " + (playerScore), 10, 15);
		gc1.fillText("Time : " + (timeLeft), 70, 15);

	}

	/**
	 * Reset the player's location and move them back to (0,0).
	 */
	public void resetPlayerLocation() {
		playerX = 0;
		playerY = 0;
		drawGame();
	}

	/**
	 * Move the player to roughly the center of the grid.
	 */
	public void movePlayerToCenter() {
		// We just move the player to cell (5, 2)
		playerX = 5;
		playerY = 2;
		drawGame();
	}

	/**
	 * This method is called periodically by the tick timeline and would for,
	 * example move, perform logic in the game, this might cause the bad guys to
	 * move (by e.g., looping over them all and calling their own tick method).
	 */
	public void tick() {
		if (isFrozen == true || isPausedMenu == true) {
			isPaused = true;
		} else {
			isPaused = false;
		}
		if (control.isGameOver()) {
			tickTimeline.stop();
		} else if (isPaused == false) {
			control.oneMovementRound();
			if (control.isMovementRound()) {
				control.setTimeLeft(control.getTimeLeft() - 1);
			}

		}

		// We then redraw the whole canvas.
		drawGame();
	}

	/**
	 * React when an object is dragged onto the canvas.
	 * 
	 * @param event
	 *            The drag event itself which contains data about the drag that
	 *            occurred.
	 */
	public void canvasDragDroppedOccured(DragEvent event) {
		double x = event.getX();
		double y = event.getY();

		// Print a string showing the location.
		String s = String.format(
				"You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		// Draw an icon at the dropped location.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Draw the the image so the top-left corner is where we dropped.
		gc.drawImage(iconImage, x, y);
		// Draw the the image so the center is where we dropped.
		// gc.drawImage(iconImage, x - iconImage.getWidth() / 2.0, y -
		// iconImage.getHeight() / 2.0);
	}

	/**
	 * Create the GUI.
	 * 
	 * @return The panel that contains the created GUI.
	 */
	private Pane buildGUI() {
		// Create top-level panel that will hold all GUI nodes.
		BorderPane root = new BorderPane();

		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);

		// Create a toolbar with some nice padding and spacing
		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		// root.setTop(toolbar);

		// Create the toolbar content

		// Reset Player Location Button
		// Button resetPlayerLocationButton = new Button("Reset Player");
		// toolbar.getChildren().add(resetPlayerLocationButton);

		// Setup the behaviour of the button.
		// resetPlayerLocationButton.setOnAction(e -> {
		// We keep this method short and use a method for the bulk of the
		// work.
		// resetPlayerLocation();
		// });

		// Center Player Button
		// Button centerPlayerLocationButton = new Button("Center Player");
		// toolbar.getChildren().add(centerPlayerLocationButton);

		// GraphicsContext gc = canvas.getGraphicsContext2D();
		// gc.fillText("Text centered on your Canvas",
		// Math.round(canvas.getWidth()), Math.round(canvas.getHeight()));

		// Setup the behaviour of the button.
		// centerPlayerLocationButton.setOnAction(e -> {
		// We keep this method short and use a method for the bulk of the
		// work.
		// movePlayerToCenter();
		// });

		// Tick Timeline buttons
		Button startTickTimelineButton = new Button("Start Ticks");
		Button stopTickTimelineButton = new Button("Stop Ticks");
		// We add both buttons at the same time to the timeline (we could have
		// done this in two steps).
		toolbar.getChildren().addAll(startTickTimelineButton,
				stopTickTimelineButton);
		// Stop button is disabled by default
		stopTickTimelineButton.setDisable(true);

		// Setup the behaviour of the buttons.
		startTickTimelineButton.setOnAction(e -> {
			// Start the tick timeline and enable/disable buttons as
			// appropriate.
			startTickTimelineButton.setDisable(true);
			tickTimeline.play();
			stopTickTimelineButton.setDisable(false);
		});

		stopTickTimelineButton.setOnAction(e -> {
			// Stop the tick timeline and enable/disable buttons as appropriate.
			stopTickTimelineButton.setDisable(true);
			tickTimeline.stop();
			startTickTimelineButton.setDisable(false);
		});

		// Setup a draggable image.
		ImageView draggableImage = new ImageView();
		// draggableImage.setImage(iconImage);
		toolbar.getChildren().add(draggableImage);

		// This code setup what happens when the dragging starts on the image.
		// You probably don't need to change this (unless you wish to do more
		// advanced things).
		draggableImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate
				// different forms
				// of drags operations, for example, moving files or copying
				// files).
				Dragboard db = draggableImage
						.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag
				// event.
				// We do not use this, but we could use it to store extra data
				// if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});

		// This code allows the canvas to receive a dragged object within its
		// bounds.
		// You probably don't need to change this (unless you wish to do more
		// advanced things).
		canvas.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// Mark the drag as acceptable if the source was the draggable
				// image.
				// (for example, we don't want to allow the user to drag things
				// or files into our application)
				if (event.getGestureSource() == draggableImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				}
			}
		});

		// This code allows the canvas to react to a dragged object when it is
		// finally dropped.
		// You probably don't need to change this (unless you wish to do more
		// advanced things).
		canvas.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// We call this method which is where the bulk of the behaviour
				// takes place.
				canvasDragDroppedOccured(event);
				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});

		// Finally, return the border pane we built up.
		return root;
	}
	/**
	 * Main method launching game.
	 * 
	 * @param args
	 *            Main method args.
	 */
	public static void main(String[] args) {
		launch(args);

	}
	/**
	 * Return the level control of the running game.
	 * 
	 * @return The level control.
	 */
	public LevelControl getControl() {
		return control;
	}
	/**
	 * Set the level control of the running game.
	 * 
	 * @param control
	 *            The level control.
	 */
	public void setControl(LevelControl control) {
		this.control = control;
	}
	/**
	 * Switch to the main menu scene.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void switchToScene1(ActionEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource("actionMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * Switch to the how to play scene.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void howToPlayScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource(("spritesScene2.fxml")));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * Switch to the second page of the how to play scene.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void howToPlayScene2(MouseEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource(("spritesScene3.fxml")));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * Switch to the menu scene.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource(("hello-view.fxml")));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Launch a game level into play.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void switchToGameLevel(ActionEvent event) throws IOException {
		ButtonType newLevel = new ButtonType("Start New");
		ButtonType loadLevel = new ButtonType("Load");
		ButtonType leaderboard = new ButtonType("Leaderboard");
		levelNumber = LevelSelection.getLevelNumberStorage();

		String savedLevel = "saves/" + this.profile.getPlayerName() + "-level"
				+ this.levelNumber + ".txt";
		File f = new File(savedLevel);

		Alert levelAlert;
		if (f.exists() && !f.isDirectory()
				&& profile.getCurrentLevel() >= levelNumber) {
			levelAlert = new Alert(AlertType.NONE, "Level " + this.levelNumber
					+ " selected, you have save data for this level. What would you like to do?",
					newLevel, loadLevel, leaderboard);
		} else if (profile.getCurrentLevel() < levelNumber) {
			levelAlert = new Alert(AlertType.WARNING,
					"You have not yet unlocked this level");
		} else {
			levelAlert = new Alert(AlertType.NONE,
					"Level " + this.levelNumber
							+ " selected, what would you like to do?",
					newLevel, leaderboard);
		}

		levelAlert.setTitle("Level Choice");
		Optional<ButtonType> result = levelAlert.showAndWait();

		if (result.get() == newLevel) {
			fileToLoad = "levels/" + "level" + this.levelNumber + ".txt";
		} else if (result.get() == loadLevel) {
			fileToLoad = savedLevel;
		} else if (result.get() == leaderboard) {
			this.switchToScene2(event);
		}
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if (profile.getCurrentLevel() >= levelNumber
				&& !(result.get() == leaderboard)) {

			this.startLevel(stage);
		}

	}
	/**
	 * Create a new player profile.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void createProfile(ActionEvent event) throws IOException {
		String profileName = nameField.getText();
		this.profile = ProfileReader.createProfile(profileName);
		if (profile == null) {
			Alert alreadyThere = new Alert(AlertType.WARNING,
					"That profile already exists, please load it instead");
			alreadyThere.show();
			logInDisplay.setText("Not logged in");
		} else {
			Alert done = new Alert(AlertType.INFORMATION,
					"Profile " + profileName + " created");
			done.show();
			logInDisplay.setText("Current profile: " + profileName);
		}
		ProfileReader.setProfileStorage(profile);
	}
	/**
	 * Load a player profile.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void loadProfile(ActionEvent event) throws IOException {
		String profileName = nameField.getText();
		this.profile = ProfileReader.loadProfile(profileName);
		if (profile == null) {
			Alert notThere = new Alert(AlertType.WARNING,
					"No profile found with that username, please create one");
			notThere.show();
			logInDisplay.setText("Not logged in");
		} else {
			logInDisplay.setText("Current profile: " + profileName);
			Alert done = new Alert(AlertType.INFORMATION,
					"Logged in as " + profileName);
			done.show();

		}
		ProfileReader.setProfileStorage(profile);
	}
	/**
	 * Delete a player profile.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void deleteProfile(ActionEvent event) throws IOException {
		String profileName = nameField.getText();
		if (ProfileReader.deleteProfile(profileName) == false) {
			Alert notThere = new Alert(AlertType.WARNING,
					"This profile does not exist and thus cannot be deleted");
			notThere.show();
		} else {
			Alert done = new Alert(AlertType.INFORMATION,
					"Profile " + profileName + " deleted 0_0");
			profile = null;
			logInDisplay.setText("Not logged in");
			done.show();

		} ;
		ProfileReader.setProfileStorage(profile);

	}
	/**
	 * Processes the end of the game.
	 * 
	 * @param didPlayerWin
	 *            A boolean denoting if the player won.
	 * @throws IOException
	 */
	public void processGameEnd(boolean didPlayerWin) throws IOException {
		Sound.StaticSound.stopSound();
		levelNumber = LevelSelection.getLevelNumberStorage();
		stage.setFullScreen(false);
		if (didPlayerWin == true) {
			Sound.StaticSound.winSound();
			playerScore = control.getPlayer().getScore()
					+ control.getTimeLeft();
			Alert win = new Alert(AlertType.INFORMATION,
					"You won with " + playerScore + " points!");
			win.show();
			LevelSelection.score(levelNumber, profile.getPlayerName(),
					playerScore);
			profile.setCurrentLevel(
					Math.max(levelNumber + 1, profile.getCurrentLevel()));
			ProfileReader.saveProfile(profile);

		} else {
			Sound.StaticSound.lossSound();
			Alert loss = new Alert(AlertType.INFORMATION,
					"You lost (which is weird since this game is easy), try again!");
			loss.show();
		}
		this.start(stage);
		this.tickTimeline.stop();
	}
	/**
	 * Update the displayed profile.
	 */
	@FXML
	public void updateLoadedProfile() {
		profile = ProfileReader.getProfileStorage();
		if (logInDisplay != null) {
			if (profile == null) {
				logInDisplay.setText("Not logged in");

			} else {
				logInDisplay
						.setText("Current profile: " + profile.getPlayerName());

			}
		}
		if (unlockedLevel != null) {
			unlockedLevel.setText("Level unlocked: "
					+ Integer.toString(profile.getCurrentLevel()));
		}
	}
	/**
	 * Launch level 1.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level1(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(1);
		switchToGameLevel(event);
	}
	/**
	 * Launch level 2.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level2(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(2);
		switchToGameLevel(event);
	}
	/**
	 * Launch level 3.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level3(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(3);
		switchToGameLevel(event);
	}
	/**
	 * Launch level 4.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level4(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(4);
		switchToGameLevel(event);
	}
	/**
	 * Launch level 5.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level5(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(5);
		switchToGameLevel(event);
	}
	/**
	 * Launch level 6.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level6(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(6);
		switchToGameLevel(event);
	}
	/**
	 * Launch level 7.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void level7(ActionEvent event) throws IOException {
		LevelSelection.setLevelNumberStorage(7);
		switchToGameLevel(event);
	}
	/**
	 * Switch to the scoreboard scene.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void switchToScene2(ActionEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource("scoreBoard.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	/**
	 * Populate the display of the scoreboard scene.
	 */
	@FXML
	public void populateScore() {
		ArrayList<ScoreEntry> scoreList = LevelSelection
				.readScoreList(LevelSelection.getLevelNumberStorage());
		this.updateLoadedProfile();
		Text[] labelList = new Text[]{entry10, entry11, entry12, entry13,
				entry14, entry15, entry16, entry17, entry18, entry19};
		scoreList.sort((ScoreEntry s1, ScoreEntry s2) -> {
			if (s1.getScore() > s2.getScore()) {
				return -1;
			}
			if (s1.getScore() < s2.getScore()) {
				return 1;
			}
			return 0;
		});
		int counter = 0;
		for (Text l : labelList) {
			if (!(scoreList.size() - 1 < counter)) {
				ScoreEntry score = scoreList.get(counter);
				l.setText(score.getProfileName() + " : " + score.getScore());
				counter += 1;
			}
		}
	}
	/**
	 * Handles choices upon attempting to close an ongoing game.
	 * 
	 * @throws IOException
	 */
	public void quitChoices() throws IOException {
		ButtonType exitSave = new ButtonType("Save");
		ButtonType exitNoSave = new ButtonType("Quit without saving");
		Alert alert = new Alert(AlertType.NONE,
				"Would you like to quit the game?", exitSave, exitNoSave,
				ButtonType.CANCEL);
		alert.setTitle("Quit?");
		stage.setFullScreen(false);
		isPausedMenu = true;
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == exitSave) {
			this.saveCurrentGame();
			Sound.StaticSound.stopSound();
			this.start(stage);
			this.tickTimeline.stop();
		} else if (result.get() == exitNoSave) {
			Sound.StaticSound.stopSound();
			this.start(stage);
			this.tickTimeline.stop();
		} else if (result.get() == ButtonType.CANCEL) {
			stage.setFullScreen(true);
			isPausedMenu = false;
		}

	}
	/**
	 * Switch to the level selection menu.
	 * 
	 * @param event
	 *            The event.
	 * @throws IOException
	 */
	public void switchToLevel(ActionEvent event) throws IOException {
		if (profile != null) {
			Parent root = FXMLLoader
					.load(getClass().getResource("Levels.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.WARNING,
					"Please create a profile or log in before playing");
			alert.show();
		}

	}

	@FXML
	private Text dayMessage;
	/**
	 * Display message of the day text.
	 * 
	 * @throws IOException
	 */
	public void textfieldDisplay() throws IOException {
		String message;
		message = MessageOfTheDay.returnString();
		dayMessage.setText(message);
	}
	/**
	 * Hide message of the day text.
	 * 
	 * @throws IOException
	 */
	public void textfieldRemoveDisplay() throws IOException {
		dayMessage.setText("");
	}
	/**
	 * Event handling exit request.
	 * 
	 * @param stage
	 *            The stage.
	 */
	public void logout(ActionEvent event) {
		Sound.StaticSound.pauseSound();
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle("Quit");
		alert.setHeaderText("You're about to quit the game!");
		alert.setContentText("Are you sure you want to quit?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) scenePane.getScene().getWindow();
			System.out.println("GOOD JOB");
			stage.close();
		}
		Sound.StaticSound.resumeSound();
	}
	/**
	 * Change the text of the mute sound button.
	 */
	public void buttonTextChange() {
		String mute = "Mute Music";
		String unmute = "Unmute Music";
		if (soundButton.getText().equals(mute)) {
			soundButton.setText(unmute);
			Sound.StaticSound.muteSound(true);
		} else {
			soundButton.setText(mute);
			Sound.StaticSound.muteSound(false);
		}
	}

}