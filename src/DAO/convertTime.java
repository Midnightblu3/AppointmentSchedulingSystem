package DAO;
import java.time.LocalDateTime;

/**
 * Functional interface for lambda expression that convert local time to time in another timezone
 * @author Rui Huang
 */

public interface convertTime {
    /**
     * convert local time to time in another timezone
     * @param localDateTime time that will be converted
     * @return converted time in another time zone
     */
    LocalDateTime convert(LocalDateTime localDateTime);
}
