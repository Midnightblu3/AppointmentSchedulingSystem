package model;

import java.time.LocalDateTime;

/**
 * Appointment class that holds data for appointment
 * @author Rui Huang
 */

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor that create a new appointment method
     * @param appointmentId appointment id
     * @param title title for appointment
     * @param description description
     * @param location location
     * @param type type
     * @param startDate start date and time
     * @param endDate end date and time
     * @param createDate create date and time
     * @param createBy user created the appointment
     * @param lastUpdate most recent update date and time
     * @param lastUpdateBy last user updated this object
     * @param customerId customer id
     * @param userId user id
     * @param contactId contact id
     */

    public Appointment(int appointmentId, String title, String description,
                       String location, String type, LocalDateTime startDate,
                       LocalDateTime endDate, LocalDateTime createDate, String createBy,
                       LocalDateTime lastUpdate, String lastUpdateBy,
                       int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * get appointment id
     * @return appointment id
     */

    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * set appointment id
     * @param appointmentId id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * get title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * set title
     * @param title title as string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * get description
     * @return description
     */

    public String getDescription() {
        return description;
    }

    /**
     * set description
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get location
     * @return laction
     */
    public String getLocation() {
        return location;
    }

    /**
     * set location
     * @param location location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get type
     * @return type
     */

    public String getType() {
        return type;
    }

    /**
     * set type
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get start date and time
     * @return start date and time
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * set start date and time
     * @param startDate set date and time
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * get end date and time
     * @return end date and time
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * set End date and time
     * @param endDate end date and time
     */

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * get create date and time
     * @return create date time
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * set create date and time
     * @param createDate create date and time
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
     * @param createBy created by
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * get last update date and time
     * @return last update date and time
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * set last update date and time
     * @param lastUpdate last update date and time
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * get last updated by
     * @return last updated by
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * set last updated by
     * @param lastUpdateBy last updated up
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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
     * get user id
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * set user id
     * @param userId user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *  get contact id
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
}
