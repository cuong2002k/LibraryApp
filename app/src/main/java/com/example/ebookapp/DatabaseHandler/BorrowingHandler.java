package com.example.ebookapp.DatabaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Borrowing;
import com.example.ebookapp.Model.BorrowingDetails;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class BorrowingHandler {
    private DatabaseHandler handler;

    public BorrowingHandler(Context context)
    {
        handler = new DatabaseHandler(context);
    }

    public ArrayList<Borrowing> getAll()
    {
        try
        {
            ArrayList<Borrowing> lstBR = new ArrayList<>();
            SQLiteDatabase db = handler.getReadableDatabase();
            Cursor data = db.rawQuery("Select * from " + DatabaseHandler.BRW_TB_NAME, null);
            data.moveToFirst();
            while (!data.isAfterLast())
            {
                int id = data.getInt(0);
                int readerId = data.getInt(1);
                String borrowDay = data.getString(2);
                String returnDay = data.getString(3);
                String returnTime = data.getString(4);

                Borrowing borrowing = new Borrowing(id, readerId, borrowDay, returnDay,returnTime);
                lstBR.add(borrowing);
                data.moveToNext();
            }
            return lstBR;

        }
        catch (Exception ex)
        {
            return null;
        }
    }
    public Boolean insertData(Borrowing borrowing, ArrayList<Book> lstBook)
    {
        try
        {
            ContentValues value = new ContentValues();
            value.put(DatabaseHandler.BRW_READER_ID, borrowing.getReaderId());
            value.put(DatabaseHandler.BRW_BORROW_DAY, borrowing.getBorrowDay());
            value.put(DatabaseHandler.BRW_RETURN_DAY, borrowing.getReturnDay());
            value.put(DatabaseHandler.BRW_RETURN_TIME, (String) null);
            SQLiteDatabase db = handler.getReadableDatabase();
            long id = db.insert(DatabaseHandler.BRW_TB_NAME, null, value);
            for(Book book : lstBook)
            {
                ContentValues content = new ContentValues();
                content.put(DatabaseHandler.BRWDETAILS_BRW_ID, id);
                content.put(DatabaseHandler.BRWDETAILS_BOOK_ID, book.getBookId());
                db.insert(DatabaseHandler.BRWDETAILS_TB_NAME, null, content);
            }
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public Boolean DeleteData(int id)
    {
        try
        {
            SQLiteDatabase db = handler.getWritableDatabase();
            db.delete(DatabaseHandler.BRWDETAILS_TB_NAME,
                    DatabaseHandler.BRWDETAILS_BRW_ID+"=?",
                    new String[]{id+""});
            db.delete(DatabaseHandler.BRW_TB_NAME,
                    DatabaseHandler.BRW_ID+"=?",
                    new String[]{id+""});
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }


    public ArrayList<Book> getAllBook(int idBorrowing)
    {


        try{
            SQLiteDatabase db = handler.getReadableDatabase();
            ArrayList<Book> lstBook = new ArrayList<>();
            String sql = "select Book.bookID, Book.title, Book.authorID, Book.year, Book.categoryID, Book.image " +
                    "from BorrowingDetails join Book " +
                    "on BorrowingDetails.bookID = Book.bookID " +
                    "Where BorrowingDetails.borrowingID = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{idBorrowing+""});
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

    public Boolean UpdateData(Borrowing borrowing, ArrayList<Book> lstBook)
    {
        try
        {
            ContentValues value = new ContentValues();
            value.put(DatabaseHandler.BRW_READER_ID, borrowing.getReaderId());
            value.put(DatabaseHandler.BRW_BORROW_DAY, borrowing.getBorrowDay());
            value.put(DatabaseHandler.BRW_RETURN_DAY, borrowing.getReturnDay());
            value.put(DatabaseHandler.BRW_RETURN_TIME, borrowing.getReturnTime());
            SQLiteDatabase db = handler.getReadableDatabase();
            long id = db.update(DatabaseHandler.BRW_TB_NAME,value , DatabaseHandler.BRW_ID + "=?",
                    new String[]{borrowing.getBorrowingId() + ""});

            db.delete(DatabaseHandler.BRWDETAILS_TB_NAME,
                    DatabaseHandler.BRWDETAILS_BRW_ID+"=?",
                    new String[]{borrowing.getBorrowingId()+""});

            for(Book book : lstBook)
            {
                ContentValues content = new ContentValues();
                content.put(DatabaseHandler.BRWDETAILS_BRW_ID, borrowing.getBorrowingId());
                content.put(DatabaseHandler.BRWDETAILS_BOOK_ID, book.getBookId());
                db.insert(DatabaseHandler.BRWDETAILS_TB_NAME, null, content);
            }
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }


    public Bitmap getImage(byte[] imgByte){
        if (imgByte != null){
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        return null;
    }

    public ArrayList<BarEntry> getStatistical()
    {
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor data = db.rawQuery("Select * from " + DatabaseHandler.BRW_TB_NAME, null);
        int[] result = new int[13];
        data.moveToFirst();
        while (!data.isAfterLast())
        {
            int id = data.getInt(0);
            int readerId = data.getInt(1);
            String borrowDay = data.getString(2);
            String returnDay = data.getString(3);
            String returnTime = data.getString(4);

            Borrowing borrowing = new Borrowing(id, readerId, borrowDay, returnDay,returnTime);
            String datestr = borrowing.getBorrowDay();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            try
            {
                Date date = sdf.parse(datestr);
                int month = Integer.parseInt(String.valueOf(date.getMonth()));
                result[month-1]++;
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }

            data.moveToNext();
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 1; i <= 12; i++)
        {
            barEntries.add(new BarEntry(i , result[i]));
        }
        return barEntries;

    }
}
