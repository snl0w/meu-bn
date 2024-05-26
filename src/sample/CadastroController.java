package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroController {

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button cadastrarButton;

    @FXML
    private Label entrarMessageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private void cadastrarButtonOnAction(ActionEvent event) {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            entrarMessageLabel.setText("Todos os campos devem ser preenchidos.");
            return;
        }

        String insertSQL = "INSERT INTO usuario (nome, telefone, email, senha) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, telefone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, senha);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                entrarMessageLabel.setText("Cadastrado com sucesso!");
                clearFields();
            } else {
                entrarMessageLabel.setText("Falha ao cadastrar usuário. Tente novamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            entrarMessageLabel.setText("Erro ao acessar o banco de dados.");
        }
    }

    private void clearFields() {
        nomeField.clear();
        telefoneField.clear();
        emailField.clear();
        senhaField.clear();
    }

    private void abrirLogin() {
        TrocarCena.trocarCena((Stage) loginButton.getScene().getWindow(), "login.fxml", 800, 600);
    }

    @FXML
    private void loginButonOnAction(){
        abrirLogin();
    }

    @FXML
    private void cancelarButtonOnAction(){
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

}
