package controller;

import DAO.AppointmentDAOImp;
import DAO.LoadForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;


import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * controller for report 1
 * @author Rui Huang
 */

public class Report1 implements Initializable {
    public ComboBox MonthCb;
    public ComboBox TypeCb;
    public Button GetReportBtn;
    public Label ReportLb;
    public Button BackBtn;
    private LoadForm goTo;
    private Parent parent;
    private AppointmentDAOImp appointmentDAOImp;
    private  String[] months={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * Initialize the form when the form is loaded
     * defining a lambda expression 'goTo' to load other form
     * @param url the url of fxml file
     * @param resourceBundle resource bundle for fxml file
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goTo=(p,e,r,t)->{
            Stage s=(Stage)((Button)(e.getSource())).getScene().getWindow();
            p= FXMLLoader.load(getClass().getResource(r));
            s.setTitle(t);
            s.setScene(new Scene(p));
            s.show();
        };

        for(int i=0;i<12;i++){
            MonthCb.getItems().add(months[i]);
        }
        MonthCb.getSelectionModel().selectFirst();
        Set<String> types=new HashSet<>();
        appointmentDAOImp= new AppointmentDAOImp();
        for(Appointment appointment:appointmentDAOImp.getAllAppointment()){
            types.add(appointment.getType());
        }
        for(String type:types){
            TypeCb.getItems().add(type);
        }
        TypeCb.getSelectionModel().selectFirst();
    }

    /**
     * Count the appointment sorted by the given month and type
     * @param event when the button is clicked
     */

    public void OnGetReportBtn(ActionEvent event) {
        int count=0;
        int month=MonthCb.getSelectionModel().getSelectedIndex()+1;
        String type=TypeCb.getValue().toString();
        for(Appointment appointment:appointmentDAOImp.getAllAppointment()){
            if ((appointment.getStartDate().getMonth().getValue()==month)&&appointment.getType().equals(type)){
                count++;
            }
        }
        ReportLb.setText(months[month-1]+" - "+type+" - "+String.valueOf(count));
    }
    /**
     * go back to main form when back button is clicked
     *  use method defined by lambda expression go to main form
     * @param event when back button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void OnBackBtn(ActionEvent event) throws IOException {

        goTo.load(parent,event,"/view/MainForm.fxml","Main Form");
    }
}
