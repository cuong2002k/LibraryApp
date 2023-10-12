package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ebookapp.Adapter.BookAdapter;
import com.example.ebookapp.Adapter.ReaderAdapter;
import com.example.ebookapp.DatabaseHandler.BookHandler;
import com.example.ebookapp.DatabaseHandler.BorrowingHandler;
import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Borrowing;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Borrowing_Edit_Activity extends AppCompatActivity {

    ArrayList<Reader> lstReader;
    Reader reader;
    ReaderAdapter readerAdapter;
    Spinner readerSpinner;

    EditText brDay, rtDay, brTime;

    ArrayList<Book> lstChooseBook;
    ArrayList<Book> lstBook;
    BookAdapter bookDialogAdapter;
    BookAdapter lvBookAdapter;


    ListView lvListBrBook;

    ImageView image_back;

    Button save, delete, chooseBook;

    BookHandler bookHandler;
    ReaderHandler readerHandler;
    BorrowingHandler borrowingHandler;

    Borrowing borrowingData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowing_edit);
        bookHandler = new BookHandler(Borrowing_Edit_Activity.this);
        readerHandler = new ReaderHandler(Borrowing_Edit_Activity.this);
        borrowingHandler = new BorrowingHandler(Borrowing_Edit_Activity.this);
        lstChooseBook = bookHandler.getAllBook();

        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("isUpdate", false);

        if (isUpdate)
        {
            Bundle bundle = intent.getBundleExtra("Borrow");
            borrowingData = (Borrowing) bundle.getSerializable("Borrow");

        }



        DoBack();
        HandlerBrDay(isUpdate);
        HandlerReturnTime(isUpdate);
        HandlerReturnDay(isUpdate);
        HandlerBRView(isUpdate);
        HandlerChooseBook(isUpdate);
        HandlerChooseReader(isUpdate);
        HandlerSaveButton(isUpdate);
        HandlerDeleteButton(isUpdate);




    }

    private void DoBack()
    {
        image_back = findViewById(R.id.btn_Back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void HandlerBrDay(Boolean isUpdate)
    {
        brDay = findViewById(R.id.borrowDay);

        if(isUpdate)
        {
            brDay.setText(borrowingData.getBorrowDay());
        }
        brDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Borrowing_Edit_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                brDay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });
    }
    private void HandlerReturnDay(Boolean isUpdate)
    {
        rtDay = findViewById(R.id.returnDay);

        if(isUpdate)
        {
            rtDay.setText(borrowingData.getReturnDay());
        }
        rtDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Borrowing_Edit_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                rtDay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });
    }
    private void HandlerReturnTime(Boolean isUpdate)
    {
        brTime = findViewById(R.id.returnTime);
        if(isUpdate == false)
        {
            brTime.setVisibility(View.GONE);
            TextView tv = findViewById(R.id.txtret);
            tv.setVisibility(View.GONE);
        }
        else {
            if(isUpdate)
            {
                brTime.setText(borrowingData.getReturnTime());
            }
        }
        brTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Borrowing_Edit_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                brTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });
    }

    private void HandlerBRView(Boolean isUpdate)
    {
        lvListBrBook = findViewById(R.id.lvBook);

        lstBook = new ArrayList<>();
        if(isUpdate)
        {
            lstBook.addAll(borrowingHandler.getAllBook(borrowingData.getBorrowingId()));
            for(Book book : lstBook)
            {
                RemoveItemLIst(book);
            }
        }
        lvBookAdapter = new BookAdapter(lstBook, Borrowing_Edit_Activity.this);

        bookDialogAdapter = new BookAdapter(lstChooseBook, Borrowing_Edit_Activity.this);

        lvListBrBook.setAdapter(lvBookAdapter);



        lvListBrBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lstChooseBook.add(lstBook.get(i));
                lstBook.remove(i);
                bookDialogAdapter.loadingData();
                LoadDataListView();
            }
        });
    }

    private void HandlerChooseBook(Boolean isUpdate)
    {
        chooseBook = findViewById(R.id.btn_choose);
        chooseBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog(isUpdate);
            }
        });
    }

    private void ShowDialog(Boolean isUpdate)
    {
        LayoutInflater inflater = (LayoutInflater) Borrowing_Edit_Activity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.borrow_book_layout, null);
        Dialog dialog = new Dialog(Borrowing_Edit_Activity.this);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        final ListView lvBrBook = dialog.findViewById(R.id.lvBrBook);
        lvBrBook.setAdapter(bookDialogAdapter);
        lvBrBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    lstBook.add(lstChooseBook.get(i));
                    lstChooseBook.remove(i);
                    bookDialogAdapter.loadingData();
                    LoadDataListView();
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void LoadDataListView()
    {
        lvBookAdapter.loadingData();
    }

    private void HandlerChooseReader(Boolean isUpdate)
    {
        lstReader = readerHandler.getAllReader();
        readerAdapter = new ReaderAdapter(lstReader);
        readerSpinner = findViewById(R.id.readerSpinner);
        readerSpinner.setAdapter(readerAdapter);
        if(isUpdate)
        {
            for(int i = 0; i < lstReader.size(); i++)
            {
                if(lstReader.get(i).getReaderId() == borrowingData.getReaderId())
                {
                    readerSpinner.setSelection(i);
                    break;
                }
            }
        }
        readerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reader = lstReader.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void HandlerSaveButton(Boolean isUpdate)
    {
        save = findViewById(R.id.btn_Save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isUpdate)
                {
                    int readerId = reader.getReaderId();
                    //brDay, returnDay, brTime;
                    String borrowDay = brDay.getText().toString();
                    String returnDay = rtDay.getText().toString();
                    String returnTime = brTime.getText().toString();
                    Borrowing borrowing = new Borrowing(borrowingData.getBorrowingId(),readerId, borrowDay, returnDay, returnTime);
                    borrowingHandler.UpdateData(borrowing, lstBook);
                }
                else {
                    int readerId = reader.getReaderId();
                    //brDay, returnDay, brTime;
                    String borrowDay = brDay.getText().toString();
                    String returnDay = rtDay.getText().toString();
                    String returnTime = null;
                    Borrowing borrowing = new Borrowing(readerId, borrowDay, returnDay, returnTime);
                    borrowingHandler.insertData(borrowing, lstBook);
                }


                setResult(Borrowing_List_Activity.RES_INSERT);
                finish();
            }
        });
    }

    private  void HandlerDeleteButton(Boolean isUpdate)
    {
        delete = findViewById(R.id.btn_Delete);

        if(!isUpdate) delete.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrowingHandler.DeleteData(borrowingData.getBorrowingId());
                setResult(Borrowing_List_Activity.RES_INSERT);
                finish();
            }
        });
    }

    private void RemoveItemLIst(Book book)
    {
        for(Book bk : lstChooseBook)
        {
            if(bk.getBookId() == book.getBookId())
            {
                lstChooseBook.remove(bk);
                return;
            }
        }
    }



}