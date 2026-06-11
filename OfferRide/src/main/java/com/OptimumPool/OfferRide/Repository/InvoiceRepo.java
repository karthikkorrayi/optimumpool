package com.OptimumPool.OfferRide.Repository;

import com.OptimumPool.OfferRide.Model.Invoices;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepo extends MongoRepository<Invoices , Integer> {
}
