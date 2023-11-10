package com.example.ebookapp.DatabaseHandler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class BookHandler{
    DatabaseHandler hd;
    public BookHandler(Context context)
    {
        this.hd = new DatabaseHandler(context);
    }

    public ArrayList<Book> getAllBook()
    {
        try{
            ArrayList<Book> lstBook = new ArrayList<>();
            String sql = "Select * from " + DatabaseHandler.BOOK_TB_NAME;
            SQLiteDatabase db = hd.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                int bookId = cursor.getInt(0);
                String title = cursor.getString(1);
                int author = cursor.getInt(2);
                String year = cursor.getString(3);
                int category = cursor.getInt(4);
                byte[] image = cursor.getBlob(5);
                Book book = new Book(bookId, title, author,year,category, getImage(image));
                lstBook.add(book);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return lstBook;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
    public boolean insertData(Book book)
    {
        try {
            SQLiteDatabase db = hd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.BOOK_TITLE, book.getTitle());
            values.put(DatabaseHandler.BOOK_AUTHOR_ID, book.getAuthor());
            values.put(DatabaseHandler.BOOK_YEAR, book.getYear());
            values.put(DatabaseHandler.BOOK_CATEGORY_ID, book.getCategory());
            values.put(DatabaseHandler.BOOK_IMAGE, getBitmapAsByteArray(book.getImage()));
            db.insert(DatabaseHandler.BOOK_TB_NAME,
                    null,
                    values);
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    public boolean updateData(Book book)
    {
        try {
            SQLiteDatabase db = hd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.BOOK_TITLE, book.getTitle());
            values.put(DatabaseHandler.BOOK_AUTHOR_ID, book.getAuthor());
            values.put(DatabaseHandler.BOOK_YEAR, book.getYear());
            values.put(DatabaseHandler.BOOK_CATEGORY_ID, book.getCategory());
            values.put(DatabaseHandler.BOOK_IMAGE, getBitmapAsByteArray(book.getImage()));
            db.update(DatabaseHandler.BOOK_TB_NAME,
                     values,
                     DatabaseHandler.BOOK_ID + "=?",
                    new String[]{book.getBookId() + ""}
                    );
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    public boolean deleteData(int id)
    {
        try {
            SQLiteDatabase db = hd.getWritableDatabase();
            db.delete(DatabaseHandler.BOOK_TB_NAME,
                    DatabaseHandler.BOOK_ID + "=?",
                    new String[]{id + ""}
                    );
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public Bitmap getImage( byte[] imgByte){
        if (imgByte != null){
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        return null;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Book getBookWithID(int id)
    {
        try{

            String sql = "Select * from " + DatabaseHandler.BOOK_TB_NAME + " where " + DatabaseHandler.BOOK_ID + "=?";
            SQLiteDatabase db = hd.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[]{id+""});
            cursor.moveToFirst();
            int bookId = cursor.getInt(0);
            String title = cursor.getString(1);
            int author = cursor.getInt(2);
            String year = cursor.getString(3);
            int category = cursor.getInt(4);
            byte[] image = cursor.getBlob(5);
            Book book = new Book(bookId, title, author,year,category, getImage(image));
            cursor.close();
            db.close();
            return book;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

}
