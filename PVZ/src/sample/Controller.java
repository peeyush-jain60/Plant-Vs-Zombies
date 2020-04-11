package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    void Credit(ActionEvent event) {
        System.out.println("Credit");
    }

    @FXML
    void Exit(ActionEvent event) {
        System.out.println("Exit");
    }

    @FXML
    void LoadGame(ActionEvent event) {
        System.out.println("Load Game");
    }

    @FXML
    void NewGame(ActionEvent event) throws IOException {
        Parent SecondStage= FXMLLoader.load(getClass().getResource("Second.fxml"));
        Scene SecondScene=new Scene(SecondStage);
        Stage SecondWindow=(Stage)((Node)event.getSource()).getScene().getWindow();
        SecondWindow.setScene(SecondScene);
        SecondWindow.show();
    }

}
