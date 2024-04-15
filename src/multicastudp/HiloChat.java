/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multicastudp;

/**
 *
 * @author jasie
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javafx.scene.control.TextArea;

public class HiloChat implements Runnable{
    
    private String username;
    private MulticastSocket socket;
    private InetAddress grupo;
    private int puerto;
    private TextArea chatArea;


    public HiloChat( String name,MulticastSocket socket, InetAddress grupo, int puerto, TextArea chatArea ){
        this.username = name;
        this.socket = socket;
        this.grupo = grupo;
        this.puerto = puerto;
        this.chatArea = chatArea;
    }
    
    
    public void run(){
        try {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                socket.receive(messageIn);

                String receivedMessage = new String(messageIn.getData(), 0, messageIn.getLength());
                chatArea.appendText(receivedMessage + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
