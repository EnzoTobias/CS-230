package coursework;
/**
 @Author Bader Shalata - 2221254
 @Date Sep-Dec
 */
/** imports */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/** this class is the main launching class for the menu  */
public class HelloApplication extends Application
{
   /** @throws IOException if file does not exist where it's assigned.
    *  @param - Stage parameter */
   @FXML
    @Override
    public void start(Stage stage) throws IOException
   {
        Parent root = FXMLLoader.load(getClass().getResource("actionMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
        Sound.staticSound.menuMusic();
        stage.setOnCloseRequest
                (event ->
        {
            event.consume();
            logout(stage);
        }       );
    }
/** this logout method verifies that the user wants to exit for sure.
 * @param - Stage parameter. */
    public void logout(Stage stage)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting? ");

        if(alert.showAndWait().get() == ButtonType.OK)
        {
            System.out.println("GOOD JOB");
            stage.close();
        }


    }
    /** Main class that launches the app. */
    public static void main(String[] args) {
        launch();
    }
}