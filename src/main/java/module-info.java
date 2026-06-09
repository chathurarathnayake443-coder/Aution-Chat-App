module lk.ijse.auctionsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens lk.ijse.auctionsystem to javafx.fxml;
    exports lk.ijse.auctionsystem;
}