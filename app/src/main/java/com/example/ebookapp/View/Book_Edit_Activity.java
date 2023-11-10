package com.example.ebookapp.View;

import static android.view.View.GONE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebookapp.Adapter.AuthorAdapter;
import com.example.ebookapp.Adapter.AuthorArrayAdapter;
import com.example.ebookapp.Adapter.CategoryAdapter;
import com.example.ebookapp.Adapter.CategoryArrayAdapter;
import com.example.ebookapp.AlertDialogUtil;
import com.example.ebookapp.CODE;
import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.DatabaseHandler.BookHandler;
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.DefineAction;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.OKAlert;
import com.example.ebookapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Book_Edit_Activity extends AppCompatActivity {
    Button save, delete, choseImage;
    TextInputEditText txtTile, txtYear;
    AutoCompleteTextView author, category;
    BookHandler handler;
    ImageView image_back, show_Image;
    Book bookData;
    final static int GALLERY_REQ_CODE = 1009;
    ArrayList<Author> lstAuthor;
    ArrayList<Category> lstCategory;

    Bitmap bitmapimg;
    Author selectAuthor;
    Category selectCategory;
    AuthorHandler authorHandler;
    CategoryHandler categoryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        txtTile = findViewById(R.id.titleBook);
        txtYear = findViewById(R.id.yearBook);
        choseImage = findViewById(R.id.btn_image);
        save = findViewById(R.id.btn_Save);
        delete = findViewById(R.id.btn_Delete);
        image_back = findViewById(R.id.btn_Back);
        show_Image = findViewById(R.id.imageBook);
        handler = new BookHandler(Book_Edit_Activity.this);
        authorHandler = new AuthorHandler(Book_Edit_Activity.this);
        categoryHandler = new CategoryHandler(Book_Edit_Activity.this);
        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("isUpdate", false);
        fillSpinner();

        if(isUpdate)
        {
            int idBook = intent.getIntExtra("idBook", 0);
            bookData = handler.getBookWithID(idBook);
            txtTile.setText(bookData.getTitle().toString());
            txtYear.setText(bookData.getYear().toString());
            selectAuthor = authorHandler.getAuthorWithID(bookData.getAuthor());
            selectCategory = categoryHandler.getCategoryWithID(bookData.getCategory());
            author.setText(selectAuthor.getName());
            category.setText(selectCategory.getName());
            show_Image.setImageBitmap(bookData.getImage());
            bitmapimg = bookData.getImage();

            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HandleDialog(DefineAction.DELETE);
                }
            });
        }
        else {
            delete.setVisibility(GONE);
        }

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handleSaveButton(isUpdate);

        choseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImage();
            }
        });
    }
    private void fillSpinner()
    {
        author = findViewById(R.id.authorBook);
        category = findViewById(R.id.categoryBook);


        lstAuthor = authorHandler.getall();
        lstCategory = categoryHandler.getAll();

        AuthorArrayAdapter authorAdapter = new AuthorArrayAdapter(Book_Edit_Activity.this,
                android.R.layout.simple_list_item_1,
                lstAuthor);
        CategoryArrayAdapter categoryAdapter = new CategoryArrayAdapter(Book_Edit_Activity.this,
                android.R.layout.simple_list_item_1, lstCategory);


        author.setAdapter(authorAdapter);
        category.setAdapter(categoryAdapter);
        author.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Author results = (Author) parent.getItemAtPosition(position);
                selectAuthor = results;
            }
        });
        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category results = (Category) parent.getItemAtPosition(position);
                selectCategory = results;
            }
        });





    }
    private void GetImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQ_CODE);
    }

    private boolean checkTitle()
    {
        int leng = txtTile.getText().toString().trim().length();
        if(leng == 0)
        {
            txtTile.setError("Tiêu đề sách không được trống");
            return false;
        }
        return true;
    }

    private  boolean checkYear()
    {
        int leng = txtYear.getText().toString().trim().length();
        if(leng == 0){
            txtYear.setError("Năm xuất bản sách không được trống.");
            return false;
        }
        return true;
    }

    private boolean checkImage()
    {
        if(bitmapimg == null)
        {
            OKAlert.ShowOkeAlert(Book_Edit_Activity.this, "Hình ảnh minh họa không được trống.");
            return false;
        }
        return  true;
    }

    private boolean checkCategory()
    {
        if(selectCategory == null)
        {
            category.setError("Vui lòng chọn thể loại sách");
            return false;
        }
        return true;
    }
    private boolean checkAuthor()
    {
        if(selectAuthor == null)
        {
            category.setError("Vui lòng chọn thể tác sách");
            return false;
        }
        return true;
    }


    private Boolean checkAll()
    {
        return checkTitle() && checkYear() && checkAuthor() && checkCategory() && checkImage();
    }

    private void handleSaveButton(Boolean isUpdate)
    {
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
        AlertDialogUtil.showYesNoAlertDialog(Book_Edit_Activity.this, "Xác nhận", "Bạn có muốn tiếp tục không?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String title = txtTile.getText().toString();
                        String year = txtYear.getText().toString();
                        Bitmap image;

                        if ( Action == DefineAction.UPDATE)
                        {
                            image = bookData.getImage();
                            if(bitmapimg != null)
                            {
                                image = bitmapimg;
                            }
                            Book book = new Book(bookData.getBookId(),title,selectAuthor.getId(),year,selectCategory.getId(),image);
                            handler.updateData(book);
                        }
                        else if(Action == DefineAction.CREATE){
                            image = bitmapimg;
                            Book book = new Book(title,selectAuthor.getId(),year,selectCategory.getId(),image);
                            handler.insertData(book);
                        }
                        else
                        {
                            handler.deleteData(bookData.getBookId());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == GALLERY_REQ_CODE)
            {
                show_Image.setImageURI(data.getData());
                Uri imageUri = data.getData();

                try {
                    bitmapimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }







}