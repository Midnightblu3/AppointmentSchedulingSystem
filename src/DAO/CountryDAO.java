package DAO;

import javafx.collections.ObservableList;
import model.Country;

/**
 * interface for country data access object
 * @author Rui Huang
 */


public interface CountryDAO {
    /**
     * get all the country object
     * @return  observable list for all country object
     */
    ObservableList<Country> getAllCountry();
}
