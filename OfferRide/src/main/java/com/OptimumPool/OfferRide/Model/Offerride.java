package com.OptimumPool.OfferRide.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "rideTable")
public class Offerride {
    @Id
    private int offer_id;
    public int getOffer_id() {
        return offer_id;
    }
    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }




    public Offerride(int offer_id, CarOwner car_owner, CarInfo car_info, ArrayList<String> wayPoint, ArrayList<Integer> distance, String date, int time, int charge_per_km ) {
        this.offer_id = offer_id;
        this.car_owner = car_owner;
        this.car_info = car_info;
        this.wayPoint = wayPoint;
        this.distance = distance;
        this.date = date;
        this.time = time;
        this.charge_per_km = charge_per_km;

    }

    public Offerride() {
    }



    private CarOwner car_owner;
    private CarInfo car_info;
    private ArrayList<String> wayPoint ;
    private ArrayList<Integer> distance ;
    private String date;
    private int time;
    private int charge_per_km;

    public CarOwner getCar_owner() {
        return car_owner;
    }

    public void setCar_owner(CarOwner owner) {
        car_owner = owner;
    }

    public CarInfo getCar_info() {
        return car_info;
    }

    public void setCar_info(CarInfo car_info) {
        this.car_info = car_info;
    }

    public ArrayList<String> getWayPoint() {
        return wayPoint;
    }

    public void setWayPoint(ArrayList<String> wayPoint) {
        this.wayPoint = wayPoint;
    }

    public ArrayList<Integer> getDistance() {
        return distance;
    }

    public void setDistance(ArrayList<Integer> distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCharge_per_km() {
        return charge_per_km;
    }

    public void setCharge_per_km(int charge_per_km) {
        this.charge_per_km = charge_per_km;
    }
}
