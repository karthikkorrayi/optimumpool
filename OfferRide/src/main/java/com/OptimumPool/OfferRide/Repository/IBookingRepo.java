package com.OptimumPool.OfferRide.Repository;

import com.OptimumPool.OfferRide.Model.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface IBookingRepo extends MongoRepository<Bookings, String> {

    @Query("{ 'offerObject.id' : ?0 }")
    List<Bookings> findByOfferObjectOffer_id(String offerId);
}