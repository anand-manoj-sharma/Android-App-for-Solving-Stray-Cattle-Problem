package com.example.stech20;

public class Type {

    private String contact;
    private String Email;
    private String Name;
    private String password;
    private String type;
    private String address;
    private double latitude;
    private double longitude;
    private double ulatitude;
    private double ulongitude;
    private long status;

    public Type()
    {

    }

    public Type(String name, String contact, String email, String password, String type,int status) {
        this.contact = contact;
        this.Email = email;
        this.Name = name;
        this.password=password;
        this.type = type;
        this.status=status;
    }


    public Type(String name, String contact, String email, String password, String type, String address, double latitude, double longitude, double ulatitude, double ulongitude, long status) {
        this.contact = contact;
        this.Email = email;
        this.Name = name;
        this.password=password;
        this.type = type;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
        this.ulatitude=ulatitude;
        this.ulongitude=ulongitude;
        this.status=status;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getUlatitude() {
        return ulatitude;
    }

    public void setUlatitude(double ulatitude) {
        this.ulatitude = ulatitude;
    }

    public double getUlongitude() {
        return ulongitude;
    }

    public void setUlongitude(double ulongitude) {
        this.ulongitude = ulongitude;
    }

    public String getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setAddress(String pass) {
        this.password = pass;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

}
