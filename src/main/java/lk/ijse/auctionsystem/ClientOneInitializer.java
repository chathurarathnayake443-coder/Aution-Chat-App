package lk.ijse.auctionsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientOneInitializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("client-one.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 691, 470);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
