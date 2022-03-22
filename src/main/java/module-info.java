module banktransactions.cs213project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens banktransactions.cs213project3 to javafx.fxml;
    exports banktransactions.cs213project3;
}