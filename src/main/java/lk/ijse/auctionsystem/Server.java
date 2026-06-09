package lk.ijse.auctionsystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Server implements Initializable {

    static Scanner input = new Scanner(System.in);
    static List<Item> items = List.of(new Item("Vintage Watch",5000.00));
    static DataOutputStream dos;
    static List<DataOutputStream> dataOutputStreams = new ArrayList<>();
    static Item item = items.get(0);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Server UI Loaded");
    }

    public static void main(String[] args){
        new Thread(()->{
            try{
                ServerSocket serverSocket = new ServerSocket(6000);
                System.out.println("Server Started at Port 6000");
                while(true){
                    Socket remoteSocket = serverSocket.accept();
                    System.out.println("Client + " + remoteSocket.getLocalSocketAddress() +
                            " connected to the Server On Port 6000");

                    DataInputStream dis = new DataInputStream(remoteSocket.getInputStream());
                    dos = new DataOutputStream(remoteSocket.getOutputStream());

                    dataOutputStreams.add(dos);
                    broadcast("============The Current Bid Status============");
                    broadcast("Item Name : " + item.getItemName());
                    broadcast("Item Price : " + item.getStartPrice());

                    new Thread(()->{
                        try{
                            while(true){
                                broadcast(dis.readUTF());
                                int message = Integer.parseInt(dis.readUTF());
                                broadcastInt(message);
                                updateBidPrice(message);
                                System.out.println(message);
                                broadcast("============The Current Bid Status============" + "\nItem Name : " + item.getItemName() + "\nItem Price : " + item.getStartPrice());
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }).start();

                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }).start();

        String message = input.nextLine();
        System.out.println(message);
        if(message.equalsIgnoreCase("exit")){
            broadcast("[Auction Closed] WINNER : " + items.get(0).getStartPrice());
            System.out.println("[Auction Closed] WINNER : " + items.get(0).getStartPrice());
            System.exit(0);
        }
        broadcast("Server - " + message);
    }

    public static void broadcast(String message){
        for(DataOutputStream dos: dataOutputStreams){
            try{
                dos.writeUTF(message);
                dos.flush();
            }
            catch(Exception e){}
        }
    }

    public static void broadcastInt(int message){
        for(DataOutputStream dos: dataOutputStreams){
            try{
                Item item = items.get(0);
                if(message < item.getStartPrice()){
                    dos.writeUTF("Bid Rejected - " + message);
                    dos.writeUTF(String.valueOf(message));
                    dos.flush();
                }
                else{
                    dos.writeUTF("Bid Accepted - " + message);
                    dos.writeUTF(String.valueOf(message));
                    dos.flush();
                }
            }
            catch(Exception e){}
        }
    }

   public static void updateBidPrice(int message){
        Item item = items.get(0);
        double currentPrice = item.getStartPrice();

        if(currentPrice < message){
            items.get(0).setStartPrice(message);
            broadcast("Server - New Highest Bid Price - " + message);
        }

   }
}
