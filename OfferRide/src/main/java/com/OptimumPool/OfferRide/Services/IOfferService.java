package com.OptimumPool.OfferRide.Services;

import com.OptimumPool.OfferRide.Configuration.BookingDTO;
import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Bookings;
import com.OptimumPool.OfferRide.Model.CustDetails;
import com.OptimumPool.OfferRide.Model.Offerride;

import java.util.List;

public interface IOfferService {
    Offerride addOffer(Offerride offer);
    Offerride updateRide(Offerride offer, String id) throws OfferRideNotFound;
    List<Offerride> getRide();
    List<CustDetails> getAllBooking();
    void receiveBookingFromRabbit(BookingDTO bookingData);
}