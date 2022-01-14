package Client.Admin;

import authentication.AuthenticationOperations;
import server.DRRSServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AdminOperations {
    private AuthenticationOperations authenInterface;
    private DRRSServer drrsServerInterface;
    private String adminId;
    private Logger log;

    public AdminOperations(AuthenticationOperations authenInterface) {
        this.authenInterface = authenInterface;
    }

    public AdminOperations(DRRSServer drrsServerInterface, String adminId) {
        this.adminId = adminId;
        this.drrsServerInterface = drrsServerInterface;
        startLogger(adminId);
    }

    public String getAdminId() {
        return adminId;
    }

    public int authenticateAdmin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your admin ID: ");
        String input = sc.nextLine();
        adminId = input;
        return authenInterface.authorize(input, true);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void askActionFromAdmin(int campusPort) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        boolean done = false;
        while (!done) {
            System.out.println("Please enter the option you would like to do: ");
            System.out.println("\t1. Create room from input");
            System.out.println("\t2. Create room from file");
            System.out.println("\t3. Delete room");
            System.out.println("\t4. Exit");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(createRoomFromInput(sc));
                    break;
                case 2:
                    createRoomFromFile(sc, "src/Files/RoomCreationTemplate");
                    break;
                case 3:
                    System.out.println(deleteRoom(sc, campusPort));
                    break;
                case 4:
                    done = true;
                    sc.close();
                    return;
            }
        }
    }

    private String createRoomFromInput(Scanner scanner) {
        String room = "";
        String date = "";
        String begin = "";
        String end = "";
        System.out.println("Please enter the room number: ");
        room = scanner.nextLine();
        System.out.println("Please enter the date (MM/DD/YYYY): ");
        date = scanner.nextLine();
        System.out.println("The system will create 2 hour slots from the beginning time to end time");
        System.out.println("Please enter the begin time (HH:MM): ");
        begin = scanner.nextLine();
        System.out.println("Please enter the end time (HH:MM): ");
        end = scanner.nextLine();
        log.info("Room creation: " + room +
                "\n\tDate: " + date +
                "\n\tAdmin ID: " + adminId
        );
        return drrsServerInterface.createRoom(room, date, begin, end);
    }

    private void createRoomFromFile(Scanner scanner, String fileName) {
        String room = "";
        String date = "";
        String begin = "";
        String end = "";
        try {
            File myObj = new File(fileName);
            scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] results = line.split(" ");
                date = results[0];
                room = results[1];
                begin = results[2];
                end = results[3];
                String isSuccessful = drrsServerInterface.createRoom(room, date, begin, end);
                System.out.println(isSuccessful);
                log.info("Room creation: " + room +
                        "\n\tDate: " + date +
                        "\n\tAdmin ID: " + adminId);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read file");
            e.printStackTrace();
        }
    }

    private String deleteRoom(Scanner scanner, int campusPort) {
        String campusName = null;
        switch (campusPort) {
            case 8100:
                campusName = "westmount";
                break;
            case 8200:
                campusName = "kirkland";
                break;
            case 8300:
                campusName = "dorval";
                break;
            default:
                campusName = "";
                break;
        }
        String room = "";
        String date = "";
        String timeslots = "";
        System.out.println("Please enter the room number: ");
        room = scanner.nextLine();
        System.out.println("Please enter the date (MM/DD/YYYY): ");
        date = scanner.nextLine();

        List<String> listOfTimeSlots = (drrsServerInterface.getTimeSlots(date, room, campusName));
        if (listOfTimeSlots.size() != 0) {
            System.out.println("Here are the time slots in the room: ");
            for (String s : listOfTimeSlots) {
                System.out.println(s);
            }
            System.out.println("Please enter the time slot numbers you would like to delete (separate by space): ");
            timeslots = scanner.nextLine();
            List<String> timeSlotArray = Arrays.asList(timeslots.split(" "));
            log.info("Room deletion: " + room +
                    "\n\tDate: " + date +
                    "\n\tAdmin ID: " + adminId);
            return drrsServerInterface.deleteRoom(room, date, timeSlotArray);
        }
        return "There is no time slots available in the date and room selected";
    }

    private void startLogger(String adminId) {
        try {
            log =  Logger.getLogger(adminId);
            log.setUseParentHandlers(false);
            String loggerFileName = "src/UserLogger/Admin/"+adminId+".log";
            FileHandler fileHandler = new FileHandler(loggerFileName, true);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
