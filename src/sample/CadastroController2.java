package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CadastroController2 {

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField nomeField;

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

    public void cadastrar(ActionEvent event) throws IOException {
        DatabaseConnection connect = new DatabaseConnection();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = connect.getConnection();

            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = senhaField.getText();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                entrarMessageLabel.setText("Por favor preencha todos os campos.");
            } else {
                String query = "SELECT * FROM usuario WHERE EMAIL = ?";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, email);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    entrarMessageLabel.setText("Email já está em uso.");
                } else {
                    String query_2 = "INSERT INTO usuario (nome, email, senha) VALUES(?,?,?)";
                    preparedStatement = con.prepareStatement(query_2);

                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, senha);

                    preparedStatement.executeUpdate();

                    entrarMessageLabel.setText("Conta criada com sucesso!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            entrarMessageLabel.setText("Erro ao criar conta. Tente novamente.");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        clearFields();
    }

    public void clearFields() {
        nomeField.setText("");
        emailField.setText("");
        senhaField.setText("");
    }

    private void abrirLogin() {
        TrocarCena.trocarCena((Stage) loginButton.getScene().getWindow(), "login.fxml", 800, 600);
    }

    @FXML
    private void loginButonOnAction() {
        abrirLogin();
    }

    @FXML
    private void cancelarButtonOnAction() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
