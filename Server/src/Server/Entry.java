package Server;

import java.util.List;

public class Entry {
    protected String room_Number;
    protected String date;
    protected String beginTime;
    protected String endTime;
    protected String studentID;
    protected int timeslot;
    protected String campusName;
    protected List<Integer> listOfTimeSlots;

    public Entry(String date) {
        this.date = date;
    }

    public Entry(String room_Number, String date, String beginTime, String endTime) {
        this.room_Number = room_Number;
        this.date = date;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Entry(String room_Number, String date, List<Integer> listOfTimeSlots) {
        this.room_Number = room_Number;
        this.date = date;
        this.listOfTimeSlots = listOfTimeSlots;
    }

    public Entry(String campusName, String room_Number, String date, int timeslot, String studentID) {
        this.campusName = campusName;
        this.room_Number = room_Number;
        this.date = date;
        this.timeslot = timeslot;
        this.studentID = studentID;
    }

    public boolean isEntryValid (){
        return !room_Number.isEmpty() && !date.isEmpty() && !beginTime.isEmpty() && !endTime.isEmpty();
    }

    public boolean isEntryValidWithList (){
        return !room_Number.isEmpty() && !date.isEmpty() && listOfTimeSlots.size()!=0;
    }

    public boolean isEntryValidWithStudentID (){
        return !campusName.isEmpty() && !studentID.isEmpty()&& !room_Number.isEmpty() && !date.isEmpty();
    }
}
