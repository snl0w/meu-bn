package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NovoBlocoController {

    @FXML
    private Button voltarButton;

    @FXML
    private Button salvarButton;

    @FXML
    private TextField tituloBlocoField;

    // Volta ao menuBn
    public void voltarMenu() {
        TrocarCena.trocarCena((Stage) voltarButton.getScene().getWindow(), "menubn.fxml", 1280, 720);
    }

    // Salva o título no banco de dados
    public void salvarTitulo() {
        String titulo = tituloBlocoField.getText().trim();

        if (!titulo.isEmpty()) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO blocodenotas (Titulo, codUsuario) VALUES (?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, titulo);
                statement.setInt(2, 1); // Substitua 1 pelo código do usuário logado ou obtenha dinamicamente

                statement.executeUpdate();
                System.out.println("Título salvo com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao salvar título: " + e.getMessage());
            }
        } else {
            System.out.println("Campo de título está vazio.");
        }
    }
}
