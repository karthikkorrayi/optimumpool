package com.OptimumPool.OfferRide.Configuration;

import com.OptimumPool.OfferRide.Model.Bookings;
import java.util.List;

public class BookingDTO {
    private List<Bookings> bookList;

    public BookingDTO() {}
    public BookingDTO(List<Bookings> bookList) { this.bookList = bookList; }

    public List<Bookings> getBookList()            { return bookList; }
    public void setBookList(List<Bookings> list)   { this.bookList = list; }
}