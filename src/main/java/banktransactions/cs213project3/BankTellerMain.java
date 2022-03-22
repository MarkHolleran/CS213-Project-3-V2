package banktransactions.cs213project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankTellerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 480);
        stage.setTitle("Bank Teller");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

