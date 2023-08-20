package controller;

import DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller class for modify Customer form
 * @author Rui Huang
 */


public class ModifyCustomerForm implements Initializable {
    public TextField CustNameTxf;
    public ComboBox<FirstLevelDivision> StateProvinceCb;
    public ComboBox<Country> CountryCb;
    public TextField PostalCodeTxf;
    public TextField PhoneTxf;
    public Button SaveBtn;
    public Button CancelBtn;
    public Label ErrorTxtLb;
    public TextField IdTxf;
    public TextField AddressTxf;
    private LoadForm goTo;
    private Parent parent;
    private FirstLevelDivisionDAOImp firstLevelDivisionDAOImp;
    private CountryDAOImp countryDAOImp;
    private CustomerDAOImp customerDAOImp;
    private Customer customer;


    /**
     * Initialize the form when the form is loaded
     * Define the lambda expression 'goTo' to load another form
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

    }
    /**
     * Create and update a customer to database when save button is clicked
     * use method defined by lambda expression to go to main form
     * @param actionEvent when the button was clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnSaveBtn(ActionEvent actionEvent) throws IOException {
        boolean error=false;
        String name="";
        String streetAddress="";
        String state="";
        String country="";
        String postalcode="";
        String phone="";
        int divisionId=0;
        String ErrorTxt="";
        if(CustNameTxf.getText().isEmpty()){
            error=true;
            ErrorTxt=ErrorTxt+"No data in Name field\n";
        }else {
            name=CustNameTxf.getText();
        }
        if(AddressTxf.getText().isEmpty()){
            error=true;
            ErrorTxt=ErrorTxt+"No data in Street Address field\n";
        }else {
            streetAddress=AddressTxf.getText();
        }
        if(StateProvinceCb.getSelectionModel().isEmpty()){
            error=true;
            ErrorTxt=ErrorTxt+"No State/Province Selected\n";
        }else {
            state=StateProvinceCb.getSelectionModel().getSelectedItem().toString();
        }
        if(CountryCb.getSelectionModel().isEmpty()){
            error=true;
            ErrorTxt=ErrorTxt+"No State/Province Selected\n";
        }else {
            divisionId=((FirstLevelDivision)StateProvinceCb.getSelectionModel().getSelectedItem()).getDivisionId();
            country=CountryCb.getSelectionModel().getSelectedItem().toString();
        }
        if(PostalCodeTxf.getText().isEmpty()||(!PostalCodeTxf.getText().matches("[0-9]+"))){
            error=true;
            ErrorTxt=ErrorTxt+"No Data or Invalid Postal Code\n";
        }else {
            postalcode=PostalCodeTxf.getText();
        }
        if(PhoneTxf.getText().isEmpty()||(!PhoneTxf.getText().matches("[0-9 -]+"))){
            error=true;
            ErrorTxt=ErrorTxt+"No Data or Invalid Phone number\n";
        }else {
            phone=PhoneTxf.getText();
        }
        if(error){
            Alert alert= new Alert(Alert.AlertType.ERROR,ErrorTxt);
            alert.showAndWait();
        }else {
            customer.setCustomerName(name);
            customer.setAddress(streetAddress);
            customer.setPostalCode(postalcode);
            customer.setPhone(phone);
            customer.setLastUpdate(LocalDateTime.now());
            customer.setLastUpdateBy(UserDAOImp.getCurrentUser().getUserName());
            customer.setDivisionId(divisionId);
            customerDAOImp.updateCustomer(customer);
            try {
                goTo.load(parent,actionEvent, "/view/MainForm.fxml", "Main Form");
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    /**
     * go back to main form when cancel button is clicked
     * use method defined by lambda expression to go to main form
     * @param event when exit button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnCancelBtn(ActionEvent event) throws IOException {
        goTo.load(parent,event,"/view/MainForm.fxml","Main Form");
    }

    /**
     * load the list first level divisions according to the country user picked
     * @param event when the country combo box is clicked
     */

    public void onCountryCb(ActionEvent event) {
        Country country=((Country)CountryCb.getSelectionModel().getSelectedItem());
        StateProvinceCb.setItems(firstLevelDivisionDAOImp.getFilteredFirstLevelDivision(country.getCountryId()));
        StateProvinceCb.getSelectionModel().select(firstLevelDivisionDAOImp.getFilteredFirstLevelDivision(country.getCountryId()).get(0));
    }

    /**
     * load the customer selected from the main form customer table to modify customer form
     * define the lambda expression 'goTo' to load another from
     * @param customer the customer selected from the customer table in main form
     */
    public void loadData(Customer customer){
        goTo=(p,e,r,t)->{
            Stage s=(Stage)((Button)(e.getSource())).getScene().getWindow();
            p= FXMLLoader.load(getClass().getResource(r));
            s.setTitle(t);
            s.setScene(new Scene(p));
            s.show();
        };
        customerDAOImp= new CustomerDAOImp();
        firstLevelDivisionDAOImp=new FirstLevelDivisionDAOImp();
        countryDAOImp=new CountryDAOImp();
        CountryCb.setItems(countryDAOImp.getAllCountry());
        StateProvinceCb.setItems(firstLevelDivisionDAOImp.getFilteredFirstLevelDivision(firstLevelDivisionDAOImp.lookUpDivision(customer.getDivisionId()).getCountryId()));
        this.customer=customer;
        IdTxf.setText(String.valueOf(customer.getCustomerId()));
        CustNameTxf.setText(customer.getCustomerName());
        AddressTxf.setText(customer.getAddress());
        StateProvinceCb.getSelectionModel().select(firstLevelDivisionDAOImp.lookUpDivision(customer.getDivisionId()));
        CountryCb.getSelectionModel().select(countryDAOImp.lookUpCountry(firstLevelDivisionDAOImp.lookUpDivision(customer.getDivisionId()).getCountryId()));
        PostalCodeTxf.setText(customer.getPostalCode());
        PhoneTxf.setText(customer.getPhone());

    }

}
