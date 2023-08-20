package controller;

import DAO.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Controller class for addAppointmentForm
 * define a Lambda expression 'fromESTtoLocalTime' to convert time from EST to local time
 * @author Rui Huang
 */

public class AddAppointmentForm implements Initializable {

    convertTime fromESTtoLocalTime = t->t.atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    public TextField IdTxf;
    public TextField TitleTxf;
    public TextField LocationTxf;
    public ComboBox<Integer> EndTimeHourCb;
    public ComboBox<Integer> EndTimeMinCb;
    public DatePicker StartTimeDp;
    public ComboBox<Integer> StartTimeHourCb;
    public ComboBox<Integer> StartTimeMinCb;
    public TextField TypeTxf;
    public ComboBox<Customer> CustomerCb;
    public ComboBox<User> UserCb;
    public ComboBox<Contact> ContactCb;
    public TextArea DescriptionTxa;
    public Button SaveBtn;
    public Button ExitBtn;
    public Label ErrorTextLb;
    private LoadForm goTo;
    private Parent parent;
    private LocalDate appDate;
    private int bsh;
    private int beh;
    private AppointmentDAOImp appointmentDAOImp;

    /**
     * Initialize method that will be called when the add appointment page is initialized
     * define the lambda expression 'goTo' to load other form
     * use the lambda expression 'fromESTtoLocalTime' to convert business hour from EST time local time
     * @param url the url of the fxml file
     * @param resourceBundle resource bundle for the fxml file
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
        bsh=fromESTtoLocalTime.convert(LocalTime.of(8,0).atDate(LocalDate.now())).toLocalTime().getHour();
        beh=fromESTtoLocalTime.convert(LocalTime.of(22,0).atDate(LocalDate.now())).toLocalTime().getHour();
        for(int i=bsh;i<beh;i++){
            StartTimeHourCb.getItems().add(i);
            EndTimeHourCb.getItems().add(i);
        }
        StartTimeHourCb.setValue(bsh);
        EndTimeHourCb.setValue(bsh);
        for(int i=0;i<60;i++){
            StartTimeMinCb.getItems().add(i);
            EndTimeMinCb.getItems().add(i);
        }
        StartTimeMinCb.setValue(0);
        EndTimeMinCb.setValue(0);
        appointmentDAOImp=new AppointmentDAOImp();
        CustomerDAOImp customerDAOImp=new CustomerDAOImp();
        ContactDAOImp contactDAOImp=new ContactDAOImp();
        UserDAOImp userDAOImp= new UserDAOImp();
        CustomerCb.setItems(customerDAOImp.getAllCustomer());
        ContactCb.setItems(contactDAOImp.getAllContact());
        UserCb.setItems(userDAOImp.getAllUser());
        CustomerCb.getSelectionModel().selectFirst();
        UserCb.getSelectionModel().selectFirst();
        ContactCb.getSelectionModel().selectFirst();



    }

    /**
     * Create and save the appointment when save button is clicked
     * use method defined by lambda expression 'goTo' to go to main form
     * @param actionEvent when the button was clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnSaveBtn(ActionEvent actionEvent) throws IOException {
        String title="";
        String description="";
        String location="";
        String type="";
        LocalDateTime starTime=null;
        LocalDateTime endTime=null;
        Customer customer=CustomerCb.getSelectionModel().getSelectedItem();
        Contact contact=ContactCb.getSelectionModel().getSelectedItem();
        User user=UserCb.getSelectionModel().getSelectedItem();
        boolean error=false;
        String errorTxt="";
        if(TitleTxf.getText().isEmpty()){
            error=true;
            errorTxt=errorTxt+"No data in the Title field.\n";
        }else {
            title=TitleTxf.getText();
        }
        if(DescriptionTxa.getText().isEmpty()){
            error=true;
            errorTxt=errorTxt+"No date in the Description field.\n";
        }else {
            description=DescriptionTxa.getText();
        }
        if(LocationTxf.getText().isEmpty()){
            error=true;
            errorTxt=errorTxt+"No data in the Location field.\n";
        }else {
            location=LocationTxf.getText();
        }
        if (TypeTxf.getText().isEmpty()){
            error=true;
            errorTxt=errorTxt+"No data in the Type field.\n";
        }else{
            type=TypeTxf.getText();
        }
        try{
            appDate=StartTimeDp.getValue();
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"No appointment date chosen.\n";
        }
        try{
            starTime= LocalTime.of(StartTimeHourCb.getValue(),
            StartTimeMinCb.getValue()).atDate(appDate);
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"Invalid Start time selected.\n";
        }
        try{
            endTime= LocalTime.of(EndTimeHourCb.getValue(),
                    EndTimeMinCb.getValue()).atDate(appDate);
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"Invalid End time selected.\n";
        }
        try {
            if (starTime.isAfter(endTime) || starTime.isEqual(endTime)) {
                error = true;
                errorTxt = errorTxt + "End time has to be after Start time!\n";
            }
        }catch (NullPointerException e){
            error=true;
        }
        try {
            if (starTime.isBefore(LocalDateTime.now())) {
                error = true;
                errorTxt = errorTxt + "Can't schedule an appointment in the past!\n";
            }
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"No appointment date/time chosen.\n";
        }


        try {
            if(timeConflict(starTime,endTime, appointmentDAOImp.getFilteredAppointment(customer.getCustomerId()))){
                error=true;
                errorTxt=errorTxt+"Time conflicted with existing Appointments.\n";
            }
        }catch (NullPointerException e){
            error=true;
            if(customer==null) {
                errorTxt = errorTxt + "No customer selected\n";
            }
        }

        try{
            customer=CustomerCb.getSelectionModel().getSelectedItem();
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"No Customer selected.\n";
        }
        try{
            contact=ContactCb.getSelectionModel().getSelectedItem();
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"No Contact selected.\n";
        }
        try{
            user=UserCb.getSelectionModel().getSelectedItem();
        }catch (NullPointerException e){
            error=true;
            errorTxt=errorTxt+"No User selected.\n";
        }



        if(!error){
            Appointment appointment=new Appointment(0,title,description,location,type,starTime,endTime,LocalDateTime.now(),
                    UserDAOImp.getCurrentUser().getUserName(),LocalDateTime.now(),UserDAOImp.getCurrentUser().getUserName(),
                    customer.getCustomerId(),user.getUserId(),contact.getContactId());
            appointmentDAOImp.addAppointment(appointment);
            //System.out.println(starTime);
            //System.out.println(endTime);
            goTo.load(parent,actionEvent,"/view/MainForm.fxml","Main Form");
        }else {
            Alert alert= new Alert(Alert.AlertType.ERROR,errorTxt);
            alert.showAndWait();
        }
    }

    /**
     * get the date from the date picker
     * @param event when the date picker is clicked
     */

    public void OnStartDateDp(ActionEvent event) {
        try {
            appDate = StartTimeDp.getValue();
        }catch (Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR,"Invalid date");
            alert.showAndWait();
        }
    }

    /**
     * set end time hour to be after the start time hour when start time combo box is changed
     * @param event when combo box is clicked
     */

    public void OnStartTimeHr(ActionEvent event) {
        EndTimeHourCb.getItems().clear();
        try {
            for (int i = StartTimeHourCb.getSelectionModel().getSelectedItem(); i < beh; i++) {
                EndTimeHourCb.getItems().add(i);
            }
            EndTimeHourCb.setValue(StartTimeHourCb.getSelectionModel().getSelectedItem());
        }catch (NullPointerException e){
            for(int i=bsh;i<beh;i++){
                StartTimeHourCb.getItems().add(i);
                EndTimeHourCb.getItems().add(i);
            }
            for(int i=0;i<60;i++){
                StartTimeMinCb.getItems().add(i);
                EndTimeMinCb.getItems().add(i);
            }
        }
    }

    /**
     * set end time minute to be after the start time minute
     * @param event when start time minute combo box is clicked
     */

    public void OnStartTimeMin(ActionEvent event) {
        EndTimeMinCb.getItems().clear();
        try {
            if (StartTimeMinCb.getSelectionModel().getSelectedItem() == 59) {
                for (int i = 0; i < 60; i++) {
                    EndTimeMinCb.getItems().add(i);
                }
                if(EndTimeHourCb.getSelectionModel().getSelectedItem()<=StartTimeHourCb.getSelectionModel().getSelectedItem()) {
                    EndTimeHourCb.getItems().clear();
                    for (int i = StartTimeHourCb.getSelectionModel().getSelectedItem() + 1; i < beh; i++) {
                        EndTimeHourCb.getItems().add(i);
                    }
                    EndTimeHourCb.setValue(StartTimeHourCb.getSelectionModel().getSelectedItem() + 1);
                }
                EndTimeMinCb.setValue(0);
            } else {
                if (StartTimeHourCb.getSelectionModel().getSelectedItem() == EndTimeHourCb.getSelectionModel().getSelectedItem()) {
                    for (int i = StartTimeMinCb.getSelectionModel().getSelectedItem() + 1; i < 60; i++) {
                        EndTimeMinCb.getItems().add(i);
                        EndTimeMinCb.setValue(StartTimeMinCb.getSelectionModel().getSelectedItem() + 1);
                    }
                }else {
                    for (int i = 0; i < 60; i++) {
                        EndTimeMinCb.getItems().add(i);
                    }
                    EndTimeMinCb.setValue(0);
                }
            }
        }catch (NullPointerException e){
            for(int i=bsh;i<beh;i++){
                StartTimeHourCb.getItems().add(i);
                EndTimeHourCb.getItems().add(i);
            }
            for(int i=0;i<60;i++){
                StartTimeMinCb.getItems().add(i);
                EndTimeMinCb.getItems().add(i);
            }
        }

    }

    /**
     * reset the end time minute after the end time hour is adjusted
     * @param event when end time hour combo box is clicked
     */

    public void OnEndTimeHrCb(ActionEvent event) {
        try {
            if (EndTimeHourCb.getSelectionModel().getSelectedItem() > StartTimeHourCb.getSelectionModel().getSelectedItem()) {
                EndTimeMinCb.getItems().clear();
                for (int i = 0; i < 60; i++) {
                    EndTimeMinCb.getItems().add(i);
                }
                EndTimeMinCb.setValue(0);
            }
        }catch (NullPointerException e){
            EndTimeMinCb.getItems().clear();
            for (int i = 0; i < 60; i++) {
                EndTimeMinCb.getItems().add(i);
            }
            EndTimeMinCb.setValue(0);
        }
    }

    /**
     * go back to main form when exit button is clicked
     * use method defined by lambda expression 'goTo' to go to main form
     * @param actionEvent when exit button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void OnExistBtn(ActionEvent actionEvent) throws IOException {
        goTo.load(parent,actionEvent,"/view/MainForm.fxml","Main Form");
    }

    /**
     * tell if there is time conflict with existing appointments
     * @param st start time
     * @param et end time
     * @param apps list of exiting appointments
     * @return whether there is conflicts
     */

    public boolean timeConflict(LocalDateTime st,LocalDateTime et, ObservableList<Appointment> apps){
        boolean conflict=false;
        for(Appointment x: apps){
            if(et.isBefore(x.getStartDate())||et.isEqual(x.getStartDate())){
                conflict=false;
            }else if(st.isAfter(x.getEndDate())||st.isEqual(x.getEndDate())){
                conflict=false;
            }else if((st.isAfter(x.getStartDate())||st.isEqual(x.getStartDate()))&&
                    (et.isBefore(x.getEndDate())||et.isEqual(x.getEndDate()))) {
                return true;
            }else {
                return true;
            }
        }
        return conflict;
    }



}
