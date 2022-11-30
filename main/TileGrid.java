import java.sql.Array;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class TileGrid extends Application{

    static GridPane root;
    static int height;
    static int width;

    static GridPane[][] colorTiles;
    //declares the array of GridPanes
    static GridPane[][] tiles;

    static Array[][] current;

    public void start(Stage primaryStage) {

        root = new GridPane();
        height = 6;
        width = 6;
        //declares the array of tiles
        tiles = new GridPane[6][6];
        colorTiles = new GridPane[2][2];

        //set up the board
        blankBoard(root, tiles, height, width);

        //loads the blank board
        root.setGridLinesVisible(true);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void blankBoard(GridPane root, GridPane[][] tiles, int height, int width) {

        //set the color of tiles and their size
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col] = new GridPane();
                tiles[row][col].setStyle("-fx-background-color: blue;");
                //adding 4 colours to each tile
                for (int crow = 0; crow < 2; crow++) {
                    for (int ccol = 0; ccol < 2; ccol++) {
                        colorTiles[crow][ccol] = new GridPane();
                        colorTiles[crow][ccol].setStyle("-fx-background-color: red;");
                        tiles[row][col].add(colorTiles[crow][ccol], crow, ccol);
                        colorTiles[crow][ccol].setPrefSize(25, 25);
                    }
                }
                        root.add(tiles[row][col], col, row);
                        tiles[row][col].setPrefSize(100, 100);
            }
        }
        //set the color of buttons and their size
        for (int i = 0; i < height; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
        }
        for (int i = 0; i < width; i++){
            root.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        //set constraints for each tile

    }
}