package com.OptimumPool.BookRide.Repository;

import com.OptimumPool.BookRide.Model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice,Integer> {

}
