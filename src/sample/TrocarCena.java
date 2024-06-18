package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrocarCena{
    public static void trocarCena(Stage stage, String fxmlFile, int width, int height){
        try{
            Parent root = FXMLLoader.load(TrocarCena.class.getResource(fxmlFile));
            stage.setScene((new Scene(root, width, height)));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void trocarCena(Stage stage, String fxmlFile, int width, int height, BlocoDeNotas blocoSelecionado) {
        try{
            Parent root = FXMLLoader.load(TrocarCena.class.getResource(fxmlFile));
            stage.setScene((new Scene(root, width, height)));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
