package Business;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.time.LocalTime;

@WebService
public class TimeSlot {
//public class TimeSlot implements Comparable<TimeSlot>{
    private LocalTime beginTime;
    private LocalTime endTime;
    private String bookedByID;
    private String bookingID;
    private int index;

    public TimeSlot() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TimeSlot(LocalTime beginTime, LocalTime endTime, int index) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.index = index;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getBookedByID() {
        return bookedByID;
    }

    public void setBookedByID(String bookedByID) {
        this.bookedByID = bookedByID;
    }


//    @Override
//    public int compareTo(TimeSlot o) {
//        return this.beginTime.compareTo(o.beginTime);
//    }

}


