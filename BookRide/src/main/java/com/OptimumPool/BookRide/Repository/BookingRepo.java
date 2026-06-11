package com.OptimumPool.BookRide.Repository;

import com.OptimumPool.BookRide.Model.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends MongoRepository<Bookings,Integer> {
    @Query("{customerName:?0}")
    public Bookings findBycustomerName(String customerName);
}
