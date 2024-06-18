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
    private Button excluirBnButton;

    private String nomeUsuario;

    String query = null;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private int codUsuario;

    //Inicializa o controlador
    public void initialize() {
        try {
            carregarInformacoesTabela();
            carregarNomeUsuario();
            usuarioMessageLabel.setText(nomeUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Carrega os blocos de notas na tabelaBlocos
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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Método para carregar o nome do usuário que fez login
    private void carregarNomeUsuario() throws SQLException {
        String query = "SELECT Nome FROM usuario WHERE codUsuario = ?"; // Ajuste a consulta conforme necessário
        int codUsuario = 1; // Substitua pelo código do usuário autenticado

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, codUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nomeUsuario = rs.getString("Nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Fecha o programa ao clicar no botão sairButton
    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

    //Abre a tela da criação de um novo bloco de notas para um novo bloco e uma nota
    @FXML
    public void abrirNovaNota(ActionEvent e) {
        TrocarCena.trocarCena((Stage) novaNotaButton.getScene().getWindow(), "novanota.fxml", 1280, 720);
    }

    @FXML
    public void excluirBlocoNota(ActionEvent e) {
        BlocoDeNotas blocoSelecionado = tabelaBlocos.getSelectionModel().getSelectedItem();

        if (blocoSelecionado != null) {
            try {
                connection = DatabaseConnection.getConnection();
                String query = "DELETE FROM blocodenotas WHERE codBloco = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, blocoSelecionado.getCodBloco());
                preparedStatement.executeUpdate();

                // Remover o bloco excluído da tabela
                tabelaBlocos.getItems().remove(blocoSelecionado);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void setCodUsuario(int codUsuario){
        this.codUsuario = codUsuario;
    }

}
