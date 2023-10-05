package com.example.ebookapp.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.DatabaseHandler.BookHandler;
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Book_Edit_Activity extends AppCompatActivity {
    Button save, delete, choseImage;
    EditText txtTile, txtYear;
    Spinner author, category;
    BookHandler handler;
    ImageView image_back, show_Image;
    Book bookData;
    final static int GALLERY_REQ_CODE = 1001;
    ArrayList<Author> lstAuthor;
    ArrayList<Category> lstCategory;

    Bitmap bitmapimg;
    Author selectAuthor;
    Category selectCategory;
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

        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("isUpdate", false);
        fillSpinner();
        if(isUpdate)
        {
            Bundle bundle = intent.getBundleExtra("Book");
            bookData = (Book) bundle.getSerializable("Book");
            txtTile.setText(bookData.getTitle().toString());
            txtYear.setText(bookData.getYear().toString());
            show_Image.setImageBitmap(bookData.getImage());
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.deleteData(bookData.getBookId());
                    setResult(Book_List_Activity.RES_INSERT);
                    finish();
                }
            });
        }
        else {
            delete.setVisibility(View.GONE);
        }

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpdate)
                {
                    String title = txtTile.getText().toString();
                    String year = txtYear.getText().toString();
                    Bitmap image = bookData.getImage();
                    if(bitmapimg != null)
                    {
                        image = bitmapimg;
                    }

                }
                else {
                    String title = txtTile.getText().toString();
                    String year = txtYear.getText().toString();
                    Bitmap image = bitmapimg;
                    Book book = new Book(title,1,year,1,image);
                    handler.insertData(book);
                }
                setResult(Book_List_Activity.RES_INSERT);
                finish();
            }
        });

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
        AuthorHandler authorHandler = new AuthorHandler(Book_Edit_Activity.this);
        CategoryHandler categoryHandler = new CategoryHandler(Book_Edit_Activity.this);

        lstAuthor = authorHandler.getall();
        lstCategory = categoryHandler.getAll();
        ArrayAdapter<Author> arrayAdapterAuthor = new ArrayAdapter<Author>(Book_Edit_Activity.this,
                android.R.layout.simple_list_item_1, lstAuthor);
        arrayAdapterAuthor.setDropDownViewResource(android.R.layout.simple_list_item_1);

        ArrayAdapter<Category> arrayAdapterCategory = new ArrayAdapter<Category>(Book_Edit_Activity.this,
                android.R.layout.simple_list_item_1, lstCategory);
        arrayAdapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_1);

        author.setAdapter(arrayAdapterAuthor);
        category.setAdapter(arrayAdapterCategory);

        author.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectAuthor = lstAuthor.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectCategory = lstCategory.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void GetImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQ_CODE);
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