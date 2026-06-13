package com.OptimumPool.OfferRide.Repository;

import com.OptimumPool.OfferRide.Model.Offerride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OfferRideRepo extends MongoRepository<Offerride, String> {
    List<Offerride> findByStatus(String status);

    @Query("{ 'car_info.username' : ?0 }")
    List<Offerride> findByCarOwnerUsername(String username);
}