package com.OptimumPool.OfferRide.Services;

import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Offerride;

import java.util.Map;

public interface IOfferService {
    public Offerride addOffer(Offerride of);
//    public Map<String ,String> getToken(Offerride f);
    public Offerride updateRide(Offerride or,int id) throws OfferRideNotFound;





}
