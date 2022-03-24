package banktransactions.cs213project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that creates a Stage from Application
 * in which the fxml file consisting of the GUI code
 * is set to and shown. The GUI is launched and
 * displayed.
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class BankTellerMain extends Application {

    /**
     * Constructor for Creating BanktellerMain
     */
    public BankTellerMain (){}

    /**
     * Creates a new scene using fxml file
     * then the scene is added to the scene
     *
     * @param stage Top level container that hosts a scene which consists of all visual elements of the GUI
     * @throws IOException if the input FXML file has an error an exception is thrown
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 480);
        stage.setTitle("Bank Teller");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for using the launch() functionality to launch the GUI
     *
     * @param args Main method
     */
    public static void main(String[] args) {
        launch();
    }
}

