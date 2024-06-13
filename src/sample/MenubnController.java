package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenubnController{

    @FXML
    private Button sairButton;

    @FXML
    private Label usuarioMessageLabel;

    @FXML
    private TableView<?> tabelaBlocos;

    @FXML
    private TableColumn<?, ?> colunaBlocos;

    @FXML
    private TableColumn<?, ?> colunaNotas;

    @FXML
    private Button novoBnButton;

    @FXML
    private Button excluirBnButton;



    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

    public void abrirNovoBn(){
        TrocarCena.trocarCena((Stage) novoBnButton.getScene().getWindow(), "novobn.fxml", 1280, 720);
    }

}
