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
 * @author Liam O'Reilly
 */
public class Main extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 1200;
	private static final int CANVAS_HEIGHT = 1000;

	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;

	// The width of the grid in number of cells.
	private static final int GRID_WIDTH = 12;

	// Y axis and Xaxis
	private int xAxis;
	private int yAxis;

	private int gameStart = 0;

	private int playerScore = 0;
	private int timeLeft;
	private String playerDirection = "UP";

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
	private Image smartTheif;
	private Image flyingAssasin;
	private Image floorFollowingTheif;

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
	private Image bombExpload;
	private Image clock;
	private Image door;
	private Image gateTest;
	private Image leverTest;
	private Image voidTile;

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

	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource("actionMenu.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Menu");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(event -> {
			event.consume();
			logout(stage);
		});

	}

	public void logout(Stage stage) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to save before exiting? ");

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
		playerImage = new Image("player.png");
		playerImageRight = new Image("playerRight.png");
		playerImageDown = new Image("playerDown.png");
		playerImageLeft = new Image("playerLeft.png");
		iconImage = new Image("icon.png");
		redTile = new Image("red.png");
		blueTile = new Image("blue.png");
		yellowTile = new Image("yellow.png");
		wallTile = new Image("wall.png");
		magentaTile = new Image("magenta.png");
		greenTile = new Image("green.png");
		cyanTile = new Image("cyan.png");
		voidTile = new Image("wall.png");
		coinItem = new Image("coin.png");
		centItem = new Image("cent.png");
		dollarItem = new Image("dollar.png");
		rubyItem = new Image("ruby.png");
		diamondItem = new Image("diamond.png");
		smartTheif = new Image("SmartThief.png");
		flyingAssasin = new Image("FlyingAssassin.png");
		floorFollowingTheif = new Image("FloorFollowingThief.png");
		bomb1 = new Image("Bomb1.png");
		bomb2 = new Image("Bomb2.png");
		bomb3 = new Image("Bomb3.png");
		bomb = new Image("Bomb-Numberless.png");
		clock = new Image("Clock.png");
		door = new Image("Door.png");
		gateTest = new Image("Gate-Red.png");
		leverTest = new Image("Lever-Red.png");

		profile = ProfileReader.getProfileStorage();
		
		// Build the GUI
		Pane root = buildGUI();

		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key
		// is pressed.
		scene.addEventFilter(KeyEvent.KEY_PRESSED,
				event -> processKeyEvent(event));

		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method
		// every second.
		tickTimeline = new Timeline(
				new KeyFrame(Duration.millis(1000), event -> tick()));
		// Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		// We start the timeline upon a button press.

		LevelControl newControl = new LevelControl();
		newControl.setLevel(
				LevelFileReader.createFromFile(fileToLoad, newControl));
		newControl.setCurrentProfile(profile);
		newControl.setLevelNumber(levelNumber);

		this.setControl(newControl);

		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreen(true);
		
	}

	public void saveCurrentGame() {
		try {
			PrintWriter out = new PrintWriter(
					new FileWriter("saves/" + this.profile.getPlayerName() + "-level"
							+ this.levelNumber + ".txt", false));
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
	 */
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.

		if (!control.isGameOver()) {
			Tile[][] Tilegrid = control.getTileGrid();

			switch (event.getCode()) {

				case RIGHT :
					control.getPlayer().moveInDirection(Direction.RIGHT);
					playerDirection = "RIGHT";
					drawGame();
					break;
				case LEFT :
					control.getPlayer().moveInDirection(Direction.LEFT);
					playerDirection = "LEFT";
					drawGame();
					break;
				case UP :
					control.getPlayer().moveInDirection(Direction.UP);
					playerDirection = "UP";
					drawGame();
					break;
				case DOWN :
					control.getPlayer().moveInDirection(Direction.DOWN);
					playerDirection = "DOWN";
					drawGame();
					break;
				case ESCAPE :
					this.saveCurrentGame();
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

		// Clear canvas
		// gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Set the background to gray.
		// gc.setFill(Color.GRAY);
		// gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		playerScore = control.getPlayer().getScore();
		timeLeft = control.getTimeLeft();

		String gridTile = LevelFileReader.levelToString(control);
		int counter = 0;

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
				}
				if (tileToHandle.hasEntity()) {
					WalkingEntity entity = tileToHandle.getContainedEntity();
					if (entity instanceof Player) {
						playerX = i;
						playerY = j;
						gameStart = 1;

					} else if (entity instanceof SmartThief) {
						entityTile = smartTheif;
						gc.drawImage(entityTile, (50 * i), (50 * j) + 20,
								(GRID_CELL_WIDTH), (GRID_CELL_HEIGHT));

					} else if (entity instanceof FlyingAssassin) {
						entityTile = flyingAssasin;
						gc.drawImage(entityTile, (50 * i), (50 * j) + 20,
								(GRID_CELL_WIDTH), (GRID_CELL_HEIGHT));

					} else if (entity instanceof FloorFollowingThief) {
						entityTile = floorFollowingTheif;
						((FloorFollowingThief) entity).getColour();
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
							itemTile = gateTest;

						} else if (item instanceof Lever) {
							itemTile = leverTest;

						} else if (item instanceof Bomb) {
							itemTile = bomb;

						} else if (item instanceof Door) {
							itemTile = door;

						} else if (item instanceof Clock) {
							itemTile = clock;
						}
						gc.drawImage(itemTile, (50 * i) + 10,
								(50 * j) + 10 + 20, (GRID_CELL_WIDTH / 1.5),
								(GRID_CELL_HEIGHT / 1.5));
					}
				}
			}
		}

		// Draw player at current location
		switch (playerDirection) {
			case "RIGHT" :
				gc.drawImage(playerImageRight, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
			case "LEFT" :
				gc.drawImage(playerImageLeft, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
			case "UP" :
				gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH,
						(playerY * GRID_CELL_HEIGHT) + 20);
				break;
			case "DOWN" :
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

		if (control.isGameOver()) {
			tickTimeline.stop();
		} else {
			control.oneMovementRound();
			control.setTimeLeft(control.getTimeLeft() - 1);
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
		root.setTop(toolbar);

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

	public static void main(String[] args) {
		launch(args);

	}

	public LevelControl getControl() {
		return control;
	}

	public void setControl(LevelControl control) {
		this.control = control;
	}

	public void switchToScene1(ActionEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource("actionMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	public void switchToGameLevel(ActionEvent event) throws IOException {
		ButtonType newLevel = new ButtonType("Start New");
		ButtonType loadLevel = new ButtonType("Load");
		ButtonType leaderboard = new ButtonType("Leaderboard");

		String savedLevel = "saves/" + this.profile.getPlayerName() + "-level"
				+ this.levelNumber + ".txt";
		File f = new File(savedLevel);

		Alert levelAlert;
		if (f.exists() && !f.isDirectory() && profile.getCurrentLevel() >= levelNumber) {
			levelAlert = new Alert(AlertType.NONE, "Level " + this.levelNumber
					+ " selected, you have save data for this level. What would you like to do?",
					newLevel, loadLevel, leaderboard);
		} else if (profile.getCurrentLevel() < levelNumber){
			levelAlert = new Alert(AlertType.WARNING, "You have not yet unlocked this level");
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
		} else if (result.get()== leaderboard) {
			this.switchToScene2(event);
		}
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if (profile.getCurrentLevel() >= levelNumber && !(result.get()== leaderboard)){

			this.startLevel(stage);
		}

	}
	
	public void createProfile(ActionEvent event) throws IOException {
		String profileName = nameField.getText();
		this.profile = ProfileReader.createProfile(profileName);
		if (profile == null) {
			Alert alreadyThere = new Alert(AlertType.WARNING, "That profile already exists, please load it instead");
			alreadyThere.show();
			logInDisplay.setText("Not logged in");
		} else {
			Alert done = new Alert(AlertType.INFORMATION, "Profile " + profileName + " created" );
			done.show();
			logInDisplay.setText("Current profile: " + profileName);
		}
		ProfileReader.setProfileStorage(profile);
	}
	
	public void loadProfile(ActionEvent event) throws IOException {
		String profileName = nameField.getText();
		this.profile = ProfileReader.loadProfile(profileName);
		if (profile == null) {
			Alert notThere = new Alert(AlertType.WARNING, "No profile found with that username, please create one");
			notThere.show();
			logInDisplay.setText("Not logged in");
		} else {
			logInDisplay.setText("Current profile: " + profileName);
		}
		ProfileReader.setProfileStorage(profile);
	}
	public void deleteProfile(ActionEvent event) throws IOException {
		String profileName = nameField.getText();
		 if (ProfileReader.deleteProfile(profileName) == false) {
				Alert notThere = new Alert(AlertType.WARNING, "This profile does not exist and thus cannot be deleted");
				notThere.show();
		 } else {
				Alert done = new Alert(AlertType.INFORMATION, "Profile " + profileName + " deleted 0_0");
				done.show();

		 };
		 ProfileReader.setProfileStorage(profile);
	}
	@FXML
	public void updateLoadedProfile() {
		profile = ProfileReader.getProfileStorage();
		if (logInDisplay != null) {
			if (profile == null) {
				logInDisplay.setText("Not logged in");

			} else {
				logInDisplay.setText("Current profile: " + profile.getPlayerName());

			}
		}
	}
	

	public void level1(ActionEvent event) throws IOException {
		this.levelNumber = 1;
		switchToGameLevel(event);
	}

	public void level2(ActionEvent event) throws IOException {
		this.levelNumber = 2;
		switchToGameLevel(event);
	}
	
	public void level3(ActionEvent event) throws IOException {
		this.levelNumber = 3;
		switchToGameLevel(event);
	}
	
	public void level4(ActionEvent event) throws IOException {
		this.levelNumber = 4;
		switchToGameLevel(event);
	}
	
	public void level5(ActionEvent event) throws IOException {
		this.levelNumber = 5;
		switchToGameLevel(event);
	}
	
	public void level6(ActionEvent event) throws IOException {
		this.levelNumber = 6;
		switchToGameLevel(event);
	}
	
	public void level7(ActionEvent event) throws IOException {
		this.levelNumber = 7;
		switchToGameLevel(event);
	}

	public void switchToScene2(ActionEvent event) throws IOException {
		Parent root = FXMLLoader
				.load(getClass().getResource("scoreBoard.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	
	@FXML
	public void populateScore() {
		ArrayList<ScoreEntry> scoreList = LevelSelection.readScoreList(1);
		Text[] labelList = new Text[]{entry10,entry11,entry12,entry13,entry14,entry15,entry16,entry17,entry18,entry19};
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
			if (!(scoreList.size()-1 < counter)) {
				ScoreEntry score = scoreList.get(counter);
				l.setText(score.getProfileName()+ " : " + score.getScore());
				counter += 1;
			}
		}
	}
	
	public void switchToLevel(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Levels.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private TextField dayMessage;
	public void textfieldDisplay() throws IOException {
		String message;
		message = "Hello";
		dayMessage.setText(message);
	}
	public void textfieldRemoveDisplay() throws IOException {
		dayMessage.setText("");
	}

	public void logout(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to save before exiting? ");

		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) scenePane.getScene().getWindow();
			System.out.println("GOOD JOB");
			stage.close();
		}
	}

}