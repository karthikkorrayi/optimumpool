package com.OptimumPool.OfferRide.Repository;

import com.OptimumPool.OfferRide.Model.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IBookingRepo extends MongoRepository<Bookings , Integer> {
}
