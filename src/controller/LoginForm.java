package controller;

import DAO.AppointmentDAOImp;
import DAO.JDBC;
import DAO.LoadForm;
import DAO.UserDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.*;

/**
 * controller class for login form
 * @author  Rui Huang
 */

public class LoginForm implements Initializable {
    public Label TitleLb;
    public Label UserNameLb;
    public TextField UserNameTxf;
    public Label PasswordLb;
    public TextField PasswordTxf;
    public Button LoginBtn;
    public Button ExitBtn;
    public Label LanguageLb;
    public Label ZoneIdLb;
    private Parent parent;
    private LoadForm goTo;
    private AppointmentDAOImp appointmentDAOImp= new AppointmentDAOImp();
    ResourceBundle rb;

    /**
     * initialize method that initialize the form
     * @param url fxml file url
     * @param resourceBundle resource bundle for fxml
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       rb= ResourceBundle.getBundle("ResourceBundle/Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            TitleLb.setText(rb.getString("Title"));
            UserNameLb.setText(rb.getString("UserName"));
            PasswordLb.setText(rb.getString("Password"));
            LoginBtn.setText(rb.getString("Login"));
            ExitBtn.setText(rb.getString("Exit"));
        }
        /**
         * define the lambda expressing to go to another form
         */

        goTo=(p,e,r,t)->{
            Stage s=(Stage)((Button)(e.getSource())).getScene().getWindow();
            p=FXMLLoader.load(getClass().getResource(r));
            s.setTitle(t);
            s.setScene(new Scene(p));
            s.show();
        };
        ZoneIdLb.setText(ZoneId.systemDefault().toString());
    }

    /**
     * log in method that check if the user name and  password combination in the data base
     * @param actionEvent when button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnLoginBtn(ActionEvent actionEvent) throws IOException {
        UserDAOImp userDAOImp=new UserDAOImp();
        String filename="login_activity.txt";
        FileWriter fileWriter= new FileWriter(filename,true);
        PrintWriter printWriter= new PrintWriter(fileWriter);

        if(userDAOImp.isValidUser(UserNameTxf.getText(),PasswordTxf.getText())) {
            goTo.load(parent,actionEvent,"/view/MainForm.fxml","Main Form");
            printWriter.println("User "+UserNameTxf.getText()+" successfully logged in at "+ LocalDateTime.now().toString());
            printWriter.close();
            if (appointmentDAOImp.upComingAppointment().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No Up Coming Appointment");
                alert.show();
            } else {
                ArrayList<Appointment> upcoming = appointmentDAOImp.upComingAppointment();
                String list = "";
                for (Appointment appointment : upcoming) {
                    list = "Upcoming Appointment: \n" +
                            "Id: " + String.valueOf(appointment.getAppointmentId()) + "\n" +
                            "Title: " + appointment.getTitle() + "\n" +
                            "Date: " + appointment.getStartDate().toLocalDate().toString() + "\n" +
                            "Time: " + appointment.getStartDate().toLocalTime().toString() + "\n" + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.WARNING, list);
                alert.show();
            }
        }else {
            String invalidUserorpassword="Invalid User Name or Password";
            if(Locale.getDefault().getLanguage().equals("fr")){
                invalidUserorpassword=rb.getString("InvalidUserOrPassword");
            }
            Alert alert= new Alert(Alert.AlertType.ERROR,invalidUserorpassword);
            alert.showAndWait();
            printWriter.println("User "+UserNameTxf.getText()+" invalid log-in in at "+ LocalDateTime.now().toString());
            printWriter.close();
        }
    }

    /**
     * exit the program
     * @param actionEvent when exit button is clicked
     */


    public void OnExitBtn(ActionEvent actionEvent) {
        String areyousure="Are you sure?";
        if (Locale.getDefault().getLanguage().equals("fr")){
            areyousure=rb.getString("AreYouSure");
        }
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,areyousure);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            JDBC.closeConnection();
            System.exit(0);
        }
    }
}
