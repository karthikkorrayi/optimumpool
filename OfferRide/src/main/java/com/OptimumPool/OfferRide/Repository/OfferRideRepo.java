package com.OptimumPool.OfferRide.Repository;

import com.OptimumPool.OfferRide.Model.Offerride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRideRepo extends MongoRepository<Offerride , Integer> {

}
