package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
/**
 * class for first level division Data access object implementation
 * define the lambda expression 'toLocalTime' to convert UTC time to local time
 * use the defined lambda expression to convert UTC time to local time to save in the observable list
 * @author Rui Huang
 */

public class FirstLevelDivisionDAOImp implements FirstLevelDivisionDAO {
    convertTime toLocalTime;
    ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivision> filteredFirstLevelDivisions = FXCollections.observableArrayList();
    private static final String GET_ALL = "SELECT * FROM client_schedule.first_level_divisions";

    /**
     * Constructor for first level division Data access object implementation
     * define the lambda expression to convert UTC time to local time
     * use the defined lambda expression to convert UTC time to local time
     */

    public FirstLevelDivisionDAOImp() {
        toLocalTime=t->t.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        try (PreparedStatement statement = JDBC.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.allFirstLevelDivisions.add(new FirstLevelDivision(resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        toLocalTime.convert(resultSet.getTimestamp("Create_Date").toLocalDateTime()),
                        resultSet.getString("Created_By"),
                        toLocalTime.convert(resultSet.getTimestamp("Last_Update").toLocalDateTime()),
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Country_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * return all first level division with country Id
     * @param countryId country id to get all the first level division object
     * @return all the first level division with country Id
     */

    @Override
    public ObservableList<FirstLevelDivision> getFilteredFirstLevelDivision(int countryId) {
        filteredFirstLevelDivisions.clear();
        for(FirstLevelDivision firstLevelDivision:this.allFirstLevelDivisions){
            if(firstLevelDivision.getCountryId()==countryId){
                this.filteredFirstLevelDivisions.add(firstLevelDivision);
            }
        }
        return filteredFirstLevelDivisions;
    }

    /**
     * look up first level division object with id
     * @param divisionId id used to look up the object
     * @return first level division object with id
     */
    public FirstLevelDivision lookUpDivision(int divisionId){
        for(FirstLevelDivision division:allFirstLevelDivisions){
            if (division.getDivisionId()==divisionId){
                return division;
            }
        }
        return null;
    }


}


