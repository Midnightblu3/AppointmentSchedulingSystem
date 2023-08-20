package DAO;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * interface for contactDAO
 */

public interface ContactDAO {
    /**
     * get all contact in the list
     * @return observable list of all contact
     */
    ObservableList<Contact> getAllContact();

    /**
     * look up contact by id
     * @param contactId id to look up the contact in the list
     * @return contact with matching id
     */
    Contact lookUpContact(int contactId);
}
