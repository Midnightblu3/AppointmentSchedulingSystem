package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.*;
import java.time.ZoneId;

/**
 * CustomerDAOImp implement CustomerDAO interface
 * CustomerDAOImp is the Data access object class that hold the data retrieved from database and display it with a observableList
 * define the lambda expression 'toUTC' to convert local time to UTC time
 *  define the lambda expression 'toLocalTime' to convert UTC time to local time
 * @author Rui Huang
 */

public class CustomerDAOImp implements CustomerDAO{
    AppointmentDAOImp appointmentDAOImp;

    convertTime toUTC=t->t.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

    convertTime toLocalTime = t->t.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

    private ObservableList<Customer> allCustomer;
    private static final String INSERT ="INSERT INTO client_schedule.customers (Customer_Name,Address,Postal_Code," +
            "Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID)" +
            " VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String DELETE="DELETE FROM client_schedule.customers WHERE Customer_ID=?";
    private static final String GET_ALL="SELECT * FROM client_schedule.customers";
    private static final String UPDATE="UPDATE client_schedule.customers SET Customer_Name=?, Address=?, Postal_Code=?," +
            " Phone=?, Last_Update=?, Last_Updated_By=?, Division_ID=?" +
            " WHERE Customer_ID=?";
    private static final String GET_LAST_ID="SELECT Customer_ID FROM client_schedule.customers WHERE Customer_ID=(SELECT max(Customer_ID) FROM customers)";

    /**
     * constructor for the class read all the rows of data from customers table from database and save it in the observableList
     * use the defined lambda expression 'toLocalTime' to convert UTC time from database to local time
     */

    public CustomerDAOImp() {

        try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_ALL)){
            allCustomer= FXCollections.observableArrayList();
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                allCustomer.add(new Customer(resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        toLocalTime.convert(resultSet.getTimestamp("Create_Date").toLocalDateTime()),
                        resultSet.getString("Created_By"),
                        toLocalTime.convert(resultSet.getTimestamp("Last_Update").toLocalDateTime()),
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Division_ID")));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        updateAppointment();
    }

    /**
     * add customer to the customer table in the database
     * use the defined lambda 'toUTC' expression to convert local time to UTC
     * @param customer the customer object hold the data for the new row to be added to the customers table
     */

    @Override
    public void addCustomer(Customer customer) {
        try(PreparedStatement statement= JDBC.connection.prepareStatement(INSERT)){
            statement.setString(1,customer.getCustomerName());
            statement.setString(2,customer.getAddress());
            statement.setString(3,customer.getPostalCode());
            statement.setString(4,customer.getPhone());
            statement.setTimestamp(5, Timestamp.valueOf(toUTC.convert(customer.getCreateDate())));
            statement.setString(6,customer.getCreateBy());
            statement.setTimestamp(7,Timestamp.valueOf(toUTC.convert(customer.getLastUpdate())));
            statement.setString(8,customer.getLastUpdateBy());
            statement.setInt(9,customer.getDivisionId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_LAST_ID)){
            int id=0;
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("Customer_ID");
            }
            customer.setCustomerId(id);
            allCustomer.add(customer);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * delete the customer from the customer table
     * @param customer the customer object that hold the data of the customer row that will be deleted from the customer table
     * @return true when customer is deleted false when there is no customer in the table with the same customer_id
     */
    @Override
    public boolean deleteCustomer(Customer customer) {
        if(this.allCustomer.contains(customer)) {
            try (PreparedStatement statement = JDBC.connection.prepareStatement(DELETE)) {
                statement.setInt(1,customer.getCustomerId());
                statement.execute();
                allCustomer.remove(customer);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        }else {
            return false;
        }

    }
    /**
     * get all the customer object in the list
     * @return observable list tha hold all the customer object
     */

    @Override
    public ObservableList<Customer> getAllCustomer() {
        return this.allCustomer;
    }


    /**
     * look up customer by id
     * @param customerId id used to look up customer
     * @return customer that match the same id
     */

    @Override
    public Customer lookupCustomer(int customerId) {
        for(Customer customer:allCustomer){
            if(customer.getCustomerId()==customerId){
                return customer;
            }
        }
        return null;
    }

    /**
     * update the customer to the database
     * use defined lambda expression 'toUTC' to convert local time to UTC
     * @param customer customer will be updated to customer
     */

    @Override
    public void updateCustomer(Customer customer) {
        try(PreparedStatement statement= JDBC.connection.prepareStatement(UPDATE)){
            statement.setString(1,customer.getCustomerName());
            statement.setString(2,customer.getAddress());
            statement.setString(3,customer.getPostalCode());
            statement.setString(4,customer.getPhone());
            statement.setTimestamp(5, Timestamp.valueOf(toUTC.convert(customer.getLastUpdate())));
            statement.setString(6,customer.getLastUpdateBy());
            statement.setInt(7,customer.getDivisionId());
            statement.setInt(8,customer.getCustomerId());
            statement.execute();
            customer.setCreateDate(this.lookupCustomer(customer.getCustomerId()).getCreateDate());
            customer.setCreateBy(this.lookupCustomer(customer.getCustomerId()).getCreateBy());
            allCustomer.set(allCustomer.indexOf(this.lookupCustomer(customer.getCustomerId())),customer);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * load appointments and save to customer object
     */

    @Override
    public void updateAppointment() {
        appointmentDAOImp=new AppointmentDAOImp();
        for(Appointment appointment:appointmentDAOImp.getAllAppointment()){
            this.lookupCustomer(appointment.getCustomerId()).addAppointment(appointment);
        }
    }
}
