package coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane scenePane;
    public void logout(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Logout");
            alert.setHeaderText("You're about to logout!");
            alert.setContentText("Do you want to save before exiting? ");

            if(alert.showAndWait().get() == ButtonType.OK)
            {
                stage = (Stage) scenePane.getScene().getWindow();
                System.out.println("GOOD JOB");
                stage.close();
            }
    }
    public void switchToScene1(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("actionMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToScene2(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("scoreBoard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToLevel(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Levels.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private TextField dayMessage;
    public void textfieldDisplay() throws IOException
    {
        String message;
        message = "Hello";
        dayMessage.setText(message);
    }
    public void textfieldRemoveDisplay() throws IOException
    {
        dayMessage.setText("");
    }
}