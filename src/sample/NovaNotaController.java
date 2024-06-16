package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    // Volta ao menuBn
    public void voltarMenu() {
        TrocarCena.trocarCena((Stage) voltarButton.getScene().getWindow(), "menubn.fxml", 1280, 720);
    }

    // Salva uma nova nota no banco de dados
    @FXML
    public void salvarNota() {
        String tituloBloco = tituloBlocoField.getText();
        String nomeNota = nomeNotaField.getText();
        String conteudoNota = conteudoNotaArea.getText();

        // Validação básica dos campos (opcional)
        if (tituloBloco.isEmpty() || nomeNota.isEmpty() || conteudoNota.isEmpty()) {
            // Trate aqui se algum campo estiver vazio
            return;
        }

        try {
            Connection connection = DatabaseConnection.getConnection();

            // Verifica se o bloco existe e obtém o codBloco
            int codBloco = buscarCodBlocoPorTitulo(connection, tituloBloco);
            if (codBloco == -1) {
                // Se o bloco não existir, trate a situação adequadamente
                System.out.println("O bloco não existe. Trate esta situação conforme necessário.");
                return;
            }

            // Insere a nota
            String sql = "INSERT INTO nota (Titulo, Conteudo, codBloco) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomeNota);
            preparedStatement.setString(2, conteudoNota);
            preparedStatement.setInt(3, codBloco);

            // Executa o INSERT
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Nova nota inserida com sucesso!");
                // Aqui você pode adicionar lógica para limpar os campos ou exibir uma mensagem de sucesso
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar o código do bloco pelo título
    private int buscarCodBlocoPorTitulo(Connection connection, String tituloBloco) {
        try {
            String sql = "SELECT codBloco FROM blocodenotas WHERE Titulo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tituloBloco);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("codBloco");
            } else {
                return -1; // Retorna -1 se o bloco não for encontrado
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro
        }
    }
}
