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
    private TableView<BlocoDeNotas> tvBloco;

    @FXML
    private TableColumn<BlocoDeNotas, String> tcTitulo;


    private ObservableList<BlocoDeNotas> data;



    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        data = FXCollections.observableArrayList(
                new BlocoDeNotas("Primeiro Bloco")
        );
        tvBloco.setItems(data);
    }
}
