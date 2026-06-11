package com.OptimumPool.OfferRide.Configuration;

import com.OptimumPool.OfferRide.Model.Offerride;
import org.json.simple.JSONObject;

import java.util.List;

public class OfferDTO {
    private JSONObject jsonObject;
   private List<Offerride> offerList ;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<Offerride> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offerride> offerList) {
        this.offerList = offerList;
    }

    public OfferDTO(JSONObject jsonObject, List<Offerride> offerList) {
        this.jsonObject = jsonObject;
        this.offerList = offerList;
    }

    public OfferDTO() {
        super();
    }
}
