package controller;

import DAO.AppointmentDAOImp;
import DAO.ContactDAOImp;
import DAO.LoadForm;
import DAO.UserDAOImp;
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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
/**
 * controller for report 3
 * @author Rui Huang
 */

public class Report3 implements Initializable {
    public Button GetReportBtn;
    public Button BackBtn;
    public TableView<Appointment> AppTable;
    public TableColumn<Appointment,Integer> AppIdCol;
    public TableColumn<Appointment,String> AppTitleCol;
    public TableColumn<Appointment,String> AppDescripCol;
    public TableColumn<Appointment, LocalDateTime> AppStartDateTime;
    public TableColumn<Appointment,LocalDateTime> AppEndDateTime;
    public TableColumn<Appointment,String> AppTypeCol;
    public TableColumn<Appointment,Integer> AppCustIdCol;
    public ComboBox UserCb;
    private LoadForm goTo;
    private AppointmentDAOImp appointmentDAOImp;
    private Parent parent;
    private UserDAOImp userDAOImp;
    /**
     * Initialize the form when the form is loaded
     * define a lambda expression 'goTo' to load other form
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
        userDAOImp =new UserDAOImp();
        for(User user: userDAOImp.getAllUser()){
            UserCb.getItems().add(user);
        }
        UserCb.getSelectionModel().selectFirst();
    }
    /**
     * list all appointments sorted by given user
     * @param event when button is clicked
     */

    public void OnGetReportBtn(ActionEvent event) {
        appointmentDAOImp=new AppointmentDAOImp();
        ObservableList<Appointment> list= FXCollections.observableArrayList();
        int userId=((User)UserCb.getSelectionModel().getSelectedItem()).getUserId();
        for (Appointment appointment:appointmentDAOImp.getAllAppointment()){
            if(appointment.getContactId()==userId){
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
     *  use method defined by lambda expression to go to main form
     * @param event when back button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void OnBackBtn(ActionEvent event) throws IOException {
        goTo.load(parent,event,"/view/MainForm.fxml","Main Form");
    }
}
