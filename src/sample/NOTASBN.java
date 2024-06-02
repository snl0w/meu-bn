package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NOTASBN {

    @FXML
    private VBox buttonContainer;

    @FXML
    private Button addButton;

    @FXML
    private TextField buttonNameField;

    private int buttonCount = 0;
    private GridPane gridPane;

    @FXML
    public void initialize() {
        gridPane = new GridPane();
        buttonContainer.getChildren().add(gridPane);
    }

    @FXML
    private void handleAddButton() {
        String buttonName = buttonNameField.getText().trim();
        if (buttonName.isEmpty()) {
            buttonName = "Button " + (++buttonCount);
        } else {
            buttonNameField.clear();
        }

        Button newButton = new Button(buttonName);
        newButton.setPrefSize(80, 30); // Ajustar o tamanho dos botões conforme necessário
        int row = (buttonCount - 1) / 3; // Número de colunas
        int col = (buttonCount - 1) % 3;
        gridPane.add(newButton, col, row);
    }
}
