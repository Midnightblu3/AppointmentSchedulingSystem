package model;

import java.time.LocalDateTime;

/**
 * country class to hold country information
 * @author Rui Huang
 */

public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    /**
     * constructor for country class
     * @param countryId country id
     * @param country country name
     * @param createDate create date and time
     * @param createBy created by name
     * @param lastUpdate last updated date and time
     * @param lastUpdateBy last updated by
     */
    public Country(int countryId, String country, LocalDateTime createDate,
                   String createBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * get country id
     * @return country id
     */

    public int getCountryId() {
        return countryId;
    }

    /**
     * Set country id
     * @param countryId country id
     */


    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * get country name
     * @return country name
     */

    public String getCountry() {
        return country;
    }

    /**
     * get country name
     * @param country country name
     */

    public void setCountry(String country) {
        this.country = country;
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
     * convert id and name to string
     * @return id and name in string
     */

    @Override
    public String toString() {
        return  "["+countryId+"] "+country;
    }
}
