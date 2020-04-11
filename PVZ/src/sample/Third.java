package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Third {

    @FXML
    void ReturnToSecondScene(ActionEvent event) throws IOException {
        Parent SecondStage= FXMLLoader.load(getClass().getResource("Second.fxml"));
        Scene SecondScene=new Scene(SecondStage);
        Stage SecondWindow=(Stage)((Node)event.getSource()).getScene().getWindow();
        SecondWindow.setScene(SecondScene);
        SecondWindow.show();
    }

}
