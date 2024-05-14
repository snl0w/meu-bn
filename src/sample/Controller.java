package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {

    @FXML
    private Button cancelarButton;
    @FXML
    private Label entrarMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField senhaPasswordField;

    public void entrarButtonOnAction(ActionEvent e){


        if(emailTextField.getText().isBlank() == false && senhaPasswordField.getText().isBlank() == false){
            validarLogin();
        } else {
            //Esse método serve para avisar o usuário que ele preencheu os campos ou que não inseriu as informações corretas
            entrarMessageLabel.setText("Por favor insire o email e a senha.");
        }
    }

    //Esse método serve para fechar o programa no botão "Cancelar"
    public void cancelarButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    //Método para verificar se as informações do usuário estão corretas e dentro do banco de dados
    public void validarLogin(){
        DatabaseConnection conectarAgora = new DatabaseConnection();
        Connection connectDB = conectarAgora.getConnection();

        String verificarLogin = "SELECT count(1) FROM users WHERE email = '" + emailTextField.getText() + "' AND senha = '" + senhaPasswordField.getText() + "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verificarLogin);

            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){


                    entrarMessageLabel.setText("Bem-Vindo(a)!");
                    carregarMenu();
                    Stage stage = (Stage) cancelarButton.getScene().getWindow();
                    stage.close();

                } else {
                    //Mensagem de erro caso o email ou senha estejam incorretos
                    entrarMessageLabel.setText("Email ou senha incorretos. Tente novamente.");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void carregarMenu() {
        try {
            new Main().carregarMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
