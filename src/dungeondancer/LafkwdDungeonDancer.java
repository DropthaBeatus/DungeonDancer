/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author beatus
 */
public class LafkwdDungeonDancer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartFXML.fxml"));
        Parent root = loader.load();
        StartFXMLController controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
        controller.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
