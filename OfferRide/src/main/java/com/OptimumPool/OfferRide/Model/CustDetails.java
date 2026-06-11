package com.OptimumPool.OfferRide.Model;

public class CustDetails {

    private String cust_name;
    private int no_of_seat_want;

    private String source;
    private String destination;
    private  String date_of_journey ;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate_of_journey() {
        return date_of_journey;
    }

    public void setDate_of_journey(String date_of_journey) {
        this.date_of_journey = date_of_journey;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public int getNo_of_seat_want() {
        return no_of_seat_want;
    }

    public void setNo_of_seat_want(int no_of_seat_want) {
        this.no_of_seat_want = no_of_seat_want;
    }

    public CustDetails(String cust_name, int no_of_seat_want , String source , String dest , String date ) {
        this.cust_name = cust_name;
        this.no_of_seat_want = no_of_seat_want;
        this.source = source;
        this.destination = dest;
        this.date_of_journey=date;
    }

    public CustDetails() {
    }
}
