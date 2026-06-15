package com.OptimumPool.BookRide.Model;

public class CarOwner {
    private String username;
    private String name;
    private String contact;

    public CarOwner() {}

    public CarOwner(String username, String name, String contact) {
        this.username = username;
        this.name = name;
        this.contact = contact;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}