package com.example.ebookapp.Model;

import java.io.Serializable;

public class Admin implements Serializable {
    int id;
    String name;
    String username;
    String password;
    String address;

    public Admin() {
    }

    public Admin(String name, String username, String password, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public Admin(int id, String name, String username, String password, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
