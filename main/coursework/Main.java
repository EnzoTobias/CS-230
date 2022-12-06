package coursework;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting point to show
 * how to draw an image on a canvas, respond to arrow key presses, use a tick method that is
 * called periodically, and use drag and drop.
 * 
 * Do not build the whole application in one file. This file should probably remain very small.
 *
 * @author Liam O'Reilly
 */
public class Main extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 600;
	private static final int CANVAS_HEIGHT = 400;

	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	
	// The width of the grid in number of cells.
	private static final int GRID_WIDTH = 12;

	private static final String gameName = "Jewel Chase";

	//Y axis and Xaxis
	private int xAxis;
	private int yAxis;

	private int gameStart = 0;
	


	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;
		
	// Loaded images
	private Image playerImage;
	private Image iconImage;
	private Image redTile;
	private Image blueTile;
	private Image yellowTile;
	private Image wallTile;
	private Image tile;
	private Image entityTile;
	private Image itemTile;
	private Image magentaTile;
	private Image greenTile;
	private Image cyanTile;
	private Image centImage;
	private Image flyingAssassinImage;
	private Image floorFollowingThiefImage;
	private Image smartThiefImage;
	private Image dollarImage;
	private Image diamondImage;
	private Image clockImage;
	private Image rubyImage;
	private Image doorImage;


	//level control
	private LevelControl control;

	
	
	// X and Y coordinate of player on the grid.
	private int playerX = 0;
	private int playerY = 0;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline; 
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {
		// Load images. Note we use png images with a transparent background.
		playerImage = new Image("Player.png");
		iconImage = new Image("Icon.png");
		redTile = new Image("Red.png");
		blueTile = new Image("Blue.png");
		yellowTile = new Image("Yellow.png");
		wallTile = new Image("Wall.png");
		magentaTile = new Image("Magenta.png");
		greenTile = new Image("Green.png");
		cyanTile = new Image("Cyan.png");
		centImage = new Image("Cent.png");
		flyingAssassinImage = new Image("FlyingAssassin.png");
		floorFollowingThiefImage = new Image("FloorFollowingThief.png");
		smartThiefImage = new Image("SmartThief.png");
		diamondImage = new Image("Diamond.png");
		dollarImage = new Image("Dollar.png");
		rubyImage = new Image("Ruby.png");
		clockImage = new Image("Clock.png");
		doorImage = new Image("Door.png");

		
		String fileToLoad = "C:\\Users\\andos\\Desktop\\CS230-CW2\\leveldef.txt";

		// Build the GUI 
		Pane root = buildGUI();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key is pressed.
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
				
		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
		 // Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		// We start the timeline upon a button press.
		
		LevelControl newControl = new LevelControl();
		newControl.setLevel(LevelFileReader.createFromFile(fileToLoad, newControl));
		
		this.setControl(newControl);
		
		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.setTitle(gameName);
		primaryStage.getIcons().add(new Image("icon.png"));
		primaryStage.show();
		Sound.staticSound.playInGameMusic();
	}
	
	/**
	 * Process a key event due to a key being pressed, e.g., to move the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		
		if(!control.isGameOver()) {
			Tile [][] Tilegrid =control.getTileGrid();

			switch (event.getCode()) {	

			
			    case RIGHT:
			    	control.getPlayer().moveInDirection(Direction.RIGHT);
			    	drawGame();
		        	break;		        
		        case LEFT:
		        	control.getPlayer().moveInDirection(Direction.LEFT);
			    	drawGame();
		        	break;	
				case UP:
					control.getPlayer().moveInDirection(Direction.UP);
			    	drawGame();
		        	break;		        
		        case DOWN:
		        	control.getPlayer().moveInDirection(Direction.DOWN);
			    	drawGame();
		        	break;	
				default:
		        	// Do nothing for all other keys.
		        	break;
			}
			
			// Redraw game as the player may have moved.
			drawGame();
		}
		
		
		
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
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
		
		// Clear canvas
		//gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Set the background to gray.
		//gc.setFill(Color.GRAY);
		//gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		
		String gridTile = LevelFileReader.levelToString(control.getLevel());
		int counter = 0;

		Tile [][] Tilegrid =control.getTileGrid();

		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, Tilegrid.length, Tilegrid[0].length);


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
						default :
							break;
					}
			
					if (counter == 0){
						gc.drawImage(tile, 50*i ,50*j , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));
					}
					if (counter == 1){
						gc.drawImage(tile, (50*i)+25 ,50*j , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));
					}
					if (counter == 2){
						gc.drawImage(tile, 50*i ,(50*j)+25 , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));
					} 
					if (counter > 2 ){
						gc.drawImage(tile, (50*i)+25 ,(50*j)+25 , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));
						counter = -1;
					}
					counter = counter +1;	
				}
				if (tileToHandle.hasEntity()) {
					WalkingEntity entity = tileToHandle.getContainedEntity();
					if (entity instanceof Player) {
						playerX = i;
						playerY = j;
						gameStart = 1;

					} else if (entity instanceof SmartThief) {
						entityTile=smartThiefImage;
						gc.drawImage(entityTile, (50*i)+12 ,(50*j)+12 , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));

					} else if (entity instanceof FlyingAssassin) {
						entityTile=flyingAssassinImage;
						gc.drawImage(entityTile, (50*i)+12 ,(50*j)+12 , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));

					} else if (entity instanceof FloorFollowingThief) {
						entityTile=floorFollowingThiefImage;
						((FloorFollowingThief) entity).getColour();
						gc.drawImage(entityTile, (50*i)+12 ,(50*j)+12 , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));
					}
					
					

				} else {
					if (tileToHandle.hasItem()) {
						Item item = tileToHandle.getContainedItem();
						if (item instanceof Collectable) {
							switch (((Collectable) item).getCollectableType()) {
								case CENT :
									itemTile= centImage;
									break;
								case DOLLAR :
									itemTile= dollarImage;
									break;
								case RUBY :
									itemTile= rubyImage;
									break;
								case DIAMOND :
									itemTile= diamondImage;
									break;
							}
						} else if (item instanceof Gate) {
							itemTile= centImage;

						} else if (item instanceof Lever) {
							itemTile= centImage;

						} else if (item instanceof Bomb) {
							itemTile= centImage;

						} else if (item instanceof Door) {
							itemTile= doorImage;

						}
						gc.drawImage(itemTile, (50*i)+12 ,(50*j)+12 , (GRID_CELL_WIDTH/2),(GRID_CELL_HEIGHT/2));
					}
			}		}
		}

		// Draw player at current location
		gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
		
		
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
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		
		if (control.isGameOver()) {
			tickTimeline.stop();
		} else {
			control.oneMovementRound();
		}
		
		// We then redraw the whole canvas.
		drawGame();
	}
	
	/**
	 * React when an object is dragged onto the canvas. 
	 * @param event The drag event itself which contains data about the drag that occurred.
	 */
	public void canvasDragDroppedOccured(DragEvent event) {
    	double x = event.getX();
        double y = event.getY();

        // Print a string showing the location.
        String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
    	System.out.println(s);

    	// Draw an icon at the dropped location.
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	// Draw the the image so the top-left corner is where we dropped.
    	gc.drawImage(iconImage, x, y);
    	// Draw the the image so the center is where we dropped.    	
    	// gc.drawImage(iconImage, x - iconImage.getWidth() / 2.0, y - iconImage.getHeight() / 2.0);
	}
	
	/**
	 * Create the GUI.
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
		Button resetPlayerLocationButton = new Button("Reset Player");
		toolbar.getChildren().add(resetPlayerLocationButton);

		// Setup the behaviour of the button.
		resetPlayerLocationButton.setOnAction(e -> {
			// We keep this method short and use a method for the bulk of the work.
			resetPlayerLocation();
		});

		// Center Player Button		
		Button centerPlayerLocationButton = new Button("Center Player");
		toolbar.getChildren().add(centerPlayerLocationButton);		

		// Setup the behaviour of the button.
		centerPlayerLocationButton.setOnAction(e -> {
			// We keep this method short and use a method for the bulk of the work.
			movePlayerToCenter();
		});
		
		// Tick Timeline buttons
		Button startTickTimelineButton = new Button("Start Ticks");
		Button stopTickTimelineButton = new Button("Stop Ticks");
		// We add both buttons at the same time to the timeline (we could have done this in two steps).
		toolbar.getChildren().addAll(startTickTimelineButton, stopTickTimelineButton);
		// Stop button is disabled by default
		stopTickTimelineButton.setDisable(true);

		// Setup the behaviour of the buttons.
		startTickTimelineButton.setOnAction(e -> {
			// Start the tick timeline and enable/disable buttons as appropriate.
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
		draggableImage.setImage(iconImage);
        toolbar.getChildren().add(draggableImage);
        
        // This code setup what happens when the dragging starts on the image.
        // You probably don't need to change this (unless you wish to do more advanced things).
        draggableImage.setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		        // Mark the drag as started.
		    	// We do not use the transfer mode (this can be used to indicate different forms
		    	// of drags operations, for example, moving files or copying files).
		    	Dragboard db = draggableImage.startDragAndDrop(TransferMode.ANY);

		    	// We have to put some content in the clipboard of the drag event.
		    	// We do not use this, but we could use it to store extra data if we wished.
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello");
                db.setContent(content);
                
		    	// Consume the event. This means we mark it as dealt with. 
		        event.consume();
		    }
		});
		
        // This code allows the canvas to receive a dragged object within its bounds.
        // You probably don't need to change this (unless you wish to do more advanced things).
        canvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
		        // Mark the drag as acceptable if the source was the draggable image.
            	// (for example, we don't want to allow the user to drag things or files into our application)
            	if (event.getGestureSource() == draggableImage) {
    		    	// Mark the drag event as acceptable by the canvas.
            		event.acceptTransferModes(TransferMode.ANY);
    		    	// Consume the event. This means we mark it as dealt with.
            		event.consume();
            	}
            }
        });
        
        // This code allows the canvas to react to a dragged object when it is finally dropped.
        // You probably don't need to change this (unless you wish to do more advanced things).
        canvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {                
            	// We call this method which is where the bulk of the behaviour takes place.
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
}