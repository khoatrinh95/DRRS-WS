
package server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the server package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateRoomResponse_QNAME = new QName("http://Server/", "createRoomResponse");
    private final static QName _GetCampusNameResponse_QNAME = new QName("http://Server/", "getCampusNameResponse");
    private final static QName _ChangeReservation_QNAME = new QName("http://Server/", "changeReservation");
    private final static QName _GetDataMapResponse_QNAME = new QName("http://Server/", "getDataMapResponse");
    private final static QName _OverLimit_QNAME = new QName("http://Server/", "overLimit");
    private final static QName _GetAvailableTimeSlot_QNAME = new QName("http://Server/", "getAvailableTimeSlot");
    private final static QName _GetAvailableTimeSlotResponse_QNAME = new QName("http://Server/", "getAvailableTimeSlotResponse");
    private final static QName _GetCampusIndexResponse_QNAME = new QName("http://Server/", "getCampusIndexResponse");
    private final static QName _GetCampusName_QNAME = new QName("http://Server/", "getCampusName");
    private final static QName _BookRoom_QNAME = new QName("http://Server/", "bookRoom");
    private final static QName _ChangeReservationResponse_QNAME = new QName("http://Server/", "changeReservationResponse");
    private final static QName _CancelBooking_QNAME = new QName("http://Server/", "cancelBooking");
    private final static QName _OverLimitResponse_QNAME = new QName("http://Server/", "overLimitResponse");
    private final static QName _GetTimeSlots_QNAME = new QName("http://Server/", "getTimeSlots");
    private final static QName _BookRoomResponse_QNAME = new QName("http://Server/", "bookRoomResponse");
    private final static QName _DeleteRoomResponse_QNAME = new QName("http://Server/", "deleteRoomResponse");
    private final static QName _GetDataMap_QNAME = new QName("http://Server/", "getDataMap");
    private final static QName _GetCampusIndex_QNAME = new QName("http://Server/", "getCampusIndex");
    private final static QName _CreateRoom_QNAME = new QName("http://Server/", "createRoom");
    private final static QName _DeleteRoom_QNAME = new QName("http://Server/", "deleteRoom");
    private final static QName _CancelBookingResponse_QNAME = new QName("http://Server/", "cancelBookingResponse");
    private final static QName _GetTimeSlotsResponse_QNAME = new QName("http://Server/", "getTimeSlotsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BookRoomResponse }
     * 
     */
    public BookRoomResponse createBookRoomResponse() {
        return new BookRoomResponse();
    }

    /**
     * Create an instance of {@link GetTimeSlots }
     * 
     */
    public GetTimeSlots createGetTimeSlots() {
        return new GetTimeSlots();
    }

    /**
     * Create an instance of {@link CancelBooking }
     * 
     */
    public CancelBooking createCancelBooking() {
        return new CancelBooking();
    }

    /**
     * Create an instance of {@link OverLimitResponse }
     * 
     */
    public OverLimitResponse createOverLimitResponse() {
        return new OverLimitResponse();
    }

    /**
     * Create an instance of {@link CancelBookingResponse }
     * 
     */
    public CancelBookingResponse createCancelBookingResponse() {
        return new CancelBookingResponse();
    }

    /**
     * Create an instance of {@link GetTimeSlotsResponse }
     * 
     */
    public GetTimeSlotsResponse createGetTimeSlotsResponse() {
        return new GetTimeSlotsResponse();
    }

    /**
     * Create an instance of {@link DeleteRoom }
     * 
     */
    public DeleteRoom createDeleteRoom() {
        return new DeleteRoom();
    }

    /**
     * Create an instance of {@link CreateRoom }
     * 
     */
    public CreateRoom createCreateRoom() {
        return new CreateRoom();
    }

    /**
     * Create an instance of {@link GetCampusIndex }
     * 
     */
    public GetCampusIndex createGetCampusIndex() {
        return new GetCampusIndex();
    }

    /**
     * Create an instance of {@link DeleteRoomResponse }
     * 
     */
    public DeleteRoomResponse createDeleteRoomResponse() {
        return new DeleteRoomResponse();
    }

    /**
     * Create an instance of {@link GetDataMap }
     * 
     */
    public GetDataMap createGetDataMap() {
        return new GetDataMap();
    }

    /**
     * Create an instance of {@link GetCampusIndexResponse }
     * 
     */
    public GetCampusIndexResponse createGetCampusIndexResponse() {
        return new GetCampusIndexResponse();
    }

    /**
     * Create an instance of {@link GetCampusName }
     * 
     */
    public GetCampusName createGetCampusName() {
        return new GetCampusName();
    }

    /**
     * Create an instance of {@link GetAvailableTimeSlot }
     * 
     */
    public GetAvailableTimeSlot createGetAvailableTimeSlot() {
        return new GetAvailableTimeSlot();
    }

    /**
     * Create an instance of {@link GetAvailableTimeSlotResponse }
     * 
     */
    public GetAvailableTimeSlotResponse createGetAvailableTimeSlotResponse() {
        return new GetAvailableTimeSlotResponse();
    }

    /**
     * Create an instance of {@link OverLimit }
     * 
     */
    public OverLimit createOverLimit() {
        return new OverLimit();
    }

    /**
     * Create an instance of {@link ChangeReservation }
     * 
     */
    public ChangeReservation createChangeReservation() {
        return new ChangeReservation();
    }

    /**
     * Create an instance of {@link GetDataMapResponse }
     * 
     */
    public GetDataMapResponse createGetDataMapResponse() {
        return new GetDataMapResponse();
    }

    /**
     * Create an instance of {@link GetCampusNameResponse }
     * 
     */
    public GetCampusNameResponse createGetCampusNameResponse() {
        return new GetCampusNameResponse();
    }

    /**
     * Create an instance of {@link CreateRoomResponse }
     * 
     */
    public CreateRoomResponse createCreateRoomResponse() {
        return new CreateRoomResponse();
    }

    /**
     * Create an instance of {@link ChangeReservationResponse }
     * 
     */
    public ChangeReservationResponse createChangeReservationResponse() {
        return new ChangeReservationResponse();
    }

    /**
     * Create an instance of {@link BookRoom }
     * 
     */
    public BookRoom createBookRoom() {
        return new BookRoom();
    }

    /**
     * Create an instance of {@link HashMapWrapper }
     * 
     */
    public HashMapWrapper createHashMapWrapper() {
        return new HashMapWrapper();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRoomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "createRoomResponse")
    public JAXBElement<CreateRoomResponse> createCreateRoomResponse(CreateRoomResponse value) {
        return new JAXBElement<CreateRoomResponse>(_CreateRoomResponse_QNAME, CreateRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampusNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getCampusNameResponse")
    public JAXBElement<GetCampusNameResponse> createGetCampusNameResponse(GetCampusNameResponse value) {
        return new JAXBElement<GetCampusNameResponse>(_GetCampusNameResponse_QNAME, GetCampusNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "changeReservation")
    public JAXBElement<ChangeReservation> createChangeReservation(ChangeReservation value) {
        return new JAXBElement<ChangeReservation>(_ChangeReservation_QNAME, ChangeReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getDataMapResponse")
    public JAXBElement<GetDataMapResponse> createGetDataMapResponse(GetDataMapResponse value) {
        return new JAXBElement<GetDataMapResponse>(_GetDataMapResponse_QNAME, GetDataMapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OverLimit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "overLimit")
    public JAXBElement<OverLimit> createOverLimit(OverLimit value) {
        return new JAXBElement<OverLimit>(_OverLimit_QNAME, OverLimit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableTimeSlot }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getAvailableTimeSlot")
    public JAXBElement<GetAvailableTimeSlot> createGetAvailableTimeSlot(GetAvailableTimeSlot value) {
        return new JAXBElement<GetAvailableTimeSlot>(_GetAvailableTimeSlot_QNAME, GetAvailableTimeSlot.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableTimeSlotResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getAvailableTimeSlotResponse")
    public JAXBElement<GetAvailableTimeSlotResponse> createGetAvailableTimeSlotResponse(GetAvailableTimeSlotResponse value) {
        return new JAXBElement<GetAvailableTimeSlotResponse>(_GetAvailableTimeSlotResponse_QNAME, GetAvailableTimeSlotResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampusIndexResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getCampusIndexResponse")
    public JAXBElement<GetCampusIndexResponse> createGetCampusIndexResponse(GetCampusIndexResponse value) {
        return new JAXBElement<GetCampusIndexResponse>(_GetCampusIndexResponse_QNAME, GetCampusIndexResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampusName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getCampusName")
    public JAXBElement<GetCampusName> createGetCampusName(GetCampusName value) {
        return new JAXBElement<GetCampusName>(_GetCampusName_QNAME, GetCampusName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookRoom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "bookRoom")
    public JAXBElement<BookRoom> createBookRoom(BookRoom value) {
        return new JAXBElement<BookRoom>(_BookRoom_QNAME, BookRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "changeReservationResponse")
    public JAXBElement<ChangeReservationResponse> createChangeReservationResponse(ChangeReservationResponse value) {
        return new JAXBElement<ChangeReservationResponse>(_ChangeReservationResponse_QNAME, ChangeReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBooking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "cancelBooking")
    public JAXBElement<CancelBooking> createCancelBooking(CancelBooking value) {
        return new JAXBElement<CancelBooking>(_CancelBooking_QNAME, CancelBooking.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OverLimitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "overLimitResponse")
    public JAXBElement<OverLimitResponse> createOverLimitResponse(OverLimitResponse value) {
        return new JAXBElement<OverLimitResponse>(_OverLimitResponse_QNAME, OverLimitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeSlots }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getTimeSlots")
    public JAXBElement<GetTimeSlots> createGetTimeSlots(GetTimeSlots value) {
        return new JAXBElement<GetTimeSlots>(_GetTimeSlots_QNAME, GetTimeSlots.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookRoomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "bookRoomResponse")
    public JAXBElement<BookRoomResponse> createBookRoomResponse(BookRoomResponse value) {
        return new JAXBElement<BookRoomResponse>(_BookRoomResponse_QNAME, BookRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRoomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "deleteRoomResponse")
    public JAXBElement<DeleteRoomResponse> createDeleteRoomResponse(DeleteRoomResponse value) {
        return new JAXBElement<DeleteRoomResponse>(_DeleteRoomResponse_QNAME, DeleteRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getDataMap")
    public JAXBElement<GetDataMap> createGetDataMap(GetDataMap value) {
        return new JAXBElement<GetDataMap>(_GetDataMap_QNAME, GetDataMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampusIndex }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getCampusIndex")
    public JAXBElement<GetCampusIndex> createGetCampusIndex(GetCampusIndex value) {
        return new JAXBElement<GetCampusIndex>(_GetCampusIndex_QNAME, GetCampusIndex.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRoom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "createRoom")
    public JAXBElement<CreateRoom> createCreateRoom(CreateRoom value) {
        return new JAXBElement<CreateRoom>(_CreateRoom_QNAME, CreateRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRoom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "deleteRoom")
    public JAXBElement<DeleteRoom> createDeleteRoom(DeleteRoom value) {
        return new JAXBElement<DeleteRoom>(_DeleteRoom_QNAME, DeleteRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBookingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "cancelBookingResponse")
    public JAXBElement<CancelBookingResponse> createCancelBookingResponse(CancelBookingResponse value) {
        return new JAXBElement<CancelBookingResponse>(_CancelBookingResponse_QNAME, CancelBookingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeSlotsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "getTimeSlotsResponse")
    public JAXBElement<GetTimeSlotsResponse> createGetTimeSlotsResponse(GetTimeSlotsResponse value) {
        return new JAXBElement<GetTimeSlotsResponse>(_GetTimeSlotsResponse_QNAME, GetTimeSlotsResponse.class, null, value);
    }

}
