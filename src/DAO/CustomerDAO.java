package DAO;


import javafx.collections.ObservableList;
import model.Customer;

/**
 * inter face for customer data access object
 */

public interface CustomerDAO {
    /**
     * add customer to the database
     * @param customer customer will be added to the data base
     */
    void addCustomer(Customer customer);

    /**
     * delete customer from the database
     * @param customer customer that will be deleted from the database
     * @return boolean that tell if customer is successfully deleted
     */
    boolean deleteCustomer(Customer customer);

    /**
     * get all the customer object in the list
     * @return observable list tha hold all the customer object
     */
    ObservableList<Customer> getAllCustomer();

    /**
     * look up customer by id
     * @param customerId id used to look up customer
     * @return customer that match the same id
     */
    Customer lookupCustomer(int customerId);

    /**
     * update the customer to the database
     * @param customer customer will be updated to customer
     */
    void updateCustomer(Customer customer);

    /**
     * load appointments and save to customer object
     */
    void updateAppointment();
}
