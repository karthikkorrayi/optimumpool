package com.OptimumPool.BookRide.Service;

import com.OptimumPool.BookRide.Model.Bookings;
import com.OptimumPool.BookRide.Repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IBookRideService {

    public List<Bookings> getList();

}
