package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Customer class that store data for customer
 * @author Rui Huang
 */

public class Customer {
   private int customerId;
   private String customerName;
   private String address;
   private String postalCode;
   private String phone;
   private LocalDateTime createDate;
   private String createBy;
   private LocalDateTime lastUpdate;
   private String lastUpdateBy;
   private int divisionId;
   private ArrayList<Appointment> appointments;

    /**
     * constructor for customer class
     * @param customerId customer id
     * @param customerName customer name
     * @param address address
     * @param postalCode postal code
     * @param phone phone number
     * @param createDate create date and time
     * @param createBy created by
     * @param lastUpdate last updated date and time
     * @param lastUpdateBy last updated by
     * @param divisionId division id
     */

    public Customer(int customerId, String customerName, String address, String postalCode,
                    String phone, LocalDateTime createDate,
                    String createBy, LocalDateTime lastUpdate,
                    String lastUpdateBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.divisionId = divisionId;
        this.appointments= new ArrayList<Appointment>();
    }

    /**
     * get customer id
     * @return customer id
     */

    public int getCustomerId() {
        return customerId;
    }

    /**
     * set customer id
     * @param customerId customer id
     */

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * get customer name
     * @return customer name
     */

    public String getCustomerName() {
        return customerName;
    }

    /**
     * set customer name
     * @param customerName customer name
     */

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * get address
     * @return address
     */

    public String getAddress() {
        return address;
    }

    /**
     *  set address
     * @param address address
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get postal Code
     * @return postal code
     */

    public String getPostalCode() {
        return postalCode;
    }

    /**
     * set postal Code
     * @param postalCode postal code
     */

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * get phone number
     * @return phone number
     */

    public String getPhone() {
        return phone;
    }

    /**
     * set phone number
     * @param phone phone number
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * get created date and time
     * @return created date and time
     */


    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     * set created date and time
     * @param createDate created date and time
     */


    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * get created by
     * @return created by
     */
    public String getCreateBy() {
        return createBy;
    }
    /**
     * set created by
     * @param createBy  created by
     */

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    /**
     * set last update date and time
     * @return last update date and time
     */

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     * set last update date and time
     * @param lastUpdate update date and time
     */

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * get last updated by
     * @return lastUpdatedBy last updated by
     */

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    /**
     * set last updated by
     * @param lastUpdateBy updated by
     */

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * get divisiona id for customer
     * @return divisionId first level division id
     */

    public int getDivisionId() {
        return divisionId;
    }

    /**
     * set divisiona id for customer
     * @param divisionId first level division id
     */

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * set a list of appointment for the customer
     * @param appointments list of appointment
     */
    public void setAppointments(ArrayList<Appointment> appointments){
        this.appointments=appointments;
    }
    /**
     * get a list of appointment for the customer
     * @return appointments list of appointments
     */
    public ArrayList<Appointment> getAppointments(){
        return this.appointments;
    }

    /**
     * add appointment to the appointments list
     * @param appointment appointment
     */
    public void addAppointment(Appointment appointment){
        this.appointments.add(appointment);
    }

    /**
     * convert id and name to string
     * @return id and name in string
     */

    @Override
    public String toString() {
        return "["+customerId+"] "+ customerName ;
    }
}
