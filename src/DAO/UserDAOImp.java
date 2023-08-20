package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * class for user data access object
 * define the lambda expression to convert UTC time to local time
 * use defined lambda expression to convert UTC time to local time
 * @author Rui Huang
 */

public class UserDAOImp implements UserDAO{
    private static final String GET_ALL ="SELECT * FROM client_schedule.users";
    private ObservableList<User> allUsers= FXCollections.observableArrayList();
    public static User currentUser;
    convertTime toLocalTime;


    /**
     * constructor method that load all the user from the database to User data access object
     * define the lambda expression to convert UTC time to local time
     * use defined lambda expression to convert UTC time to local time
     */

    public UserDAOImp() {

        toLocalTime=t->t.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
       try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_ALL);){
           ResultSet resultSet=statement.executeQuery();
           while (resultSet.next()){
               allUsers.add(new User(resultSet.getInt("User_ID"),
                       resultSet.getString("User_Name"),
                       resultSet.getString("Password"),

                       toLocalTime.convert(resultSet.getTimestamp("Create_Date").toLocalDateTime()),
                       resultSet.getString("Created_By"),
                       toLocalTime.convert(resultSet.getTimestamp("Last_Update").toLocalDateTime()),
                       resultSet.getString("Last_Updated_By")));
           }
       }catch (SQLException e){
           e.printStackTrace();
           throw new RuntimeException();
       }
    }
    /**
     * check if it is valid user in the database
     * @param userName User's user name
     * @param password User's password
     * @return true when it is a valid user false when not able to find the user with matching name and password
     */

    @Override
    public boolean isValidUser(String userName,String password) {
        for(User u: allUsers){
            if (u.getUserName().equals(userName)&&u.getPassword().equals(password)){
                setCurrentUser(u);
                return true;
            }
        }
        return false;
    }

    /**
     * get all the user from the data base
     * @return list of user
     */

    @Override
    public ObservableList<User> getAllUser() {
        return allUsers;
    }

    /**
     * look up user by user Id
     * @param userId Id used to look up the user
     * @return the User object with matching Id
     */

    @Override
    public User lookUpUser(int userId) {
        for(User user:allUsers){
            if(user.getUserId()==userId){
                return user;
            }
        }
        return null;
    }

    /**
     * get the current user using the application
     * @return  the current user object
     */

    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * set the current user using the application
     * @param currentUser  the current user object
     */

    public static void setCurrentUser(User currentUser) {
        UserDAOImp.currentUser = currentUser;
    }
}
