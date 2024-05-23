package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenubnController {

    @FXML
    private Button sairButton;

    @FXML
    public void initialize() {
        // Qualquer inicialização necessária
    }

    @FXML
    public void sairButtonOnAction() {
        // Lógica para o botão Sair
        System.out.println("Saindo...");
    }
}
