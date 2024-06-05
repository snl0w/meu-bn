package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenubnController {

    @FXML
    private Button sairButton;

    @FXML
    public void sairButtonOnAction() {
    }

    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

}
