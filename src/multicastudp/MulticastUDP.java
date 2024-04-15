/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package multicastudp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jasie
 */
public class MulticastUDP extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatUDP.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Obtener el controlador de la vista
        ChatUDPController controller = loader.getController();

        // Configurar el manejo del cierre de la ventana
        stage.setOnCloseRequest(event -> {
           try {
               controller.handleCloseWindow();
           } catch (IOException ex) {
               Logger.getLogger(MulticastUDP.class.getName()).log(Level.SEVERE, null, ex);
           }
        });

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
