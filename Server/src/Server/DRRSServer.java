package Server;

import Business.HashMapWrapper;
import Business.TimeSlot;
import org.omg.CORBA.ORB;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@WebService
public class DRRSServer{
    private String campusName;
    private String campusCode;
    private HashMap <String, HashMap<String, List<TimeSlot>>> dataMap;
    private Logger log;
    private int campusIndex;
    private static int index = 0;
    private static List<DRRSServer> serverList = new ArrayList<>();
    private static HashMap<String, TimeSlot> bookingIDMap= new HashMap<>();
    private static HashMap<String, Integer> studentBookingMap = new HashMap<>();
    private static LocalDateTime startOfWeek;
    private static final Object lockRoom = new Object();
    private static final Object lockAction = new Object();

    public DRRSServer() {
        super();
    }

    public DRRSServer(String campus){
        super();
        this.campusName = campus;
        dataMap = new HashMap <>();
        serverList.add(this);
        startOfWeek = LocalDateTime.now();
        campusIndex = index;
        index++;
        String campusNameLowerCase = campusName.toLowerCase();
        switch (campusNameLowerCase) {
            case "westmount":
                campusCode = "WST";
                break;
            case "dorval" :
                campusCode = "DVL";
                break;
            case "kirkland" :
                campusCode = "KKL";
                break;
            default:
                campusCode = "default";
                break;
        }
        // instantiate Logger file
        startLogger(campus);

        log.info(campus + " Server created");
    }

    public String createRoom(String room_Number, String date, String beginTime, String endTime){
        synchronized (lockAction) {
            Entry entry = new Entry(room_Number, date, beginTime, endTime);
            if (!entry.isEntryValid())
                return "Please provide a valid input";
            // if date already exists
            if (dataMap.containsKey(entry.date)) {
                // if date and room already exist
                if (dataMap.get(entry.date).containsKey(entry.room_Number)){
                    LocalTime begin = LocalTime.parse(entry.beginTime);
                    LocalTime end = LocalTime.parse(entry.endTime);
                    List<TimeSlot> list = dataMap.get(entry.date).get(entry.room_Number);

                    // if there is no time overlap
                    if (list.get(0).getBeginTime().isAfter(end) ||
                            list.get(list.size()-1).getEndTime().isBefore(begin) || list.get(0).getBeginTime().equals(end) ||
                            list.get(list.size()-1).getEndTime().equals(begin)){
                        // append new slot
                        list.addAll(createTimeSlot(entry));

                        // fix index and sort order i
                        fixInDexAndSortOrder(list);

                        log.info("Time slots creation: " + room_Number +
                                "\n\tDate: " + date +
                                "\n\tSUCCESS"
                        );
                    }
                    else {
                        log.info("Time slots creation: " + room_Number +
                                "\n\tDate: " + date +
                                "\n\tFAIL"
                        );
                        return "This entry already exists or overlaps with an existing entry in the system";
                    }
                }
                else {
                    // append new room # and add new slot
                    HashMap<String, List<TimeSlot>> dateMap= new HashMap<>();
                    dataMap.get(entry.date).put(entry.room_Number, createTimeSlot(entry));
                    log.info("Room creation: " + room_Number +
                            "\n\tDate: " + date +
                            "\n\tSUCCESS"
                    );
                }
            }
            // if date not exist
            else {
                HashMap<String, List<TimeSlot>> dateMap= new HashMap<>();
                dateMap.put(entry.room_Number, createTimeSlot(entry));
                dataMap.put(entry.date, dateMap);
                log.info("Room creation: " + room_Number +
                        "\n\tDate: " + date +
                        "\n\tSUCCESS"
                );
            }
            return "Creation Successful";
        }
    }

    /*
    user must provide a list of ints representing the index of the time slots
     */
    public String deleteRoom(String room_Number, String date, String[] listOfTimeSlotsIndexFromInputString) {
        synchronized (lockAction) {
            List<Integer> listOfTimeSlotsIndexFromInput = new ArrayList<>();
            for (String s : listOfTimeSlotsIndexFromInputString){
                listOfTimeSlotsIndexFromInput.add(Integer.parseInt(s));
            }
            Entry entry = new Entry(room_Number, date, listOfTimeSlotsIndexFromInput);

            // verify if input valid
            if (!entry.isEntryValidWithList())
                return "Please provide a valid input";

            // check if date and room exist
            if (!foundDateAndRoom(entry, this))
                return "Cannot find Date or Room indicated";

            // here, date and room are found
            List<TimeSlot> list = dataMap.get(entry.date).get(entry.room_Number);
            List<Integer> listOfTimeSlotsIndex = new ArrayList<>();
            for (TimeSlot t : list){
                listOfTimeSlotsIndex.add(t.getIndex());
            }

            // if time slots are found
            // if room is booked, delete booking id from bookingIDList, reduce student booking count
            if (listOfTimeSlotsIndex.containsAll(listOfTimeSlotsIndexFromInput)) {
                for (TimeSlot timeslot : list){
                    if (listOfTimeSlotsIndexFromInput.contains(timeslot.getIndex())){
                        if (timeslot.getBookingID()!=null){
                            String bookingID = timeslot.getBookingID();
                            String studentID = timeslot.getBookedByID();
                            int countOfStudentBooking = studentBookingMap.get(studentID);
                            countOfStudentBooking--;
                            studentBookingMap.put(studentID, countOfStudentBooking);
                            bookingIDMap.remove(bookingID);
                        }
                    }
                }
                list.removeIf(t -> listOfTimeSlotsIndexFromInput.contains(t.getIndex()));
                fixInDexAndSortOrder(list);

                log.info("Room deletion: " + room_Number +
                        "\n\tDate: " + date +
                        "\n\tSUCCESS"
                );

                return "Deletion successful";
            }
            // if can't find time slot
            log.info("Room deletion: " + room_Number +
                    "\n\tDate: " + date +
                    "\n\tFAIL"
            );
            return "Cannot find the time slot(s) indicated";
        }
    }

    public String bookRoom(String studentID, String campusName, String roomNumber, String date, int timeslot) {
        // check valid input
        Entry entry = new Entry(campusName, roomNumber, date, timeslot, studentID);
        if (!entry.isEntryValidWithStudentID())
            return "Please provide a valid input";

        // search campus
        DRRSServer server = lookupServer(campusName);

        // search date and room number and timeslot
        if (server != null) {
            if (!foundDateAndRoom(entry, server))
                return "Cannot find Date or Room indicated";

            // here, date and room are found
            List<TimeSlot> list = server.dataMap.get(date).get(roomNumber);
            List<Integer> listOfTimeSlotsIndex = new ArrayList<>();
            for (TimeSlot t : list){
                listOfTimeSlotsIndex.add(t.getIndex());
            }
            if (listOfTimeSlotsIndex.contains(timeslot)) {
                TimeSlot targetTimeSlot =  list.get(timeslot-1);

                synchronized (lockRoom) {
                    if (targetTimeSlot.getBookedByID() == null) {
                        String bookingID = assignBookingID();
                        targetTimeSlot.setBookedByID(studentID);
                        targetTimeSlot.setBookingID(bookingID);
                        bookingIDMap.put(bookingID, targetTimeSlot);

                        if (studentBookingMap.containsKey(studentID)){
                            int count = studentBookingMap.get(studentID);
                            count++;
                            studentBookingMap.put(studentID, count);
                        }
                        else {
                            studentBookingMap.put(studentID,1);
                        }
                        log.info("Room booked: " + roomNumber +
                                "\n\tDate: " + date +
                                "\n\tStudent ID: " + studentID +
                                "\n\tBooking ID: " + bookingID +
                                "\n\tSUCCESS"
                        );
                        return "Booking confirmed - your booking ID is " + bookingID;
                    }
                    else {
                        log.info("Room booked: " + roomNumber +
                                "\n\tDate: " + date +
                                "\n\tStudent ID: " + studentID +
                                "\n\tFAIL"
                        );
                        return "Timeslot already booked. Please choose a different slot";
                    }
                }
            }
        }

        else
            return "Cannot find Campus indicated";

        return "Cannot find the timeslot given";
    }

    public String getAvailableTimeSlot(String date) {
        String result ="";

        if (date.isEmpty())
            return "Please enter valid date";

        for (DRRSServer server : serverList){
            int count = 0;
            HashMap<String, List<TimeSlot>> roomMap = server.dataMap.get(date);

            // if no room in this date
            if (roomMap==null) continue;
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
            result += server.campusName + ": " + count + "\n";
        }
        return result;
    }

    public String cancelBooking(String bookingID, String studentId){
        if (bookingIDMap.containsKey(bookingID)){
            TimeSlot timeSlot = bookingIDMap.get(bookingID);

            if(timeSlot.getBookedByID().equalsIgnoreCase(studentId)){
                timeSlot.setBookingID(null);
                timeSlot.setBookedByID(null);
                bookingIDMap.remove(bookingID);

                int count = studentBookingMap.get(studentId);
                count--;
                studentBookingMap.put(studentId,count);

                log.info("Booking cancellation: " +
                        "\n\tStudent ID: " + studentId +
                        "\n\tBooking ID: " + bookingID +
                        "\n\tSUCCESS"
                );

                return "Booking " + bookingID + " cancellation confirmed";
            }
            else {
                log.info("Booking cancellation: " +
                        "\n\tStudent ID: " + studentId +
                        "\n\tBooking ID: " + bookingID +
                        "\n\tFAIL"
                );
                return "Cannot cancel booking because your studentID does not match the one who booked this slot";
            }

        }
        else {
            log.info("Booking cancellation: " +
                    "\n\tStudent ID: " + studentId +
                    "\n\tBooking ID: " + bookingID +
                    "\n\tFAIL"
            );
            return "Cannot find record of your booking ID";
        }
    }

    public String changeReservation(String studentID, String bookingID, String newCampusName, String date, String newRoomNo, String newTimeSlot) {
        String result = "";
        try {
            // pack request to send over udp
            HashMap<String, Object> udpHashMap = new HashMap<>();
            udpHashMap.put("action", "changeReservation");
            udpHashMap.put("studentID", studentID);
            udpHashMap.put("bookingID", bookingID);
            udpHashMap.put("newCampusName", newCampusName);
            udpHashMap.put("date", date);
            udpHashMap.put("newRoomNo", newRoomNo);
            udpHashMap.put("newTimeSlot", newTimeSlot);

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

            StringBuilder stringBuilder = data(incoming);
            result = stringBuilder.toString();
        } catch (IOException e) {
            log.info("Cannot connect to UDP Server");
        }

        return result;
    }


    public String[] getTimeSlots (String date, String room, String campusName){
        String[] listOfTimeSlotsArray = {};
        Entry entry = new Entry(room, date, null);

        int campusIndex = getCampusIndex(campusName);

        if (campusIndex == -1)
            return listOfTimeSlotsArray;

        // verify if input valid
        // check if date and room exist
        if (!foundDateAndRoom(entry, serverList.get(campusIndex)))
            return listOfTimeSlotsArray;

        // here, date and room are found
        List<TimeSlot> list = serverList.get(campusIndex).dataMap.get(entry.date).get(entry.room_Number);
        List<String> listOfTimeSlots = new ArrayList<>();
        for (TimeSlot t : list){
            listOfTimeSlots.add(Integer.toString(t.getIndex()) + ". " + t.getBeginTime() + " - " + t.getEndTime());
        }

        listOfTimeSlotsArray = new String[listOfTimeSlots.size()];
        listOfTimeSlotsArray = listOfTimeSlots.toArray(listOfTimeSlotsArray);

        return listOfTimeSlotsArray;
    }

    public boolean overLimit (String studentID){
        // check if a week has passed
        resetBookingCount();

        if (!studentBookingMap.containsKey(studentID))
            return false;
        if (studentBookingMap.containsKey(studentID) && studentBookingMap.get(studentID)<3)
            return false;
        else
            return true;
    }

    private List<TimeSlot> createTimeSlot (Entry entry) {
        List<TimeSlot> timeSlotList = new ArrayList<>();
        LocalTime begin = LocalTime.parse(entry.beginTime);
        LocalTime end = LocalTime.parse(entry.endTime);
        int index = 1;
        while (begin.isBefore(end)){
            TimeSlot timeSlot = new TimeSlot(begin, begin.plusHours(2), index);
            timeSlotList.add(timeSlot);
            index++;
            begin = begin.plusHours(2);
        }
        return timeSlotList;
    }

    private DRRSServer lookupServer (String serverName) {
        for (DRRSServer s: serverList){
            if (s.campusName.equalsIgnoreCase(serverName))
                return s;
        }
        return null;
    }

    private boolean foundDateAndRoom (Entry entry, DRRSServer serverToLookupIn) {
        HashMap<String, List<TimeSlot>> roomMap = serverToLookupIn.dataMap.get(entry.date);
        if (roomMap == null || roomMap.size() == 0)
            return false;

        if (entry.room_Number != null){
            List<TimeSlot> listOfTimeSlots = roomMap.get(entry.room_Number);
            if (listOfTimeSlots == null || listOfTimeSlots.size() == 0)
                return false;
        }
        return true;
    }

    private String assignBookingID (){
        String randomID = "";
        boolean isUnique = true;

        do {
            // generate random 5 digit number
            Random r = new Random();
            int random = r.nextInt(99999);
            randomID = Integer.toString(random);

            // verify that booking ID is unique;
            if (bookingIDMap.containsKey(randomID))
                isUnique = false;
        }
        while(!isUnique);

        return randomID;
    }

    private void fixInDexAndSortOrder(List<TimeSlot> list){
//        Collections.sort(list);
        int i = 1;
        for (TimeSlot t : list) {
            t.setIndex(i);
            i++;
        }
    }

    public HashMapWrapper getDataMap() {
        HashMapWrapper hashMap = new HashMapWrapper(dataMap);
        return hashMap;
    }

    public String getCampusName() {
        return campusName;
    }

    public static List<DRRSServer> getServerList() {
        return serverList;
    }

    public int getCampusIndex (String campusName) {
        for (DRRSServer server : serverList){
            if (server.campusName.equalsIgnoreCase(campusName)){
                return server.campusIndex;
            }
        }
        return -1;
    }

    private void resetBookingCount(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonday = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY) ) ;
        if (now.isAfter(nextMonday)){
            studentBookingMap.clear();
        }
    }

    private void startLogger(String campusName) {
        try {
            log =  Logger.getLogger(campusName);
            log.setUseParentHandlers(false);
            String loggerFileName = "src/Logger/" + campusName + ".log";
            FileHandler fileHandler = new FileHandler(loggerFileName, true);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
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
}
