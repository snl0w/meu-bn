package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
                entrarMessageLabel.setText("Login bem-sucedido!");
                goToNextPage();
            } else {
                entrarMessageLabel.setText("Email ou senha incorretos. Tente novamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            entrarMessageLabel.setText("Erro ao tentar fazer login: " + e.getMessage());
        }
    }

    private void goToNextPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menubn.fxml"));
            Stage stage = (Stage) entrarButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            entrarMessageLabel.setText("Erro ao carregar a próxima página: " + e.getMessage());
        }
    }
}
