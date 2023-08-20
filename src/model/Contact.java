package model;

/**
 * class for contact
 * @author Rui Huang
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /**
     * constructor for contact
     * @param contactId id
     * @param contactName name
     * @param email email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * get contact id
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * set contact id
     * @param contactId contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * get contact name
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * set contact name
     * @param contactName contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * get email
     * @return email
     */

    public String getEmail() {
        return email;
    }

    /**
     * set email
     * @param email email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * to string
     * @return contact id and name in string
     */

    @Override
    public String toString() {
        return  "["+contactId + "] " + contactName;
    }
}
