package DAO;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * functional interface for Lambda expression to load forms
 * @author Rui Huang
 */
public interface LoadForm {
    /**
     * Load method that load the form
     * @param parent parent of the scene
     * @param event the event happened when button is clicked
     * @param root url of the fxml file
     * @param title title of the stage
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    void load(Parent parent,ActionEvent event, String root, String title) throws IOException;
}
