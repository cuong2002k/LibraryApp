package com.example.ebookapp.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Admin;
import com.example.ebookapp.View.MainActivity;

public class AdminHandler{
    DatabaseHandler hd;
    public AdminHandler(Context context) {
            hd = new DatabaseHandler(context);
    }
        public int checkAdmin()
        {
            String sql = "Select COUNT(*) from " + DatabaseHandler.ADMIN_TB_NAME;
            SQLiteDatabase db;
            db = hd.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            int count = 0;
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            db.close();
            return count;
        }

        public boolean insertData(Admin admin)
        {
            SQLiteDatabase db;
            ContentValues value = new ContentValues();
            value.put(DatabaseHandler.ADMIN_name, admin.getName());
            value.put(DatabaseHandler.ADMIN_USERNAME, admin.getUsername());
            value.put(DatabaseHandler.ADMIN_PASSWORD, admin.getPassword());
            value.put(DatabaseHandler.ADMIN_ADDRESS, admin.getAddress());
            db = hd.getWritableDatabase();
            long result = db.insert(DatabaseHandler.ADMIN_TB_NAME,null,value);
            db.close();
            if(result == -1) return false;
            return true;
        }

        public int checkLogin(String userName, String password)
        {
            SQLiteDatabase db;
            String sql = "Select COUNT(*) from " + DatabaseHandler.ADMIN_TB_NAME + " where " + DatabaseHandler.ADMIN_USERNAME + "=? and " + DatabaseHandler.ADMIN_PASSWORD + "=?";
            db = hd.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[] { userName, password });
            int count = 0;
            if (cursor != null) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            db.close();
            cursor.close();
            return count;
        }
}
