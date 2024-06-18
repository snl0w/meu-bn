package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String FXML_FILE = "login.fxml";

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o arquivo FXML
            Parent root = FXMLLoader.load(getClass().getResource(FXML_FILE));

            // Configura o estilo da janela
            configureStage(primaryStage, root);

            // Exibe a janela
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Trata a exceção imprimindo o stack trace
        }
    }

    private void configureStage(Stage stage, Parent root) {
        // Inicializa o estilo da janela como não decorada
        stage.initStyle(StageStyle.UNDECORATED);

        // Define a cena com largura e altura específicas
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
    }

    //Main
    public static void main(String[] args) {
        launch(args);
    }
}
