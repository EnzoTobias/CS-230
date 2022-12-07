package com.example.newproject;
/** imports */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;

/** this class contains all the methods the behaving methods for the menu class.
 * @Bader Shalata - 2221254
 * Date - Sep-Dec */
public class HelloController extends HelloApplication{
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextField Player;
    @FXML
    private TextField dayMessage;


    /**
     * playerName method handles the input for player name and stores it in a variable.
     */
    @FXML
    private TextField textarea123;
    public void playerName()
    {
        String userInput = Player.getText();
        textarea123.setText(userInput);
    }


    /**
     * this logout method verifies that the user wants to exit for sure.
     *
     * @param - ActionEvent event
     */
    public void logout(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting? ");

        if (alert.showAndWait().get() == ButtonType.OK)
        {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("GOOD JOB");
            stage.close();
        }
    }

    /**
     * this method allows us to switch scenes.
     *
     * @param - ActionEvent event
     * @throws IOException if file does not exist where it's assigned.
     */
    public void switchToScene1(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("actionMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this method allows us to switch back.
     *
     * @param - ActionEvent event
     * @throws IOException if file does not exist where it's assigned.
     */
    public void switchToScene2(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("scoreBoard.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this method allows us to switch to level menu.
     *
     * @param - ActionEvent event
     * @throws IOException if file does not exist where it's assigned.
     */
    public void switchToLevel(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Levels.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    /**
     * method that takes message of the day input and outputs it.
     *
     * @param - (message) ADD MESSAGE OF THE DAY.
     * @throws IOException if file does not exist where it's assigned.
     */
    public void textfieldDisplay() throws IOException
    {
        String message;
        message = "Hello";
        dayMessage.setText(message);
    }

    /**
     * method that removes text display
     * @throws IOException if file does not exist where it's assigned.
     */
    public void textfieldRemoveDisplay() throws IOException
    {
        dayMessage.setText("");
    }
}