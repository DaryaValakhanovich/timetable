import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        fixTimetable();
    }
    private static void fixTimetable(){
        StringBuilder result = new StringBuilder();
        System.out.println("Enter filepath:");
        Scanner in = new Scanner(System.in);
        String fileName = in.next();
        Path path = Paths.get(fileName);
        try (Scanner scanner = new Scanner(path)) {
            String[] subStr;
            ArrayList<Service> services = new ArrayList();
            while (scanner.hasNextLine()) {
                String aa = scanner.nextLine();
                subStr = aa.split(" ");
                addService(new Service(subStr[0], Time.valueOf(subStr[1] + ":00"),
                        Time.valueOf(subStr[2] + ":00")), services);
            }
            scanner.close();

            ArrayList<Service> grottyServices = new ArrayList();
            for (int i = 0; i < services.size(); i++) {
                if (services.get(i).getName().equals("Grotty")) {
                    grottyServices.add(services.get(i));
                    services.remove(i);
                    i--;
                }
            }
            services.stream()
                    .sorted(Comparator.comparing(Service::getDepartureTime))
                    .forEach(a -> result.append(a.toString()).append("\n"));
            result.append("\n");
            grottyServices.stream()
                    .sorted(Comparator.comparing(Service::getDepartureTime))
                    .forEach(a -> result.append(a.toString()).append("\n"));
        } catch (IOException ex) {
            System.out.println("File reading error");
        }

        try (FileWriter writer = new FileWriter("output.txt", false)) {
            writer.write(result.toString());
        } catch (IOException ex) {
            System.out.println("File writing error");
        }
    }
    private static void addService(Service newService, ArrayList<Service> services) {
        if (!(newService.getArrivalTime().getTime() - newService.getDepartureTime().getTime() > 3600000)) {
            for (int i = 0; i < services.size(); i++) {
                if (newService.getDepartureTime().equals(services.get(i).getDepartureTime())) {
                    if (newService.getArrivalTime().before(services.get(i).getArrivalTime())) {
                        services.remove(i);
                        services.add(newService);
                    } else if (newService.getArrivalTime().equals(services.get(i).getArrivalTime())) {
                        if (newService.getName().equals("Posh")) {
                            services.remove(i);
                            services.add(newService);
                        }
                    }
                    return;
                }
                if (newService.getArrivalTime().equals(services.get(i).getArrivalTime())) {
                    if (newService.getDepartureTime().after(services.get(i).getDepartureTime())) {
                        services.remove(i);
                        services.add(newService);
                    }
                    return;
                }
                if (newService.getDepartureTime().after(services.get(i).getDepartureTime())) {
                    if (newService.getArrivalTime().before(services.get(i).getArrivalTime())) {
                        services.remove(i);
                        services.add(newService);
                        return;
                    }
                }
            }
            services.add(newService);
        }
    }

}
