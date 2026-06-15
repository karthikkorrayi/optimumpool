package com.OptimumPool.OfferRide.Model;

public class CustDetails {

    private String booking_id;
    private String cust_name;       // username from Auth
    private long   cust_phone;      // from Auth profile
    private int    no_of_seat_want;
    private String source;
    private String destination;
    private String date_of_journey;
    private String bookingStatus;   // PENDING / ACCEPTED / REJECTED

    public CustDetails() {}

    public CustDetails(String booking_id, String cust_name, long cust_phone,
                       int no_of_seat_want, String source, String destination,
                       String date_of_journey, String bookingStatus) {
        this.booking_id      = booking_id;
        this.cust_name       = cust_name;
        this.cust_phone      = cust_phone;
        this.no_of_seat_want = no_of_seat_want;
        this.source          = source;
        this.destination     = destination;
        this.date_of_journey = date_of_journey;
        this.bookingStatus   = bookingStatus;
    }

    public String getBooking_id()                      { return booking_id; }
    public void setBooking_id(String id)               { this.booking_id = id; }
    public String getCust_name()                       { return cust_name; }
    public void setCust_name(String n)                 { this.cust_name = n; }
    public long getCust_phone()                        { return cust_phone; }
    public void setCust_phone(long p)                  { this.cust_phone = p; }
    public int getNo_of_seat_want()                    { return no_of_seat_want; }
    public void setNo_of_seat_want(int n)              { this.no_of_seat_want = n; }
    public String getSource()                          { return source; }
    public void setSource(String s)                    { this.source = s; }
    public String getDestination()                     { return destination; }
    public void setDestination(String d)               { this.destination = d; }
    public String getDate_of_journey()                 { return date_of_journey; }
    public void setDate_of_journey(String d)           { this.date_of_journey = d; }
    public String getBookingStatus()                   { return bookingStatus; }
    public void setBookingStatus(String s)             { this.bookingStatus = s; }
}