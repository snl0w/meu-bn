package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NovaNotaController {

    @FXML
    private Button voltarButton;

    @FXML
    private Button salvarButton;

    @FXML
    private TextField tituloBlocoField;

    @FXML
    private TextField nomeNotaField;

    @FXML
    private TextArea conteudoNotaArea;

    @FXML
    private Label mensagemSalvamento;

    // Volta ao menu
    public void voltarMenu() {
        TrocarCena.trocarCena((Stage) voltarButton.getScene().getWindow(), "menubn.fxml", 1280, 720);
    }

    // Salva o título do bloco de notas, nome da nota e conteúdo no banco de dados
    public void salvarNota() {
        String tituloBloco = tituloBlocoField.getText().trim();
        String nomeNota = nomeNotaField.getText().trim();
        String conteudo = conteudoNotaArea.getText().trim();

        if (!tituloBloco.isEmpty() && !nomeNota.isEmpty() && !conteudo.isEmpty()) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false); // Inicia a transação

                try {
                    // Primeiro, insere o título do bloco de notas
                    String queryTitulo = "INSERT INTO blocodenotas (titulo, codUsuario) VALUES (?, ?)";
                    PreparedStatement statementTitulo = conn.prepareStatement(queryTitulo, PreparedStatement.RETURN_GENERATED_KEYS);
                    statementTitulo.setString(1, tituloBloco);
                    statementTitulo.setInt(2, 1); // Substitua 1 pelo código do usuário logado ou obtenha dinamicamente
                    statementTitulo.executeUpdate();

                    // Obtém o ID do bloco recém-criado
                    ResultSet generatedKeys = statementTitulo.getGeneratedKeys();
                    int blocoId = 0;
                    if (generatedKeys.next()) {
                        blocoId = generatedKeys.getInt(1);
                    }

                    // Em seguida, insere o nome da nota e o conteúdo associado ao bloco
                    String queryNota = "INSERT INTO notas (codBloco, nome, conteudo, codUsuario) VALUES (?, ?, ?, ?)";
                    PreparedStatement statementNota = conn.prepareStatement(queryNota);
                    statementNota.setInt(1, blocoId);
                    statementNota.setString(2, nomeNota);
                    statementNota.setString(3, conteudo);
                    statementNota.setInt(4, 1); // Substitua 1 pelo código do usuário logado ou obtenha dinamicamente
                    statementNota.executeUpdate();

                    conn.commit(); // Confirma a transação
                    mensagemSalvamento.setText("Nota salva com sucesso!");
                    clearFields();
                } catch (SQLException e) {
                    conn.rollback(); // Reverte a transação em caso de erro
                    mensagemSalvamento.setText("Erro ao salvar nota.");
                } finally {
                    conn.setAutoCommit(true); // Retorna ao modo de commit automático
                }
            } catch (SQLException e) {
                mensagemSalvamento.setText("Erro!");
            }
        } else {
            mensagemSalvamento.setText("Preencha todos os campos!");
        }
    }

    public void clearFields() {
        tituloBlocoField.setText("");
        nomeNotaField.setText("");
        conteudoNotaArea.setText("");
    }

}
