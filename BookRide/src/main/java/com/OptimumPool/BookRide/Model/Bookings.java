package com.OptimumPool.BookRide.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "bookings_col")
public class Bookings {

    @Id
    private String booking_id;

    private Offerride offerObject;
    private String customerName;
    private int no_seat_want;
    private int distance;
    private String source;
    private String destination;
    private String bookingStatus;

    public Bookings() {
        this.booking_id    = UUID.randomUUID().toString();
        this.bookingStatus = "PENDING";
    }

    public Bookings(Offerride offerObject, String customerName,
                    int no_seat_want, int distance, String source, String destination) {
        this();
        this.offerObject  = offerObject;
        this.customerName = customerName;
        this.no_seat_want = no_seat_want;
        this.distance     = distance;
        this.source       = source;
        this.destination  = destination;
    }

    public String getBooking_id()              { return booking_id; }
    public void setBooking_id(String id)       { this.booking_id = id; }
    public Offerride getOfferObject()          { return offerObject; }
    public void setOfferObject(Offerride o)    { this.offerObject = o; }
    public String getCustomerName()            { return customerName; }
    public void setCustomerName(String n)      { this.customerName = n; }
    public int getNo_seat_want()               { return no_seat_want; }
    public void setNo_seat_want(int n)         { this.no_seat_want = n; }
    public int getDistance()                   { return distance; }
    public void setDistance(int d)             { this.distance = d; }
    public String getSource()                  { return source; }
    public void setSource(String s)            { this.source = s; }
    public String getDestination()             { return destination; }
    public void setDestination(String d)       { this.destination = d; }
    public String getBookingStatus()           { return bookingStatus; }
    public void setBookingStatus(String s)     { this.bookingStatus = s; }
}