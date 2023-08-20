package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

/**
 * class for country data occess object
 * define the lambda expression 'toLocalTime' to convert UTC time to local time
 * @author Rui Huang
 */

public class CountryDAOImp implements CountryDAO{

    convertTime toLocalTime = t->t.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    ObservableList<Country> allCountries= FXCollections.observableArrayList();
    private static final String GET_ALL="SELECT * FROM client_schedule. countries";

    /**
     * constructor for country data access object
     * Use the defined lambda expression to convert UTC time to local time
     */
    public CountryDAOImp() {
        try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_ALL)){
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                allCountries.add(new Country(resultSet.getInt("Country_ID"),
                        resultSet.getString("Country"),
                        toLocalTime.convert(resultSet.getTimestamp("Create_Date").toLocalDateTime()),
                        resultSet.getString("Created_By"),
                        toLocalTime.convert(resultSet.getTimestamp("Last_Update").toLocalDateTime()),
                        resultSet.getString("Last_Updated_By")));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * get all the country object
     * @return  observable list for all country object
     */
    @Override
    public ObservableList<Country> getAllCountry() {
        return allCountries;
    }

    /**
     * look up country by id
     * @param CountryId coutry id
     * @return country object with matching id
     */

    public Country lookUpCountry(int CountryId){
        for (Country country:allCountries){
            if(country.getCountryId()==CountryId){
                return country;
            }
        }
        return null;
    }
}
