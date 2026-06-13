package com.OptimumPool.BookRide.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoices_col")
public class Invoice {
    @Id
    private String invoiceId;
    private Bookings booking_obj;
    private int bill_generated;
    private String status; // e.g., "PENDING", "PAID"

    public Invoice() {
    }

    public Invoice(Bookings booking_obj, int bill_generated) {
        this.booking_obj = booking_obj;
        this.bill_generated = bill_generated;
        this.status = "PENDING";
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}