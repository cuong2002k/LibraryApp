package com.example.ebookapp.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ebookapp.R;

import java.io.Serializable;

public class Book implements Serializable {
    int bookId;
    String title;
    int author;
    String year;
    int category;
    Bitmap image;

    public Book() {
    }

    public Book(String title, int author, String year, int category, Bitmap image) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.image = image;
    }

    public Book(int bookId, String title, int author, String year, int category, Bitmap image) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.image = image;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
