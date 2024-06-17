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

public class MenubnController {

    @FXML
    private Button sairButton;

    @FXML
    private Label usuarioMessageLabel;

    @FXML
    private TableView<BlocoDeNotas> tabelaBlocos;

    @FXML
    private TableColumn<BlocoDeNotas, String> colNomeBlocos;

    @FXML
    private Button novaNotaButton;

    @FXML
    private Button novoBlocoButton;

    @FXML
    private Button excluirBnButton;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * Inicializa o controlador.
     */
    public void initialize() {
        try {
            carregarInformacoesTabela();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os blocos de notas na tabelaBlocos.
     */
    public void carregarInformacoesTabela() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
            ObservableList<BlocoDeNotas> listaBloco = FXCollections.observableArrayList();

            String query = "SELECT * FROM blocodenotas";
            resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                BlocoDeNotas blocoDeNotas = new BlocoDeNotas();
                blocoDeNotas.setCodBloco(resultSet.getInt("codBloco"));
                blocoDeNotas.setTitulo(resultSet.getString("Titulo"));
                listaBloco.add(blocoDeNotas);
            }

            colNomeBlocos.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
            tabelaBlocos.setItems(listaBloco);

            // Adiciona um listener para tratar a seleção de blocos de notas
            tabelaBlocos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        carregarNotas(newSelection.getCodBloco());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carrega as notas do bloco de notas selecionado na tabelaNotas.
     */
    private void carregarNotas(int codBloco) throws SQLException {
        ObservableList<Notas> listaNotas = FXCollections.observableArrayList();

        String query = "SELECT * FROM notas WHERE codBloco = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, codBloco);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Notas notas = new Notas();
            notas.setCodNota(resultSet.getInt("codNota"));
            notas.setCodBloco(resultSet.getInt("codBloco"));
            notas.setTitulo(resultSet.getString("Titulo"));
            notas.setConteudo(resultSet.getString("Conteudo"));
            listaNotas.add(notas);
        }

    }

    /**
     * Fecha o programa ao clicar no botão sairButton.
     */
    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Abre a tela para inserir o título de um novo bloco de notas.
     */
    @FXML
    public void abrirNovoBloco(ActionEvent e) {
        TrocarCena.trocarCena((Stage) novoBlocoButton.getScene().getWindow(), "novobloco.fxml", 1280, 720);
    }

    /**
     * Abre a tela da criação de uma nova nota para um bloco de notas.
     */
    @FXML
    public void abrirNovaNota(ActionEvent e) {
        TrocarCena.trocarCena((Stage) novaNotaButton.getScene().getWindow(), "novanota.fxml", 1280, 720);
    }
}
