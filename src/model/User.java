package model;

import java.time.LocalDateTime;

/**
 * class for user
 * @author Rui Huang
 */

public class User {
    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    /**
     * constructor for user class to store data
     * @param userId user id
     * @param userName user name
     * @param password password
     * @param createDate created date and time
     * @param createBy create by
     * @param lastUpdate last updated date and time
     * @param lastUpdateBy last updated by
     */

    public User(int userId, String userName, String password, LocalDateTime createDate, String createBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
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
     * get user name
     * @return user name
     */

    public String getUserName() {
        return userName;
    }
    /**
     * set user name
     * @param userName user name
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get user password
     * @return user password
     */

    public String getPassword() {
        return password;
    }
    /**
     * set user password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * get create date and time
     * @return last update date and time
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
     * @return last update by
     */

    public String getCreateBy() {
        return createBy;
    }
    /**
     * set created by
     * @param createBy last update by
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
     * get last update by
     * @return last update by
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * set last update by
     * @param lastUpdateBy last update by
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * convert to string
     * @return user id and name in string
     */
    @Override
    public String toString() {
        return "["+userId+"] " +userName;
    }
}
