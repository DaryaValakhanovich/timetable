import java.sql.Time;
import java.time.format.DateTimeFormatter;

public class Service {
    private String name;
    private Time departureTime;
    private Time arrivalTime;

    public Service(String name, Time departureTime, Time arrivalTime) {
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return name;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return name + ' '
                + departureTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " "
                + arrivalTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
