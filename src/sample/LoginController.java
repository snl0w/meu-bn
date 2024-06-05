package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button cancelarButton;
    @FXML
    private Label entrarMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField senhaPasswordField;
    @FXML
    private Button entrarButton;
    @FXML
    private Button cadastrarButton;

    private void goToNextPage() {
        TrocarCena.trocarCena((Stage) entrarButton.getScene().getWindow(), "menubn.fxml", 1280, 720);
    }

    private void abrirCadastro() {
        TrocarCena.trocarCena((Stage) cadastrarButton.getScene().getWindow(), "cadastro2.fxml", 800, 600);
    }

    @FXML
    public void entrarButtonOnAction(ActionEvent e) {
        if (!emailTextField.getText().isBlank() && !senhaPasswordField.getText().isBlank()) {
            validarLogin();
        } else {
            entrarMessageLabel.setText("Por favor insira o email e a senha.");
        }
    }

    @FXML
    public void cancelarButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cadastrarButtonOnAction(ActionEvent e) {
        abrirCadastro();
    }

    public void validarLogin() {
        DatabaseConnection conectarAgora = new DatabaseConnection();
        Connection connectDB = conectarAgora.getConnection();

        String verificarLogin = "SELECT count(1) FROM Usuario WHERE email = ? AND Senha = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verificarLogin);
            preparedStatement.setString(1, emailTextField.getText());
            preparedStatement.setString(2, senhaPasswordField.getText());
            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next() && queryResult.getInt(1) == 1) {
                goToNextPage();
            } else {
                entrarMessageLabel.setText("Email ou senha incorretos. Tente novamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            entrarMessageLabel.setText("Erro ao tentar fazer login: " + e.getMessage());
        }
    }


}
