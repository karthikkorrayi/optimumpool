package com.OptimumPool.BookRide.Repository;

import com.OptimumPool.BookRide.Model.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookingRepo extends MongoRepository<Bookings, String> {

    Bookings findByCustomerName(String customerName);

    List<Bookings> findAllByCustomerName(String customerName);

    @Query("{ 'offerObject.offer_id' : ?0 }")
    List<Bookings> findByOfferObjectOffer_id(String offerId);

    List<Bookings> findByBookingStatus(String status);
}