package com.OptimumPool.BookRide.Repository;

import com.OptimumPool.BookRide.Model.Offerride;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRideRepository extends MongoRepository<Offerride,Integer> {

}
