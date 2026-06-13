package com.OptimumPool.OfferRide.Configuration;

import com.OptimumPool.OfferRide.Model.Offerride;
import java.util.List;

public class OfferDTO {
    private List<Offerride> offerList;

    public OfferDTO() {}
    public OfferDTO(List<Offerride> offerList) { this.offerList = offerList; }

    public List<Offerride> getOfferList()          { return offerList; }
    public void setOfferList(List<Offerride> list) { this.offerList = list; }
}