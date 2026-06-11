package com.OptimumPool.OfferRide.Model;

public class CarInfo {
    private String carNum;
    private String model ;
    private int no_of_seats;
    private int avl_seats;

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNo_of_seats() {
        return no_of_seats;
    }

    public void setNo_of_seats(int no_of_seats) {
        this.no_of_seats = no_of_seats;
    }

    public int getAvl_seats() {
        return avl_seats;
    }

    public void setAvl_seats(int avl_seats) {
        this.avl_seats = avl_seats;
    }

    public CarInfo(String carNum, String model, int no_of_seats, int avl_seats) {
        this.carNum = carNum;
        this.model = model;
        this.no_of_seats = no_of_seats;
        this.avl_seats = avl_seats;
    }

    public CarInfo() {
    }


}
