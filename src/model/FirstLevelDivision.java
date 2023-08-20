package model;

import java.time.LocalDateTime;

/**
 * First level division class
 * @author Rui Huang
 */

public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private int countryId;

    /**
     *  constructor
     * @param divisionId division id
     * @param division division name
     * @param createDate create date and time
     * @param createBy created by
     * @param lastUpdate last update date and time
     * @param lastUpdateBy last update by
     * @param countryId country id
     */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createDate,
                              String createBy, LocalDateTime lastUpdate, String lastUpdateBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.countryId = countryId;
    }

    /**
     * get division id
     * @return division id
     */

    public int getDivisionId() {
        return divisionId;
    }

    /**
     * set division id
     * @param divisionId division id
     */

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * get division name
     * @return division name
     */

    public String getDivision() {
        return division;
    }

    /**
     * set division name
     * @param division division name
     */

    public void setDivision(String division) {
        this.division = division;
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
     * set last updated by
     * @param lastUpdateBy last updated by
     */
    public void setLastUpdateBy(String lastUpdateBy) {
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
     * set country id
     * @param countryId country id
     */

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /**
     * convert id and name to string
     * @return id and name in string
     */

    @Override
    public String toString() {
        return  "["+divisionId+"] "+division;
    }
}
