package com.example.ebookapp.DatabaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Author;

import java.util.ArrayList;

public class AuthorHandler {
    DatabaseHandler database;
    SQLiteDatabase db;
    private String TAG;
    private String sql;
    public AuthorHandler(Context context)
    {
        database = new DatabaseHandler(context);
    }

    public ArrayList<Author> getall()
    {
            ArrayList<Author> lstAuthor = new ArrayList<>();
            sql = "Select * from " + DatabaseHandler.AUTHOR_TB_NAME;
            db = database.getReadableDatabase();
            Cursor data = db.rawQuery(sql, null);
            data.moveToFirst();
            while(!data.isAfterLast())
            {
                int id = data.getInt(0);
                String name =  data.getString(1);
                Author author = new Author(
                  id , name
                );
                lstAuthor.add(author);
                data.moveToNext();
            }
            data.close();
            return lstAuthor;
    }
    public boolean insertData(Author author)
    {
        TAG = "Insert author";
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(database.AUTHOR_NAME, author.getName());
            db = database.getWritableDatabase();
            db.insert(database.AUTHOR_TB_NAME, null, contentValues);
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
            return false;
        }
    }
    public boolean updateData(Author author)
    {
        TAG = "Update author";
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(database.AUTHOR_NAME, author.getName());
            String where = "where " + database.AUTHOR_ID + "= ?";
            db = database.getWritableDatabase();
            db.update(database.AUTHOR_TB_NAME,
                    contentValues  ,
                    "where " + database.AUTHOR_ID + "= ?",
                    new String[]{author.getId()+""});
            return true;
        }
        catch (Exception ex)
        {
            Log.i(TAG, ex.getMessage());
            return false;
        }
    }
    public boolean deleteData(int id)
    {
        TAG = "Delete author";
        try
        {

            db = database.getWritableDatabase();
            db.delete(database.AUTHOR_TB_NAME,
                    "where " + database.AUTHOR_ID + "= ?",
                    new String[]{id+""});
            return true;
        }
        catch (Exception ex)
        {
            Log.i(TAG, ex.getMessage());
            return false;
        }
    }




}
