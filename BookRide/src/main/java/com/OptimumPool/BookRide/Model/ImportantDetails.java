package com.OptimumPool.BookRide.Model;

public class ImportantDetails {
    private String carNum;
    private int avl_seats;
    private int distance;
    private int charge;

    public ImportantDetails() {}
    public ImportantDetails(String carNum, int avl_seats, int distance, int charge) {
        this.carNum = carNum; this.avl_seats = avl_seats;
        this.distance = distance; this.charge = charge;
    }

    public String getCarNum()            { return carNum; }
    public void setCarNum(String c)      { this.carNum = c; }
    public int getAvl_seats()            { return avl_seats; }
    public void setAvl_seats(int a)      { this.avl_seats = a; }
    public int getDistance()             { return distance; }
    public void setDistance(int d)       { this.distance = d; }
    public int getCharge()               { return charge; }
    public void setCharge(int c)         { this.charge = c; }
}