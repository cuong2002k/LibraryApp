package com.example.ebookapp.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Category;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class CategoryHandler {
    DatabaseHandler dbhandler;

    public CategoryHandler(Context context)
    {
        dbhandler = new DatabaseHandler(context);
    }

    public ArrayList<Category> getAll()
    {
        try
        {
            ArrayList<Category> lstCategory = new ArrayList<>();
            String sql = "Select * from " + DatabaseHandler.CATEGORY_TB_NAME;
            SQLiteDatabase db = dbhandler.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Category category = new Category(id, name);
                lstCategory.add(category);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return lstCategory;
        }
        catch (Exception exception)
        {
            return null;
        }
    }

    public boolean insertData(Category category)
    {
        try
        {
            ContentValues value = new ContentValues();
            value.put(DatabaseHandler.CATEGORY_name, category.getName());
            SQLiteDatabase db = dbhandler.getWritableDatabase();
            db.insert(DatabaseHandler.CATEGORY_TB_NAME, null, value);
            db.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public boolean updateData(Category category)
    {
        try
        {
            ContentValues value = new ContentValues();
            value.put(DatabaseHandler.CATEGORY_name, category.getName());
            SQLiteDatabase db = dbhandler.getWritableDatabase();
            db.update(DatabaseHandler.CATEGORY_TB_NAME,
                    value,
                    DatabaseHandler.CATEGORY_ID + "=?",
                    new String[] { category.getId() + "" });
            db.close();
            return  true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public boolean deleteData(int id)
    {
        try
        {
            SQLiteDatabase db = dbhandler.getWritableDatabase();
            db = dbhandler.getWritableDatabase();
            String sql = "DELETE FROM " + dbhandler.CATEGORY_TB_NAME + " WHERE " + dbhandler.CATEGORY_ID + " = ?";
            db.execSQL(sql,new String[]{id+""});
            db.close();
            return  true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public Category getCategoryWithID(int id)
    {
        try
        {
            ArrayList<Category> lstCategory = new ArrayList<>();
            String sql = "Select * from " + DatabaseHandler.CATEGORY_TB_NAME + " where " + DatabaseHandler.CATEGORY_ID + " =?";
            SQLiteDatabase db = dbhandler.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[]{id+""});
            cursor.moveToFirst();
            int ids = cursor.getInt(0);
            String name = cursor.getString(1);
            Category category = new Category(ids, name);
            db.close();
            return category;
        }
        catch (Exception exception)
        {
            return null;
        }
    }
}
