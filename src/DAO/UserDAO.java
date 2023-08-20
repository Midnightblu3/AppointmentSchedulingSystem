package DAO;

import javafx.collections.ObservableList;
import model.User;

import java.util.ArrayList;

/**
 * interface for user data access object
 * @author Rui Huang
 */

public interface UserDAO {
    /**
     * check if it is valid user in the database
     * @param userName User's user name
     * @param password User's password
     * @return true when it is a valid user false when not able to find the user with matching name and password
     */
    boolean isValidUser(String userName,String password);

    /**
     * get all the user from the data base
     * @return list of user
     */
    ObservableList<User> getAllUser();

    /**
     * look up user by user Id
     * @param userId Id used to look up the user
     * @return the User object with matching Id
     */
    User lookUpUser(int userId);
}
