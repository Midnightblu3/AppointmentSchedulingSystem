package controller;

import DAO.AppointmentDAOImp;
import DAO.ContactDAOImp;
import DAO.LoadForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;
/**
 * controller for report 2
 * @author Rui Huang
 */

public class Report2 implements Initializable {
    public ComboBox ContactCb;
    public Button GetReportBtn;
    public Button BackBtn;
    public TableColumn<Appointment,Integer> AppIdCol;
    public TableColumn<Appointment,String> AppTitleCol;
    public TableColumn<Appointment,String> AppDescripCol;
    public TableColumn<Appointment, LocalDateTime> AppStartDateTime;
    public TableColumn<Appointment,LocalDateTime> AppEndDateTime;
    public TableColumn<Appointment,String> AppTypeCol;
    public TableColumn<Appointment,Integer> AppCustIdCol;
    public TableView AppTable;
    private LoadForm goTo;
    private Parent parent;
    private AppointmentDAOImp appointmentDAOImp;
    private ContactDAOImp contactDAOImp;
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
        contactDAOImp =new ContactDAOImp();
        for (Contact contact:contactDAOImp.getAllContact()){
            ContactCb.getItems().add(contact);
        }
        ContactCb.getSelectionModel().selectFirst();

    }

    /**
     * list all appointments sorted by given contact
     * @param event when button is clicked
     */

    public void OnGetReportBtn(ActionEvent event) {
        appointmentDAOImp=new AppointmentDAOImp();
        ObservableList<Appointment> list= FXCollections.observableArrayList();
        int contactId=((Contact)ContactCb.getSelectionModel().getSelectedItem()).getContactId();
        for (Appointment appointment:appointmentDAOImp.getAllAppointment()){
            if(appointment.getContactId()==contactId){
                list.add(appointment);
            }
        }
        AppTable.setItems(list);
        AppIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        AppTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        AppDescripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        AppTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        AppStartDateTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        AppEndDateTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        AppCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
    /**
     * go back to main form when back button is clicked
     * use method defined by lambda expression to go to main form
     * @param event when back button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void OnBackBtn(ActionEvent event) throws IOException {
        goTo.load(parent,event,"/view/MainForm.fxml","Main Form");
    }
}
