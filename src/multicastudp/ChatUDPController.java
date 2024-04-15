/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package multicastudp;

import java.io.IOError;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class ChatUDPController implements Initializable {

    @FXML
    private Label lbUsername;
    @FXML
    private TextField lbMessage;
    @FXML
    private TextArea taChat;
    
    private String user;
    private int port = 9000;  
    private String address = "224.0.0.0";
    private MulticastSocket socket;
    private InetAddress group;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nombre de Usuario");
        dialog.setHeaderText("Ingrese su nombre de usuario:");
        dialog.setContentText("Nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            user = result.get();
        } else {
            System.exit(0);
        }
        
        lbUsername.setText("Nombre de usuario: "+ user);
        taChat.setEditable(false);
            lbMessage.setOnKeyPressed(event -> {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendMessage();
        }
        });
        
         try {
            // Crear socket UDP y conectar al servidor
            
            group = InetAddress.getByName(address);
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
            Thread hiloChat = new Thread(new HiloChat(user, socket, group, port, taChat) );
            hiloChat.start();
            
            // Mensaje de sesión nueva
            String newSesion = user + " se ha unido al chat";
             sendSesionNotification(newSesion);
            
         } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }    

    @FXML
    private void clickSendMessage(ActionEvent event) {
           sendMessage();
    }
    
    
    private void sendSesionNotification(String message) throws IOException{
        byte[] bytesMessage = new byte[1024];
        bytesMessage = message.getBytes();
        DatagramPacket outgoingMessage = new DatagramPacket(bytesMessage,bytesMessage.length, group, port);
        socket.send(outgoingMessage);
        
    }
    
    
    public void handleCloseWindow() throws IOException {
        // Enviar mensaje de notificación al servidor cuando el usuario se va
        String leaveMessage = user + " se ha salido del chat";
        sendSesionNotification(leaveMessage);
    }
    
    
    private void sendMessage(){
         String message = lbMessage.getText().trim();
           if(! message.isEmpty()){
               try{
                   byte[] messageBytes = new byte[1024];
                   message = user + ": " + message;
                   messageBytes = message.getBytes();
                   DatagramPacket outgoingMessage = new 
                        DatagramPacket(messageBytes, messageBytes.length,group, port);
                   socket.send(outgoingMessage);
                   lbMessage.clear();
                   
               }catch(IOException ex){
                   System.err.println(ex.getMessage());
               }
           }
    }
}
