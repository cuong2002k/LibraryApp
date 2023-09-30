package com.example.ebookapp.Model;

import java.io.Serializable;

public class Author implements Serializable {
    int id;
    String name;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author() {
    }

    public Author(String name) {
        this.name = name;
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
}
