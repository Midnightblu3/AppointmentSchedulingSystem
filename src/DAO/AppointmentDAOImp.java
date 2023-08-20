package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 * implemented class hold all the appointment data access objects
 * define a lambda expression 'toUTC' to convert Local time to UTC time
 * define a lambda expression 'toLocalTime' convert UTC time to local time
 * @author Rui Huang
 */

public class AppointmentDAOImp implements AppointmentDAO{
    convertTime toUTC=t->t.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    convertTime toLocalTime = t->t.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    ObservableList<Appointment> allAppointments= FXCollections.observableArrayList();
    ObservableList<Appointment> filteredAppointments=FXCollections.observableArrayList();
    private static final String INSERT ="INSERT INTO client_schedule.appointments (Title,Description," +
            "Location,Type,Start,End,Create_Date,Created_By,Last_Update," +
            "Last_Updated_By,Customer_ID,User_ID,Contact_ID)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DELETE="DELETE FROM client_schedule.appointments WHERE Appointment_ID=?";
    private static final String GET_ALL="SELECT * FROM client_schedule.appointments";
    private static final String UPDATE="UPDATE client_schedule.appointments SET Title=?, Description=?," +
            " Location=?, Type=?, Start=?, End=?, Last_Update=?," +
            " Last_Updated_By=?,Customer_ID=?,User_ID=?, Contact_ID=?" +
            " WHERE Appointment_ID=?";
    private static final String GET_LAST_ID="SELECT Appointment_ID FROM client_schedule.appointments " +
            "WHERE Appointment_ID=(SELECT max(Appointment_ID) FROM client_schedule.appointments)";

    /**
     * constructor
     * use the lambda expression to convert UTC time to local time
     */

    public AppointmentDAOImp() {
        try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_ALL)){
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                allAppointments.add(new Appointment(resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                       toLocalTime.convert(resultSet.getTimestamp("Start").toLocalDateTime()),
                        toLocalTime.convert(resultSet.getTimestamp("End").toLocalDateTime()),
                        toLocalTime.convert(resultSet.getTimestamp("Create_Date").toLocalDateTime()),
                        resultSet.getString("Created_By"),
                        toLocalTime.convert(resultSet.getTimestamp("Last_Update").toLocalDateTime()),
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")));
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * add appointment to database
     * use defined lambda expression 'toUTC' to convert local time to UTC time to save time UTC to database
     * @param appointment appointment that will be added
     */

    @Override
    public void addAppointment(Appointment appointment) {
        try(PreparedStatement statement=JDBC.connection.prepareStatement(INSERT)){
            statement.setString(1,appointment.getTitle());
            statement.setString(2,appointment.getDescription());
            statement.setString(3,appointment.getLocation());
            statement.setString(4,appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(toUTC.convert(appointment.getStartDate())));
            statement.setTimestamp(6,Timestamp.valueOf(toUTC.convert(appointment.getEndDate())));
            statement.setTimestamp(7,Timestamp.valueOf(toUTC.convert(appointment.getCreateDate())));
            statement.setString(8,appointment.getCreateBy());
            statement.setTimestamp(9,Timestamp.valueOf(toUTC.convert(appointment.getLastUpdate())));
            statement.setString(10,appointment.getLastUpdateBy());
            statement.setInt(11,appointment.getCustomerId());
            statement.setInt(12,appointment.getUserId());
            statement.setInt(13,appointment.getContactId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try(PreparedStatement statement=JDBC.connection.prepareStatement(GET_LAST_ID)){
            int id=0;
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                id=resultSet.getInt("Appointment_ID");
            }
            appointment.setAppointmentId(id);
            allAppointments.add(appointment);
            System.out.println(toUTC.convert(appointment.getStartDate()));
            System.out.println(toUTC.convert(appointment.getEndDate()));
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * delete an appointment from data base
     * @param appointment the appointment that will be deleted
     * @return true of false on whether appointment is deleted
     */

    @Override
    public boolean deleteAppointment(Appointment appointment) {
        if(this.allAppointments.contains(appointment)) {
            try (PreparedStatement statement = JDBC.connection.prepareStatement(DELETE)) {
                statement.setInt(1, appointment.getAppointmentId());
                statement.execute();
                allAppointments.remove(appointment);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else{
            return false;
        }
    }

    /**
     * get all the appointment from the list
     * @return observable list for all the appointment
     */

    @Override
    public ObservableList<Appointment> getAllAppointment() {
        return this.allAppointments;
    }

    /**
     * get filtered appointment with customer id
     * @param customerId customer id to filter the appointment
     * @return filtered list
     */

    @Override
    public ObservableList<Appointment> getFilteredAppointment(int customerId) {
        ObservableList<Appointment> filteredList=FXCollections.observableArrayList();
        for(Appointment appointment:allAppointments){
            if(appointment.getCustomerId()==customerId){
                filteredList.add(appointment);
            }
        }
        return filteredList;
    }

    /**
     * look up appointment by appointment id
     * @param appointmentId id that used look appointment
     * @return appointment
     */

    @Override
    public Appointment lookupAppointment(int appointmentId) {
        for(Appointment appointment:this.allAppointments){
            if (appointment.getAppointmentId()==appointmentId){
                return appointment;
            }
        }
        return null;
    }
    /**
     * update an appointment to the database
     * use defined lambda 'toUTC' expression to convert Local time to UTC time to save it in the database
     * @param appointment the appointment will be be updated to the database
     */

    @Override
    public void updateAppointment(Appointment appointment) {
        try(PreparedStatement statement=JDBC.connection.prepareStatement(UPDATE)){
            statement.setString(1,appointment.getTitle());
            statement.setString(2,appointment.getDescription());
            statement.setString(3,appointment.getLocation());
            statement.setString(4,appointment.getType());
            /**
             *
             */
            statement.setTimestamp(5,Timestamp.valueOf(toUTC.convert(appointment.getStartDate())));
            statement.setTimestamp(6,Timestamp.valueOf(toUTC.convert(appointment.getEndDate())));
            statement.setTimestamp(7,Timestamp.valueOf(toUTC.convert(appointment.getLastUpdate())));
            statement.setString(8,appointment.getLastUpdateBy());
            statement.setInt(9,appointment.getCustomerId());
            statement.setInt(10,appointment.getUserId());
            statement.setInt(11,appointment.getContactId());
            statement.setInt(12,appointment.getAppointmentId());
            statement.execute();
            appointment.setCreateDate(this.lookupAppointment(appointment.getAppointmentId()).getCreateDate());
            appointment.setCreateBy(this.lookupAppointment(appointment.getAppointmentId()).getCreateBy());
            appointment.setCreateDate(this.lookupAppointment(appointment.getAppointmentId()).getCreateDate());
            this.allAppointments.set(allAppointments.indexOf(this.lookupAppointment(appointment.getAppointmentId())),appointment);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * get list of the appointment this month
     * @return list of appointment for this month
     */

    @Override
    public ObservableList<Appointment> getMonthlyList() {

        YearMonth yearMonth = YearMonth.of( LocalDateTime.now().getYear(), LocalDateTime.now().getMonth() );
        LocalDate firstOfMonth = yearMonth.atDay( 1 );
        LocalDate last = yearMonth.atEndOfMonth();
        ObservableList<Appointment> appThisMonth=FXCollections.observableArrayList();
        for(Appointment appointment:allAppointments){
            if((appointment.getEndDate().toLocalDate().isEqual(firstOfMonth)||appointment.getEndDate().toLocalDate().isAfter(firstOfMonth))&&
                    (appointment.getEndDate().toLocalDate().isEqual(last)||appointment.getEndDate().toLocalDate().isBefore(last))){
                appThisMonth.add(appointment);
            }
        }
        return appThisMonth;
    }
    /**
     * get list of the appointment this week
     * @return list of appointment for this week
     */

    @Override
    public ObservableList<Appointment> getWeeklyList() {
        ObservableList<Appointment> appThisWeek=FXCollections.observableArrayList();
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        LocalDate startOfCurrentWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        DayOfWeek lastDayOfWeek = firstDayOfWeek.plus(6);
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
        for(Appointment appointment:allAppointments){
            if((appointment.getEndDate().toLocalDate().isEqual(startOfCurrentWeek)||appointment.getEndDate().toLocalDate().isAfter(startOfCurrentWeek))&&
                    (appointment.getEndDate().toLocalDate().isEqual(endOfWeek)||appointment.getEndDate().toLocalDate().isBefore(endOfWeek))){
                appThisWeek.add(appointment);
            }
        }
        return appThisWeek;
    }

    /**
     * get list of the upcoming appointment
     * @return list of upcoming appointment
     */

    @Override
    public ArrayList<Appointment> upComingAppointment() {
        ArrayList<Appointment> upComingApp=new ArrayList<>();
        for(Appointment appointment: allAppointments){
            if(appointment.getUserId()==UserDAOImp.getCurrentUser().getUserId()) {
                if (((appointment.getStartDate().isAfter(LocalDateTime.now()) || appointment.getStartDate().isEqual(LocalDateTime.now())) &&
                        (appointment.getStartDate().isBefore(LocalDateTime.now().plusMinutes(15)) || appointment.getStartDate().isEqual(LocalDateTime.now().plusMinutes(15))))) {
                    upComingApp.add(appointment);
                }
            }
        }
        return upComingApp;
    }
}
