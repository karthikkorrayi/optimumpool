package com.OptimumPool.BookRide.Model;


public class CarOwner {
    private int ownerId;
    private String name ;

    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarOwner(int ownerId, String name ,String contact) {
        this.ownerId = ownerId;
        this.name = name;
        this.contact=contact;
    }

    public CarOwner() {
    }
}
