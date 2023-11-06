package com.example.ebookapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    public final static String DB_NAME = "Library.db";
    public final static int VERSION = 6;

    //table database
    // READER TABLE
    public final static String READER_TB_NAME = "Reader";
    public final static String READER_ID = "readerID";
    public final static String READER_NAME = "name";
    public final static String READER_ADDRESS = "address";
    public final static String READER_PHONE = "phone";
    public final static String READER_CITY = "city";

    //BOOK TABLE
    public final static String BOOK_TB_NAME = "Book";
    public final static String BOOK_ID = "bookID";
    public final static String BOOK_TITLE = "title";
    public final static String BOOK_AUTHOR_ID = "authorID";
    public final static String BOOK_YEAR = "year";
    public final static String BOOK_CATEGORY_ID = "categoryID";
    public final static String BOOK_IMAGE = "image";

    //AUTHOR TABLE
    public final static String AUTHOR_TB_NAME = "Author";
    public final static String AUTHOR_ID = "authorID";
    public final static String AUTHOR_NAME = "name";

    //CATEGORY TABLE
    public final static String CATEGORY_TB_NAME = "Category";
    public final static String CATEGORY_ID = "categoryID";
    public final static String CATEGORY_name = "name";

    //ADMIN TABLE
    public final static String ADMIN_TB_NAME = "Admin";
    public final static String ADMIN_ID = "adminID";
    public final static String ADMIN_name = "name";
    public final static String ADMIN_USERNAME = "username";
    public final static String ADMIN_PASSWORD = "password";
    public final static String ADMIN_ADDRESS = "address";

    //BORROWING
    public final static String BRW_TB_NAME = "Borrowing";
    public final static String BRW_ID = "borrowingID";
    public final static String BRW_READER_ID = "readerID";
    public final static String BRW_BORROW_DAY = "borrowDay";
    public final static String BRW_RETURN_DAY = "returnDay";
    public final static String BRW_RETURN_TIME = "returnTime";

    //BORROWING DETAILS
    public final static String BRWDETAILS_TB_NAME = "BorrowingDetails";
    public final static String BRWDETAILS_BRW_ID = "borrowingID";
    public final static String BRWDETAILS_BOOK_ID = "bookID";


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null , VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CreateTable(sqLiteDatabase);
        String TAG = "CREATE TABLE";
        try
        {
            db.execSQL(CreateAuthorTB()); // exec author table
            db.execSQL(CreateCategoryTB()); // exec category table
            db.execSQL(CreateBookTB()); // exec book table

            db.execSQL(CreateReaderTB()); // exec reader table
            db.execSQL(CreateAdminTB()); // exec admin table
            db.execSQL(CreateBorrowingTB()); // exec borrowing
            db.execSQL(CreateBorrowingDetailsTB()); // exec borrowing details
        }
        catch (Exception exception)
        {
            Log.i(TAG, exception.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < newVersion)
        {
            db.execSQL("drop table if exists " + BOOK_TB_NAME);
            db.execSQL("drop table if exists " + READER_TB_NAME);
            db.execSQL("drop table if exists " + AUTHOR_TB_NAME);
            db.execSQL("drop table if exists " + ADMIN_TB_NAME);
            db.execSQL("drop table if exists " + BRW_TB_NAME);
            db.execSQL("drop table if exists " + BRWDETAILS_TB_NAME);
            db.execSQL("drop table if exists " + CATEGORY_TB_NAME);
            onCreate(db);
        }

    }
    private String CreateBookTB()
    {
        String sql = "Create table if not exists " + BOOK_TB_NAME + "(" +
                "" + BOOK_ID + " integer primary key autoincrement not null," +
                "" + BOOK_TITLE + " text," +
                "" + BOOK_AUTHOR_ID + " integer not null," +
                "" + BOOK_YEAR + " date," +
                "" + BOOK_CATEGORY_ID + " integer not null,"+
                "" + BOOK_IMAGE + " blob ," +
                "" + "CONSTRAINT fk_BOOK_CATEGORY_ID FOREIGN KEY (" + BOOK_CATEGORY_ID + ") REFERENCES " + CATEGORY_TB_NAME + "("+ CATEGORY_ID +") ON DELETE CASCADE" + "," +
                "" + "CONSTRAINT fk_BOOK_AUTHOR_ID FOREIGN KEY (" + BOOK_AUTHOR_ID + ") REFERENCES " + AUTHOR_TB_NAME + "("+ AUTHOR_ID +") ON DELETE CASCADE" +
                ");";
        return sql;
    }
    private String CreateReaderTB()
    {
        String sql = "Create table if not exists " + READER_TB_NAME + "(" +
                "" + READER_ID + " integer primary key autoincrement not null, " +
                "" + READER_NAME + " text," +
                "" + READER_ADDRESS + " text," +
                "" + READER_PHONE + " text," +
                "" + READER_CITY + " text);";
        return sql;
    }
    private String CreateAuthorTB()
    {
        String sql = "create table if not exists " + AUTHOR_TB_NAME + "(" +
                "" + AUTHOR_ID + " integer primary key autoincrement not null," +
                "" + AUTHOR_NAME + " text);";
        return sql;
    }
    private String CreateCategoryTB()
    {
        String sql = "create table if not exists "+ CATEGORY_TB_NAME +"(" +
                "" + CATEGORY_ID + " integer primary key autoincrement not null," +
                "" + CATEGORY_name + " text);";
        return sql;
    }
    private String CreateAdminTB()
    {
        String sql = "create table if not exists "+ ADMIN_TB_NAME +"(" +
                "" + ADMIN_ID + " integer primary key autoincrement not null," +
                "" + ADMIN_name + " text," +
                "" + ADMIN_USERNAME + " text," +
                "" + ADMIN_PASSWORD + " text," +
                "" + ADMIN_ADDRESS + " text);";
        return sql;
    }
    private String CreateBorrowingTB()
    {
        String sql = "create table if not exists "+ BRW_TB_NAME +"(" +
                "" + BRW_ID + " integer primary key autoincrement not null," +
                "" + BRW_READER_ID + " integer not null," +
                "" + BRW_BORROW_DAY + " text," +
                "" + BRW_RETURN_DAY + " text," +
                "" + BRW_RETURN_TIME + " text," + "" +
                "" + "CONSTRAINT fk_BRW_READER_ID FOREIGN KEY (" + BRW_READER_ID + ") REFERENCES " + READER_TB_NAME + "("+ READER_ID +") ON DELETE CASCADE" +
                ");";
        return sql;
    }
    private String CreateBorrowingDetailsTB()
    {
        String sql = "create table if not exists " + BRWDETAILS_TB_NAME + "(" +
                "" + BRWDETAILS_BRW_ID + " integer not null ,"+
                "" + BRWDETAILS_BOOK_ID + " integer not null," +
                " PRIMARY KEY (" +BRWDETAILS_BRW_ID + "," + BRWDETAILS_BOOK_ID + ")," +
                "" + "FOREIGN KEY (" + BRWDETAILS_BRW_ID + ") REFERENCES " + BRW_TB_NAME + "("+ BRW_ID +") ON DELETE CASCADE" + "," +
                "" + "FOREIGN KEY (" + BRWDETAILS_BOOK_ID + ") REFERENCES " + BOOK_TB_NAME + "("+ BOOK_ID +") ON DELETE CASCADE" +
                ");";

        return sql;
    }
}
