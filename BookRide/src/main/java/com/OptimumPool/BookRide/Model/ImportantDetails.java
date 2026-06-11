package com.OptimumPool.BookRide.Model;

public class ImportantDetails {
    private String carNum;
    private int avl_seats;

    private int distance;
    private int charge;

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public int getAvl_seats() {
        return avl_seats;
    }

    public void setAvl_seats(int avl_seats) {
        this.avl_seats = avl_seats;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public ImportantDetails(String carNum, int avl_seats, int distance, int charge) {
        this.carNum = carNum;
        this.avl_seats = avl_seats;
        this.distance = distance;
        this.charge = charge;
    }

    public ImportantDetails() {
    }
}
