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
    private Button cadastrarButton;

    private Connection connectDB;

    @FXML
    public void initialize() {
        DatabaseConnection conectarAgora = new DatabaseConnection();
        connectDB = conectarAgora.getConnection();
    }

    // Vai para o menubn quando as informações de email e senha estão corretas
    private void proximaPagina(int codUsuario, String nomeUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menubn.fxml"));
            Parent root = loader.load();

            // Obtendo o controller do MenubnController
            MenubnController menubnController = loader.getController();
            menubnController.setCodUsuario(codUsuario);
            menubnController.setNomeUsuario(nomeUsuario);

            Stage stage = (Stage) entrarButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abre a página de cadastro do usuário
    private void abrirCadastro() {
        TrocarCena.trocarCena((Stage) cadastrarButton.getScene().getWindow(), "cadastro.fxml", 800, 600);
    }

    // Botão que ao ser clicado, verifica o login do usuário
    @FXML
    public void entrarButtonOnAction(ActionEvent e) {
        if (!emailTextField.getText().isBlank() && !senhaPasswordField.getText().isBlank()) {
            validarLogin();
        } else {
            entrarMessageLabel.setText("Por favor insira o email e a senha.");
        }
    }

    // Fecha o programa
    @FXML
    public void cancelarButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    // Botão para abrir o cadastro
    @FXML
    public void cadastrarButtonOnAction(ActionEvent e) {
        abrirCadastro();
    }

    // Valida se as informações do usuário estão presentes e corretas no banco de dados
    public void validarLogin() {
        String verificarLogin = "SELECT codUsuario, Nome FROM Usuario WHERE email = ? AND Senha = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verificarLogin);
            preparedStatement.setString(1, emailTextField.getText());
            preparedStatement.setString(2, senhaPasswordField.getText());
            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                int codUsuario = queryResult.getInt("codUsuario");
                String nomeUsuario = queryResult.getString("Nome");
                proximaPagina(codUsuario, nomeUsuario);
            } else {
                entrarMessageLabel.setText("Email ou senha incorretos. Tente novamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            entrarMessageLabel.setText("Erro ao tentar fazer login: " + e.getMessage());
        }
    }
}
