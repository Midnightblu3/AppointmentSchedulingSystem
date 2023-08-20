package DAO;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

/**
 * interface for first level division data access object
 */

public interface FirstLevelDivisionDAO {

    /**
     * return all first level division with country Id
     * @param countryId country id to get all the first level division object
     * @return all the first level division with country Id
     */
    ObservableList<FirstLevelDivision> getFilteredFirstLevelDivision(int countryId);
}
