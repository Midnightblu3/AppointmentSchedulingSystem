package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

import java.util.ArrayList;

/**
 * interface for appointmentDAO
 * @author Rui Huang
 */

public interface AppointmentDAO {
    /**
     * add appointment to data base
     * @param appointment appointment that will be added
     */
    void addAppointment(Appointment appointment);

    /**
     * delete an appointment from data base
     * @param appointment the appointment that will be deleted
     * @return true of false on whether appointment is deleted
     */
    boolean deleteAppointment(Appointment appointment);

    /**
     * get all the appointment from the list
     * @return observable list for all the appointment
     */
    ObservableList<Appointment> getAllAppointment();

    /**
     * get filtered appointment with customer id
     * @param customerId customer id to filter the appointment
     * @return filtered list
     */
    ObservableList<Appointment> getFilteredAppointment(int customerId);

    /**
     * look up appointment by appointment id
     * @param appointmentId id that used look appointment
     * @return appointment
     */
    Appointment lookupAppointment(int appointmentId);

    /**
     * update an appointment to the data base
     * @param appointment the appointment will be be updated to the database
     */
    void updateAppointment(Appointment appointment);

    /**
     * get list of the appointment this month
     * @return list of appointment for this month
     */
    ObservableList<Appointment> getMonthlyList();
    /**
     * get list of the appointment this week
     * @return list of appointment for this week
     */
    ObservableList<Appointment> getWeeklyList();
    /**
     * get list of the upcoming appointment
     * @return list of upcoming appointment
     */
    ArrayList<Appointment> upComingAppointment();
}
