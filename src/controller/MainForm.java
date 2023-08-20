package controller;

import DAO.AppointmentDAOImp;
import DAO.CustomerDAOImp;
import DAO.JDBC;
import DAO.LoadForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * the controller class for main form
 * @author Rui Huang
 */

public class MainForm implements Initializable {
    public TableView<Customer> CustomerTable;
    public TableColumn<Customer,Integer> CustIdCol;
    public TableColumn<Customer,String> CustNameCol;
    public TableColumn<Customer,String> CustAddressCol;
    public TableColumn<Customer,String> CustPostalCol;
    public TableColumn<Customer,String> CustPhoneCol;
    public Button CustAddBtnMain;
    public Button CustModBtnMain;
    public Button CustDelBtnMain;
    public Button Report1Btn;
    public Button Report2Btn;
    public Button Report3Btn;
    public Label ErrorTxtLb;
    public RadioButton AllAppViewRb;
    public RadioButton MonthlyViewRb;
    public RadioButton WeeklyViewRb;
    public TableView<Appointment> AppTable;
    public TableColumn<Appointment,Integer> AppIdCol;
    public TableColumn<Appointment,String> AppTitleCol;
    public TableColumn<Appointment,String> AppDescripCol;
    public TableColumn<Appointment,String> AppLocationCol;
    public TableColumn<Appointment, LocalDateTime> AppStartDateTime;
    public TableColumn<Appointment,LocalDateTime> AppEndDateTime;
    public TableColumn<Appointment,String> AppTypeCol;
    public TableColumn<Appointment,Integer> AppCustIdCol;
    public TableColumn<Appointment,Integer> AppUserIdCol;
    public Button AppAddBtnMain;
    public Button AppModBtnMain;
    public Button AppDelBtnMain;
    public Button ExitBtnMain;
    private Parent parent;
    private CustomerDAOImp customerDAOImp;
    private AppointmentDAOImp appointmentDAOImp;
    private LoadForm goTo;

    /**
     * Initialize the form when the form is loaded
     * define a lambda expression 'goTo' to load other form
     * @param url the url of fxml file
     * @param resourceBundle resource bundle for fxml file
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerDAOImp=new CustomerDAOImp();
        appointmentDAOImp=new AppointmentDAOImp();
       CustomerTable.setItems(customerDAOImp.getAllCustomer());
       CustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       CustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
       CustAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
       CustPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
       CustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
       AppTable.setItems(appointmentDAOImp.getAllAppointment());
       AppIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
       AppTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
       AppDescripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
       AppLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
       AppTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
       AppStartDateTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
       AppEndDateTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
       AppCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       AppUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
       AllAppViewRb.setSelected(true);
        goTo=(p,e,r,t)->{
            Stage s=(Stage)((Button)(e.getSource())).getScene().getWindow();
            p=FXMLLoader.load(getClass().getResource(r));
            s.setTitle(t);
            s.setScene(new Scene(p));
            s.show();
        };
    }

    /**
     * go to add customer page
     * use method defined by lambda expression 'goTo' to go to add customer form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnAddCustBtn(ActionEvent actionEvent) throws IOException {
        goTo.load(parent,actionEvent,"/view/AddCustomerForm.fxml","Add Customer Form");
    }

    /**
     * go to modify customer page
     * redefine the lambda expression to load customer data to modify customer form
     * use method defined by lambda expression 'goTo' to go to modify customer form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnModifyCustBtn(ActionEvent actionEvent) throws IOException {
        goTo=(p,e,r,t)->{
            Stage s=(Stage)((Button)(e.getSource())).getScene().getWindow();
            s.setTitle(t);
            s.setScene(new Scene(p));
            s.show();
        };
       FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyCustomerForm.fxml"));
        loader.load();
        ModifyCustomerForm modifyCustomerForm=loader.getController();
        try {
            modifyCustomerForm.loadData(CustomerTable.getSelectionModel().getSelectedItem());

            goTo.load(loader.getRoot(),actionEvent,"/view/ModifyCustomerForm.fxml","Modify Customer");

        }catch (NullPointerException e){
            ErrorTxtLb.setText("No customer selected");
        }
    }

    /**
     * delete selected customer from the database
     * @param actionEvent when button is clicked
     */

    public void OnDelCustBtn(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Removing the customer will delete all the appointments with this customer,are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            Customer customer = CustomerTable.getSelectionModel().getSelectedItem();
            if (customer != null) {
                ArrayList<Appointment> appointments=customer.getAppointments();
                for(Appointment appointment:appointments){
                    appointmentDAOImp.deleteAppointment(appointment);
                }
                customerDAOImp.deleteCustomer(customer);
                appointmentDAOImp= new AppointmentDAOImp();
                AppTable.setItems(appointmentDAOImp.getAllAppointment());
                AppTable.refresh();

            }else {
                ErrorTxtLb.setText("No Customer Selected.");
            }
        }
    }
    /**
     * go to report 1 page
     * use method defined by lambda expression 'goTo' to go to report 1 form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnReport1Btn(ActionEvent actionEvent) throws IOException {
        goTo.load(parent,actionEvent,"/view/Report1.fxml","Report Form 1");

    }

    /**
     * go to report 2 page
     * use method defined by lambda expression 'goTo' to go to report 2 form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnReport2Btn(ActionEvent actionEvent) throws IOException {
        goTo.load(parent,actionEvent,"/view/Report2.fxml","Report Form 2");
    }

    /**
     * go to report 3 page
     * use method defined by lambda expression 'goTo' to go to report 3 form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnReport3Btn(ActionEvent actionEvent) throws IOException {
        goTo.load(parent,actionEvent,"/view/Report3.fxml","Report Form 3");
    }

    /**
     * go to add appointment page
     * use method defined by lambda expression 'goTo' to go to add appointment form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void OnAddAppBtn(ActionEvent actionEvent) throws IOException {
        goTo.load(parent,actionEvent,"/view/AddAppointmentForm.fxml","Add Appointment Form");
    }

    /**
     * go to modify appointment page
     * redefine 'goTo' lambda expression to load appointment data to modify appointment form
     * use method defined by lambda expression 'goTo' to go to modify appointment form
     * @param actionEvent when add customer button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnModifyAppBtn(ActionEvent actionEvent) throws IOException {

        goTo=(p,e,r,t)->{
            Stage s=(Stage)((Button)(e.getSource())).getScene().getWindow();
            s.setTitle(t);
            s.setScene(new Scene(p));
            s.show();
        };

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyAppointmentForm.fxml"));
        loader.load();
        ModifyAppointmentForm modifyAppointmentForm=loader.getController();
        try {
            modifyAppointmentForm.loadData(AppTable.getSelectionModel().getSelectedItem());
            goTo.load(loader.getRoot(),actionEvent,"/view/ModifyAppointmentForm.fxml","Modify Appointment Form");
        }catch (NullPointerException | IOException e){
            ErrorTxtLb.setText("No appointment selected");
        }
    }

    /**
     * delete selected appointment from the database
     * @param actionEvent when button is clicked
     */


    public void OnDelAppBtn(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete:\n"+
                "Appontment Id: "+ AppTable.getSelectionModel().getSelectedItem().getAppointmentId()+"\n"+
                "Appointment Type: "+AppTable.getSelectionModel().getSelectedItem().getType());
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            Appointment appointment =AppTable.getSelectionModel().getSelectedItem();
            if(appointment!=null){
                appointmentDAOImp.deleteAppointment(appointment);}
            else{
                ErrorTxtLb.setText("No Appointment Selected.");
            }
        }
    }

    /**
     * switch to all appointment view
     * @param actionEvent when radio button is selected
     */

    public void OnAllAppointmentRb(ActionEvent actionEvent) {
        AppTable.setItems(appointmentDAOImp.getAllAppointment());
    }
    /**
     * switch to appointment monthly view
     * @param actionEvent when radio button is selected
     */

    public void OnMonthRb(ActionEvent actionEvent) {
        AppTable.setItems(appointmentDAOImp.getMonthlyList());
    }
    /**
     * switch to appointment weekly view
     * @param actionEvent when radio button is selected
     */

    public void OnWeekRb(ActionEvent actionEvent) {
        AppTable.setItems(appointmentDAOImp.getWeeklyList());
    }

/**
 * go back to main form when cancel button is clicked
 * @param actionEvent when exit button is clicked
*/
    public void OnExit(ActionEvent actionEvent) {

        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            JDBC.closeConnection();
            System.exit(0);
        }
    }
}
