package lk.ijse.auctionsystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientOne implements Initializable {

    @FXML
    private VBox chatBox;

    @FXML
    private TextField usernameBox;

    @FXML
    private TextField ipBox;

    @FXML
    private TextField clientMsgArea;

    @FXML
    private ScrollPane textPane;

    DataOutputStream dos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Client One UI Loaded");
    }

    @FXML
    public void connectToServer(){
        try{
            Socket socket = new Socket(ipBox.getText(),6000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            sendMsg( usernameBox.getText() + "Connected to the server");

            new Thread(()->{
                while(true){
                    try{
                        String message = dis.readUTF();
                        Platform.runLater(()->{
                            appendText(message);
                        });
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Please enter the Both Username and the Local Host IP").show();
        }
    }

    public void sendMsg(){
        try{
            int msg = Integer.parseInt(clientMsgArea.getText());
            dos.writeUTF(Integer.toString(msg));
            dos.flush();
            clientMsgArea.setText("");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try{
            dos.writeUTF(msg);
            dos.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void appendText(String msg) {
        Platform.runLater(() -> {
            Label label = new Label(msg);
            label.setWrapText(true);
            label.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 8; -fx-background-radius: 10;");
            chatBox.getChildren().add(label);
            textPane.setVvalue(1.0);
        });
    }

    @FXML
    public void disconnect(){
        System.exit(0);
    }
}
