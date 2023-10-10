package com.example.ebookapp.View;

import static android.view.View.GONE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebookapp.Adapter.AuthorAdapter;
import com.example.ebookapp.Adapter.CategoryAdapter;
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
    final static int GALLERY_REQ_CODE = 1009;
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
            int idBook = intent.getIntExtra("idBook", 0);
            bookData = handler.getBookWithID(idBook);
            txtTile.setText(bookData.getTitle().toString());
            txtYear.setText(bookData.getYear().toString());

            author.setSelection(getIndexAuthor(lstAuthor, bookData.getAuthor()));
            category.setSelection(getIndexCategory(lstCategory, bookData.getCategory()));

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
            delete.setVisibility(GONE);
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
                    Book book = new Book(bookData.getBookId(),title,selectAuthor.getId(),year,selectCategory.getId(),image);
                    handler.updateData(book);

                }
                else {
                    String title = txtTile.getText().toString();
                    String year = txtYear.getText().toString();
                    Bitmap image = bitmapimg;
                    Book book = new Book(title,selectAuthor.getId(),year,selectCategory.getId(),image);
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

        AuthorAdapter authorAdapter = new AuthorAdapter(lstAuthor){
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View viewProduct;
                if (view == null) {
                    viewProduct = View.inflate(viewGroup.getContext(), R.layout.author_item_layout, null);
                } else viewProduct = view;
                Author author = (Author) getItem(i);
                ((TextView) viewProduct.findViewById(R.id.txtID)).setText("");
                ((TextView) viewProduct.findViewById(R.id.txtName)).setText(author.getName() + "");
                return viewProduct;
            }
        };

        CategoryAdapter categoryAdapter = new CategoryAdapter(lstCategory){
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View viewProduct;
                if (view == null) {
                    viewProduct = View.inflate(viewGroup.getContext(), R.layout.author_item_layout, null);
                } else viewProduct = view;
                Category category = (Category) getItem(i);
                ((TextView) viewProduct.findViewById(R.id.txtID)).setText("");
                ((TextView) viewProduct.findViewById(R.id.txtName)).setText(category.getName() + "");
                return viewProduct;
            }
        };

        author.setAdapter(authorAdapter);
        category.setAdapter(categoryAdapter);

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

    private int getIndexAuthor(ArrayList<Author> lstItem, int idItem)
    {
        for(int i = 0; i < lstItem.size(); i++)
        {
            if(lstItem.get(i).getId() == idItem) return i;
        }
        return 0;
    }

    private int getIndexCategory(ArrayList<Category> lstItem, int idItem)
    {
        for(int i = 0; i < lstItem.size(); i++)
        {
            if(lstItem.get(i).getId() == idItem) return i;
        }
        return 0;
    }


}