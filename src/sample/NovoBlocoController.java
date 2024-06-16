package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class NovoBlocoController {

    @FXML
    private Button voltarButton;

    @FXML
    private Button salvarButton;

    @FXML
    private TextField tituloBlocoField;

    // Volta ao menuBn
    public void voltarMenu() {
        TrocarCena.trocarCena((Stage) voltarButton.getScene().getWindow(), "menubn.fxml", 1280, 720);
    }



}
