package com.OptimumPool.OfferRide.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Document(collection = "rideTable")
public class Offerride {

    @Id
    private String offer_id;   // FIXED: UUID string instead of random int

    private CarOwner car_owner;
    private CarInfo car_info;
    private ArrayList<String> wayPoint;
    private ArrayList<Integer> distance;
    private String date;
    private int time;
    private int charge_per_km;
    private String status;     // NEW: "ACTIVE" or "COMPLETED"

    public Offerride() {
        this.offer_id = UUID.randomUUID().toString();
        this.status = "ACTIVE";
    }

    public Offerride(CarOwner car_owner, CarInfo car_info, ArrayList<String> wayPoint,
                     ArrayList<Integer> distance, String date, int time, int charge_per_km) {
        this();
        this.car_owner   = car_owner;
        this.car_info    = car_info;
        this.wayPoint    = wayPoint;
        this.distance    = distance;
        this.date        = date;
        this.time        = time;
        this.charge_per_km = charge_per_km;
    }

    public String getOffer_id()                { return offer_id; }
    public void setOffer_id(String id)         { this.offer_id = id; }
    public CarOwner getCar_owner()             { return car_owner; }
    public void setCar_owner(CarOwner o)       { this.car_owner = o; }
    public CarInfo getCar_info()               { return car_info; }
    public void setCar_info(CarInfo c)         { this.car_info = c; }
    public ArrayList<String> getWayPoint()     { return wayPoint; }
    public void setWayPoint(ArrayList<String> w) { this.wayPoint = w; }
    public ArrayList<Integer> getDistance()    { return distance; }
    public void setDistance(ArrayList<Integer> d) { this.distance = d; }
    public String getDate()                    { return date; }
    public void setDate(String date)           { this.date = date; }
    public int getTime()                       { return time; }
    public void setTime(int time)              { this.time = time; }
    public int getCharge_per_km()              { return charge_per_km; }
    public void setCharge_per_km(int c)        { this.charge_per_km = c; }
    public String getStatus()                  { return status; }
    public void setStatus(String status)       { this.status = status; }
}