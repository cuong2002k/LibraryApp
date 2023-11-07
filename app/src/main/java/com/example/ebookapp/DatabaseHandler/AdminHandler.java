package com.example.ebookapp.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Admin;
import com.example.ebookapp.View.MainActivity;

public class AdminHandler extends DatabaseHandler {

    public AdminHandler(Context context) {
        super(context);
    }

    SQLiteDatabase db;

    public boolean checkAdmin()
    {
        String sql = "Select * from " + ADMIN_TB_NAME;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor == null;
    }

    public boolean insertData(Admin admin)
    {
        ContentValues value = new ContentValues();
        value.put(ADMIN_name, admin.getName());
        value.put(ADMIN_USERNAME, admin.getName());
        value.put(ADMIN_PASSWORD, admin.getName());
        value.put(ADMIN_ADDRESS, admin.getName());
        db = getWritableDatabase();
        long result = db.insert(ADMIN_TB_NAME,null,value);
        if(result == -1) return false;
        return true;
    }

    public boolean checkLogin(String userName, String password)
    {
        String sql = "Select * from " + ADMIN_TB_NAME + " where " + ADMIN_USERNAME + "=? and " + ADMIN_PASSWORD + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] { userName, password });
        if(cursor != null) return true;
        return false;
    }
}
