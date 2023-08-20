package main;


import DAO.JDBC;
//import com.sun.javafx.runtime.VersionInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Main class that load the application
 * @author Rui Huang
 */

public class Main extends Application {

    /**
     * load main form
     */
    @Override
    public void start(Stage stage) throws Exception {
        JDBC.openConnection();

        System.out.println("JavaFX Version: " + System.getProperty("javafx.version"));
        System.out.println("JavaFX Runtime Version: " + System.getProperty("javafx.runtime.version"));
        FXMLLoader fxmlLoader= new FXMLLoader(Main.class.getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main method that load the application
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
            launch(args);

    }
}
