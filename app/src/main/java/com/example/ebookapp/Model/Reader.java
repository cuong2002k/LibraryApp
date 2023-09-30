package com.example.ebookapp.Model;

import java.io.Serializable;

public class Reader implements Serializable {
    private int readerId;
    private String name;
    private String address;
    private String phone;
    private String city;

    public Reader(){};

    public Reader(String name, String address, String phone, String city) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.city = city;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
