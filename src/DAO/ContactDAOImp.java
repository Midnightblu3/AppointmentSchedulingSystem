package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for contactDAO holds all the Contact data access objects
 * @author Rui Huang
 */

public class ContactDAOImp implements ContactDAO{
    ObservableList<Contact> allContacts= FXCollections.observableArrayList();
    private static final String GET_ALL="SELECT * FROM client_schedule.contacts";

    /**
     * constructor for contactDAOImp
     */

    public ContactDAOImp() {
        try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_ALL)){
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                allContacts.add(new Contact(resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * get all contact in the list
     * @return observable list of all contact
     */

    @Override
    public ObservableList<Contact> getAllContact() {
        return allContacts;
    }


    /**
     * look up contact by id
     * @param contactId id to look up the contact in the list
     * @return contact with matching id
     */

    @Override
    public Contact lookUpContact(int contactId) {
        for(Contact contact:allContacts){
            if (contact.getContactId()==contactId){
                return contact;
            }
        }
        return null;
    }
}
