package com.OptimumPool.OfferRide.Configuration;


import com.OptimumPool.OfferRide.Model.Bookings;
import org.json.simple.JSONObject;

import java.util.List;

public class BookingDTO {
    private JSONObject jsonObject;


    public JSONObject getJsonObject() {
        return jsonObject;
    }







    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }


    public BookingDTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

    }

    public BookingDTO() {
        super();
    }
}
