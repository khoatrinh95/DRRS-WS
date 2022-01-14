package UDP;

import Business.TimeSlot;
import Server.DRRSServer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UdpOperations implements Runnable{
    private Thread thread;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private List<DRRSServer> listOfServers;
    private HashMap<String, Object> receivedHashMap;

    public UdpOperations(DatagramSocket socket, DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
        this.listOfServers = DRRSServer.getServerList();
        try {
            receivedHashMap = (HashMap<String, Object>) deserialize(packet.getData());
        } catch (IOException e) {
            System.out.println("Cannot deserialize request");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot find appropriate class to cast");
        }
    }
    @Override
    public void run() {
        String operation = (String) receivedHashMap.get("action");
        String result ="";
        if (operation.equalsIgnoreCase("getAvailableTimeSlots")){
            String date = (String) receivedHashMap.get("date");
            date = date.substring(0,10);

            result = getAvailableTimeSlots(date);
        }
        else if (operation.equalsIgnoreCase("changeReservation")) {
            String bookingID = (String) receivedHashMap.get("bookingID");
            String studentID = (String) receivedHashMap.get("studentID");
            String newCampusName = (String) receivedHashMap.get("newCampusName");
            String date = (String) receivedHashMap.get("date");
            String newRoomNo = (String) receivedHashMap.get("newRoomNo");
            String newTimeSlot = (String) receivedHashMap.get("newTimeSlot");

            result = changeReservation(bookingID, studentID, newCampusName, newRoomNo, date, newTimeSlot);
        }

        // sending result back to client
        try {
            byte buf[] = null;

            buf = result.getBytes();

            // send the packet
            DatagramPacket sendingPacket = new DatagramPacket(buf, buf.length, this.packet.getAddress(), this.packet.getPort());
            socket.send(sendingPacket);
        } catch (IOException e){
            System.out.println("Error sending packet back to client");
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, "Udp Process");
            thread.start();
        }
    }

    public String getAvailableTimeSlots(String date) {
        String result ="";

        if (date.isEmpty())
            return "Please enter valid date";

        for (DRRSServer server : DRRSServer.getServerList()){
            int count = 0;
            HashMap<String, List<TimeSlot>> roomMap = server.getDataMap().get(date);


            // if no room in this date
            if (roomMap==null) {
                continue;
            }
            else {
                for (Map.Entry<String,List<TimeSlot>> room : roomMap.entrySet()){
                    List<TimeSlot> timeSlotList = room.getValue();
                    for (TimeSlot timeslot : timeSlotList){
                        if (timeslot.getBookedByID()==null){
                            count++;
                        }
                    }
                }
            }
            result += server.getCampusName() + ": " + count + "\n";
        }
        return result;
    }

    public String changeReservation(String bookingID, String studentID, String newCampusName, String newRoomNo, String date, String newTimeSlot) {
        // take the campusName -> get the DRRSserver

        DRRSServer targetServer = null;
        for (DRRSServer server : listOfServers) {
            if (server.getCampusName().equalsIgnoreCase(newCampusName)) {
                targetServer = server;
            }
        }
        // let the DRRSserver do the bookRoom
        String bookRoomResult = targetServer.bookRoom(studentID, newCampusName, newRoomNo, date, Integer.parseInt(newTimeSlot));

        // if book room successful, cancel the old booking
        if (bookRoomResult.contains("confirmed")) {
            String cancelRoomResult = targetServer.cancelBooking(bookingID, studentID);

            // if cancel successful -> return result
            if (cancelRoomResult.contains("confirmed")) {
                return cancelRoomResult + "\n**NEW: " + bookRoomResult;
            }

            // if cancel not successful -> cancel the new booking
            else {
                String newBookingID = bookRoomResult.substring(bookRoomResult.length()-5);
                String cancelNewBooking = targetServer.cancelBooking(newBookingID, studentID);

                return cancelRoomResult;
            }
        }
        // if book new room not successful -> return error
        else {
            System.out.println("if book new room not successful -> return error***" + bookRoomResult);
            return bookRoomResult;
        }
    }

    private static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
}
