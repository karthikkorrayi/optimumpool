package com.OptimumPool.BookRide.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoice")
public class Invoice {
    @Id
    private int invoice_id;
    private Bookings booking_obj;
    private int bill_generated;

    public Invoice(int invoice_id, Bookings booking_obj, int bill_generated) {
        this.invoice_id = invoice_id;
        this.booking_obj = booking_obj;
        this.bill_generated = bill_generated;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

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


    public Invoice() {
    }
}
