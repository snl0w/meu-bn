package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private TabPane tabPane;

    @FXML
    private Tab atualizarTab;

    @FXML
    private Button novaNotaButton;

    @FXML
    private Button excluirBnButton;

    @FXML
    private Button atualizarButton;

    @FXML
    private TextField tituloBlocoField;

    @FXML
    private TextField nomeNotaField;

    @FXML
    private TextArea conteudoNotaArea;

    @FXML
    private Button atualizarNotaButton;

    @FXML
    private Label mensagemLabel;

    private String nomeUsuario;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private int codUsuario;


    //Inicializador que vai carregar as informações do bloco de notas
    public void initialize() {
        try {
            carregarInformacoesTabela();
            carregarNomeUsuario();
            usuarioMessageLabel.setText(nomeUsuario);

            tabelaBlocos.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    abrirBlocoNota();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Carrega as informações do bloco de notas na tabela
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

    //Carrega o nome do usuário no perfil
    private void carregarNomeUsuario() throws SQLException {
        String query = "SELECT Nome FROM usuario WHERE codUsuario = ?";
        int codUsuario = 1;

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

    //Fecha o programa
    @FXML
    public void sairButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) sairButton.getScene().getWindow();
        stage.close();
    }

    //Abre outra janela para a criação de uma nova nota em um bloco de notas
    @FXML
    public void abrirNovaNota(ActionEvent e) {
        TrocarCena.trocarCena((Stage) novaNotaButton.getScene().getWindow(), "novanota.fxml", 1280, 720);
    }

    //Exclui um bloco de notas selecionado
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

                tabelaBlocos.getItems().remove(blocoSelecionado);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    //Abre outra aba quando clicado duas vezes em cima de um item ou ao clicar no item e em seguida no botão "Atualizar"
    @FXML
    public void abrirBlocoNota() {
        BlocoDeNotas blocoSelecionado = tabelaBlocos.getSelectionModel().getSelectedItem();

        if (blocoSelecionado != null) {
            try {
                carregarInformacoesBlocoNota(blocoSelecionado);
                tabPane.getSelectionModel().select(atualizarTab);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Carrega as informações das notas
    private void carregarInformacoesBlocoNota(BlocoDeNotas bloco) throws SQLException {
        String query = "SELECT * FROM notas WHERE codBloco = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bloco.getCodBloco());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tituloBlocoField.setText(bloco.getTitulo());
                nomeNotaField.setText(rs.getString("nome"));
                conteudoNotaArea.setText(rs.getString("conteudo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Atualiza o bloco e a nota após clicado em "Atualizar"
    @FXML
    public void atualizarNota(ActionEvent e) {
        BlocoDeNotas blocoSelecionado = tabelaBlocos.getSelectionModel().getSelectedItem();

        if (blocoSelecionado != null) {
            try {
                connection = DatabaseConnection.getConnection();
                String query = "UPDATE blocodenotas SET titulo = ? WHERE codBloco = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, tituloBlocoField.getText());
                preparedStatement.setInt(2, blocoSelecionado.getCodBloco());
                preparedStatement.executeUpdate();

                query = "UPDATE notas SET nome = ?, conteudo = ? WHERE codBloco = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nomeNotaField.getText());
                preparedStatement.setString(2, conteudoNotaArea.getText());
                preparedStatement.setInt(3, blocoSelecionado.getCodBloco());
                preparedStatement.executeUpdate();

                //Mensagem de sucesso caso o bloco e a nota tenha sido salvos com sucesso
                mensagemLabel.setText("Bloco e nota atualizada com sucesso!");
                clearFields();
                carregarInformacoesTabela();

                //Mensagem desaparece depois de 4 segundos
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), evt -> mensagemLabel.setText("")));
                timeline.play();
            } catch (SQLException ex) {
                ex.printStackTrace();
                mensagemLabel.setText("Erro ao atualizar a nota.");
            }
        } else {
            mensagemLabel.setText("Nenhum bloco de notas selecionado.");
            //Mensagem desaparece depois de 4 segundos
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), evt -> mensagemLabel.setText("")));
            timeline.play();
        }
    }

    //Método para limpar os campos titulo, nome da nota e conteudo
    public void clearFields() {
        tituloBlocoField.setText("");
        nomeNotaField.setText("");
        conteudoNotaArea.setText("");
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }
}
