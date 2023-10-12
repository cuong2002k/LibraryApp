package com.example.ebookapp.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Reader;

import java.util.ArrayList;

public class ReaderHandler {
    DatabaseHandler hd;
    public ReaderHandler(Context context)
    {
        hd = new DatabaseHandler(context);
    }

    public ArrayList<Reader> getAllReader()
    {
        try {
            ArrayList<Reader> lstReader = new ArrayList<>();
            String sql = "Select * from " + DatabaseHandler.READER_TB_NAME;
            SQLiteDatabase db = hd.getReadableDatabase();
            Cursor data = db.rawQuery(sql, null);
            data.moveToFirst();
            while(!data.isAfterLast())
            {
                int id = data.getInt(0);
                String name = data.getString(1);
                String address = data.getString(2);
                String phone = data.getString(3);
                String city = data.getString(4);
                Reader reader = new Reader(id,name,address,phone,city);
                lstReader.add(reader);
                data.moveToNext();
            }
            db.close();
            return  lstReader;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public boolean insertData(Reader reader)
    {
        try {
            SQLiteDatabase db = hd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.READER_NAME, reader.getName());
            values.put(DatabaseHandler.READER_ADDRESS, reader.getAddress());
            values.put(DatabaseHandler.READER_PHONE, reader.getPhone());
            values.put(DatabaseHandler.READER_CITY, reader.getCity());
            db.insert(DatabaseHandler.READER_TB_NAME,null, values);
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }
    public boolean updateData(Reader reader)
    {
        try {
            SQLiteDatabase db = hd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.READER_NAME, reader.getName());
            values.put(DatabaseHandler.READER_ADDRESS, reader.getAddress());
            values.put(DatabaseHandler.READER_PHONE, reader.getPhone());
            values.put(DatabaseHandler.READER_CITY, reader.getCity());
            db.update(DatabaseHandler.READER_TB_NAME,
                    values,
                    DatabaseHandler.READER_ID + "=?",
                    new String[] {reader.getReaderId() + ""}
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
        try{

            SQLiteDatabase db = hd.getWritableDatabase();
            db.delete(DatabaseHandler.READER_TB_NAME, DatabaseHandler.READER_ID+"=?",
                    new String[]{id+""});
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public Reader getReaderWithID(int id)
    {
        try {
            String sql = "Select * from " + DatabaseHandler.READER_TB_NAME + "" +
                    " where " + DatabaseHandler.READER_ID + " =?";
            SQLiteDatabase db = hd.getReadableDatabase();
            Cursor data = db.rawQuery(sql, new String[]{id+""});
            data.moveToFirst();

                int ids = data.getInt(0);
                String name = data.getString(1);
                String address = data.getString(2);
                String phone = data.getString(3);
                String city = data.getString(4);
                Reader reader = new Reader(id,name,address,phone,city);

                db.close();
            return  reader;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

}
