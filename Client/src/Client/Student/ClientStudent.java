package Client.Student;

import Client.Admin.AdminOperations;
import authentication.AuthenticationOperations;
import authentication.AuthenticationOperationsService;
import org.omg.CORBA.ORB;

import org.omg.CosNaming.NamingContextExtHelper;
import server.DRRSServer;
import server.DRRSServerService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

public class ClientStudent {
    public static void main(String args[]) {
        String studentId = null;
        AuthenticationOperations authInterface = null;
        StudentOperations studentOperations = null;
        DRRSServer drrs = null;
        Scanner scanner = new Scanner(System.in);
        int campusPort = 0;

        // Ask if they want to see timeslots or book
        // if timeslot -> establish udp here
        boolean signIn = false;
        while(!signIn){
            System.out.println("What would you like to do?");
            System.out.println("\t1. Get available time slots");
            System.out.println("\t2. Sign in");
            System.out.println("\t3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // establish udp
                    try{
                        getAvailableTimeSlots(scanner);
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    signIn = true;
                    break;
                case 3:
                    System.out.println("System shutting down...");
                    System.exit(0);
                default:
                    System.out.println("Invalid input. System shut down...");
            }
        }

        // connect to authentication server
        do {
            try {
                URL url = new URL("http://127.0.0.1:9000/auth?wsdl");

                AuthenticationOperationsService service = new authentication.AuthenticationOperationsService(url);
                AuthenticationOperations authentication = service.getAuthenticationOperationsPort();
                studentOperations = new StudentOperations(authentication);
                campusPort = studentOperations.authenticateStudent();

                if (campusPort==-1){
                    System.out.println("Cannot authenticate student");
                }
            } catch (MalformedURLException urlException) {
                return;
            }
            studentId = studentOperations.getStudentID();

        } while (campusPort==-1);

        // connect to student server
        try {
            URL url = new URL("http://127.0.0.1:"+ campusPort +"/auth?wsdl");

            DRRSServerService service = new server.DRRSServerService(url);
            DRRSServer DRRSserver = service.getDRRSServerPort();
            studentOperations = new StudentOperations(DRRSserver, studentId);

            // ask action from student
            studentOperations.askActionFromStudent(studentId);

        } catch (MalformedURLException urlException) {
            return;
        }
    } //end main

    private static void getAvailableTimeSlots(Scanner scanner) throws IOException {

        System.out.println("Please enter the date (MM/DD/YYYY): ");
        String input = scanner.nextLine();

        // pack request to send over udp
        HashMap<String, Object> udpHashMap = new HashMap<>();
        udpHashMap.put("action", "getAvailableTimeSlots");
        udpHashMap.put("date", input);

        // set up the udp connection - connect to port 8000
        DatagramSocket socket = new DatagramSocket();

        // make packet and send
        byte[] outgoing = serialize(udpHashMap);
        DatagramPacket outgoingPacket = new DatagramPacket(outgoing, outgoing.length, InetAddress.getLocalHost(), 8000);
        socket.send(outgoingPacket);

        // receive request
        byte[] incoming = new byte[1000];
        DatagramPacket incomingPacket = new DatagramPacket(incoming, incoming.length);
        socket.receive(incomingPacket);

        StringBuilder result = data(incoming);

        if (result.length() == 0)
            System.out.println("No record found on " + input + "\n");
        else {
            System.out.println("Here are the records of available time slots on "+ input +": ");
            System.out.println(result);
        }

    }

    private static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

    private static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
}
