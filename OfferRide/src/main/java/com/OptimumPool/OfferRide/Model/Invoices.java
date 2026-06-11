package com.OptimumPool.OfferRide.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoice")
public class Invoices {
    @Id
    private int id;
    private Bookings booking_obj;
    private int bill_generated;

    public Bookings getBooking_obj() {
        return booking_obj;
    }

    public void setBooking_obj(Bookings booking_obj) {
        this.booking_obj = booking_obj;
    }

    public int getBill_generated() {
        return bill_generated;
    }

    public void setBill_generated(int bill_generated) {
        this.bill_generated = bill_generated;
    }

    public Invoices(Bookings booking_obj, int bill_generated) {
        this.booking_obj = booking_obj;
        this.bill_generated = bill_generated;
    }

    public Invoices() {
    }
}
