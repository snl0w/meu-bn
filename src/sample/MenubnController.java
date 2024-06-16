package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenubnController{

    @FXML
    private Button sairButton;

    @FXML
    private Label usuarioMessageLabel;

    @FXML
    private TableView<BlocoDeNotas> tabelaBlocos;

    @FXML
    private TableColumn<BlocoDeNotas, String> colNomeBlocos;

    @FXML
    private TableView<Notas> tabelaNotas;

    @FXML
    private TableColumn<Notas, String> colNomeNotas;

    @FXML
    private Button novaNotaButton;

    @FXML
    private Button novoBlocoButton;

    @FXML
    private Button excluirBnButton;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void initialize() {
        try {
            carregarBlocos();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tabelaBlocos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    carregarNotas(newSelection.getCodBloco());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Carrega os blocos de notas na tabelaBlocos
    public void carregarBlocos() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
            ObservableList<BlocoDeNotas> listaBloco = FXCollections.observableArrayList();

            query = "SELECT * FROM blocodenotas";
            resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                BlocoDeNotas blocoDeNotas = new BlocoDeNotas();
                blocoDeNotas.setCodBloco(resultSet.getInt("codBloco"));
                blocoDeNotas.setTitulo(resultSet.getString("Titulo"));
                listaBloco.add(blocoDeNotas);
            }

            colNomeBlocos.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
            tabelaBlocos.setItems(listaBloco);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Método para carregar as notas na tabela notas, mas não está funcionando
    public void carregarNotas(int codBloco) throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
            ObservableList<Notas> listaNotas = FXCollections.observableArrayList();

            query = "SELECT * FROM nota WHERE codBloco = ?";
            preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setInt(1, codBloco);
            resultSet = preparedStatement.executeQuery();

            System.out.println("Query executada: " + query); // Debug para verificar a consulta SQL

            while (resultSet.next()) {
                Notas nota = new Notas();
                nota.setTitulo(resultSet.getString("Titulo"));
                listaNotas.add(nota);
            }

            colNomeNotas.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
            tabelaNotas.setItems(listaNotas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Ao clicar em sair, vai fechar o programa
    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

    //Ao clicar no botão Novo Bn, vai abrir uma tela para inserir o titulo de um novo bloco de notas
    @FXML
    public void abrirNovoBloco(ActionEvent e){
        TrocarCena.trocarCena((Stage) novoBlocoButton.getScene().getWindow(), "novobloco.fxml", 1280, 720);
    }

    //Ao clicar no botão Nova Nota, vai abrir a tela da criação de uma nova nota para um bloco de notas
    @FXML
    public void abrirNovaNota(ActionEvent e){
        TrocarCena.trocarCena((Stage) novaNotaButton.getScene().getWindow(), "novanota.fxml", 1280, 720);
    }

}
