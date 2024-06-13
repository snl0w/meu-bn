package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NovoBNController {

    @FXML
    private Button voltarButton;

    @FXML
    private Button salvarButton;

    //Volta ao menuBn
    public void voltarMenu(){
        TrocarCena.trocarCena((Stage) voltarButton.getScene().getWindow(), "menubn.fxml", 1280, 720);
    }

}
