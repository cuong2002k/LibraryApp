package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ebookapp.Adapter.BookAdapter;
import com.example.ebookapp.Adapter.ReaderArrayAdapter;
import com.example.ebookapp.AlertDialogUtil;
import com.example.ebookapp.CODE;
import com.example.ebookapp.DatabaseHandler.BookHandler;
import com.example.ebookapp.DatabaseHandler.BorrowingHandler;
import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.DefineAction;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Borrowing;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.OKAlert;
import com.example.ebookapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Borrowing_Edit_Activity extends AppCompatActivity {

    ArrayList<Reader> lstReader;
    Reader selectReader;
    ReaderArrayAdapter readerAdapter;
    AutoCompleteTextView readerSpinner;
    TextInputEditText brDay, rtDay, brTime;
    ArrayList<Book> lstChooseBook;
    ArrayList<Book> lstBook;
    BookAdapter bookDialogAdapter;
    BookAdapter lvBookAdapter;


    GridView lvListBrBook;

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
            TextInputLayout textInputLayout = findViewById(R.id.tilreturnTime);
            textInputLayout.setVisibility(View.GONE);
        }
        else {
            if(isUpdate)
            {
                brTime.setText(borrowingData.getReturnTime());
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
        }

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
        lvBookAdapter = new BookAdapter(lstBook, Borrowing_Edit_Activity.this){

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View viewProduct;
                if (view == null) {
                    viewProduct = View.inflate(viewGroup.getContext(), R.layout.book_item, null);
                } else viewProduct = view;
                Book book = (Book) getItem(i);
                Bitmap image = book.getImage();
                if (image != null) {
                    Bitmap Resize = Bitmap.createScaledBitmap(image, 100, 120, false);
                    ((ImageView) viewProduct.findViewById(R.id.imageBook)).setImageBitmap(Resize);
                }
                ((TextView) viewProduct.findViewById(R.id.nameBook)).setVisibility(View.GONE);
                ((TextView) viewProduct.findViewById(R.id.authorBook)).setVisibility(View.GONE);
                ((TextView) viewProduct.findViewById(R.id.CategoryBook)).setVisibility(View.GONE);
                viewProduct.findViewById(R.id.lnLayoutBook).setVisibility(View.GONE);
                return viewProduct;
            }
        };

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
        readerAdapter = new ReaderArrayAdapter(Borrowing_Edit_Activity.this, android.R.layout.simple_list_item_1,lstReader);
        readerSpinner = findViewById(R.id.readerSpinner);
        readerSpinner.setAdapter(readerAdapter);
        if(isUpdate)
        {
            selectReader = readerHandler.getReaderWithID(borrowingData.getReaderId());
            readerSpinner.setText(
                    selectReader.getName()
            );
        }
        readerSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectReader = (Reader) adapterView.getItemAtPosition(i);
            }
        });
    }

    private void HandlerSaveButton(Boolean isUpdate)
    {
        save = findViewById(R.id.btn_Save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAll())
                {
                    if(isUpdate)
                    {
                        HandleDialog(DefineAction.UPDATE);
                    }
                    else {
                        HandleDialog(DefineAction.CREATE);
                    }
                }

            }
        });
    }

    private void HandleDialog(String Action)
    {
        AlertDialogUtil.showYesNoAlertDialog(Borrowing_Edit_Activity.this, "Xác nhận", "Bạn có muốn tiếp tục không?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int readerId = selectReader.getReaderId();
                        //brDay, returnDay, brTime;
                        String borrowDay = brDay.getText().toString();
                        String returnDay = rtDay.getText().toString();
                        String returnTime = null;

                        if ( Action == DefineAction.UPDATE)
                        {
                            returnTime = brTime.getText().toString();
                            Borrowing borrowing = new Borrowing(borrowingData.getBorrowingId(),readerId, borrowDay, returnDay, returnTime);
                            borrowingHandler.UpdateData(borrowing, lstBook);
                        }
                        else if(Action == DefineAction.CREATE){
                            Borrowing borrowing = new Borrowing(readerId, borrowDay, returnDay, returnTime);
                            borrowingHandler.insertData(borrowing, lstBook);
                        }
                        else
                        {
                            borrowingHandler.DeleteData(borrowingData.getBorrowingId());
                        }
                        setResult(CODE.RES);
                        finish();
                        dialog.dismiss();

                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng cửa sổ thông báo
                    }
                });
    }

    private  void HandlerDeleteButton(Boolean isUpdate)
    {
        delete = findViewById(R.id.btn_Delete);

        if(!isUpdate) delete.setVisibility(View.GONE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandleDialog(DefineAction.DELETE);
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

    private Boolean checkAll()
    {
        return checkReader() && checkListBook() && checkBorDay() && checkReturnDay();
    }

    private boolean checkReader()
    {
        if(selectReader == null)
        {
            readerSpinner.setError("Vui lòng chọn đọc giả mượn sách");
            return false;
        }
        return true;
    }

    boolean checkListBook()
    {
        if(lstBook.size() == 0)
        {
            OKAlert.ShowOkeAlert(Borrowing_Edit_Activity.this, "Danh sách mượn trống");
            return false;

        }
        return true;
    }

    boolean checkBorDay()
    {
        int leng = brDay.getText().toString().trim().length();
        if(leng == 0)

        {
            brDay.setError("Ngày mượn không được trống");
            return false;
        }
        return true;
    }

    boolean checkReturnDay()
    {
        int leng = rtDay.getText().toString().trim().length();
        String borrowing = brDay.getText().toString().trim();
        String returnDay = rtDay.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = sdf.parse(borrowing);
            Date date2 = sdf.parse(returnDay);
            if(date2.compareTo(date1) < 0)
            {
                TextInputLayout textInputLayout = findViewById(R.id.tilreturnDay);
                textInputLayout.setError("Ngày trả phải lớn hơn ngày mượn");
                return false;
            }
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(leng == 0)

        {
            rtDay.setError("Ngày trả không được trống");
            return false;
        }
        return true;
    }



}