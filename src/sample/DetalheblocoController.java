package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalheblocoController {

    @FXML
    private TextField tituloBlocoField;

    @FXML
    private TextArea conteudoNotaArea;

    @FXML
    private TextField nomeNotaField;

    @FXML
    private Button atualizarButton;

    @FXML
    private Button voltarButton;

    @FXML
    private Label mensagemSalvamentoLabel;

    @FXML
    private Label conteudoLabel;

    private BlocoDeNotas blocoDeNotas;
    private Notas notas;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void setNotas(ActionEvent e) {
        this.notas = notas;
        carregarDetalhes();
    }

    public void setBlocoDeNotas(BlocoDeNotas blocoDeNotas) {
        this.blocoDeNotas = blocoDeNotas;
        carregarDetalhes();
    }

    private void carregarDetalhes() {
        if (blocoDeNotas != null && notas != null) {
            tituloBlocoField.setText(blocoDeNotas.getTitulo());
            nomeNotaField.setText(notas.getTitulo());
            conteudoNotaArea.setText(notas.getConteudo());
        }
    }

    @FXML
    private void salvarNovaNota() {
        if (blocoDeNotas != null && notas != null) {
            blocoDeNotas.setTitulo(tituloBlocoField.getText());
            notas.setTitulo(nomeNotaField.getText());
            notas.setConteudo(conteudoNotaArea.getText());

            try {
                connection = DatabaseConnection.getConnection();
                String query = "UPDATE blocodenotas SET Titulo = ?, NomeNota = ?, Conteudo = ? WHERE codBloco = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, blocoDeNotas.getTitulo());
                preparedStatement.setString(2, notas.getTitulo());
                preparedStatement.setString(3, notas.getConteudo());
                preparedStatement.setInt(4, blocoDeNotas.getCodBloco());
                preparedStatement.executeUpdate();

                mensagemSalvamentoLabel.setText("Bloco de notas atualizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
                mensagemSalvamentoLabel.setText("Erro ao atualizar o bloco de notas.");
            }
        }
    }

    @FXML
    private void voltarMenu(ActionEvent event) {
        TrocarCena.trocarCena((Stage) voltarButton.getScene().getWindow(), "menubn.fxml", 1280, 720); // Ajuste conforme necessário
    }
}
