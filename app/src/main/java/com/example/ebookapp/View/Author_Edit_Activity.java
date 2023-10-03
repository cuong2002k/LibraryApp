package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.R;

public class Author_Edit_Activity extends AppCompatActivity {

    Button saveAuthor;
    Button deleteAuthor;
    EditText txtAuthor;
    AuthorHandler handler;
    ImageView image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_author);
        saveAuthor = findViewById(R.id.btn_Save_Author);
        txtAuthor = findViewById(R.id.AuthorName);
        deleteAuthor = findViewById(R.id.btn_Delete_Author);
        image_back = findViewById(R.id.btn_Back_Author);

        handler = new AuthorHandler(Author_Edit_Activity.this);
        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("isUpdate", false);



        if(isUpdate)
        {
            Bundle bundle = intent.getBundleExtra("Author");
            Author author = (Author) bundle.getSerializable("Author");
            txtAuthor.setText(author.getName());
            deleteAuthor.setVisibility(View.VISIBLE);
            deleteAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.deleteData(author.getId());
                    setResult(Author_List_Activity.RES_INSERT_AUTHOR);
                    finish();
                }
            });
        }
        else
        {
            deleteAuthor.setVisibility(View.GONE);
        }



        saveAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isUpdate)
                {
                    String name = txtAuthor.getText().toString();
                    Author author = new Author(name);
                    handler.insertData(author);
                    setResult(Author_List_Activity.RES_INSERT_AUTHOR);
                    finish();
                }
                else {
                    Bundle bundle = intent.getBundleExtra("Author");
                    Author author = (Author) bundle.getSerializable("Author");
                    author.setName(txtAuthor.getText().toString());
                    handler.updateData(author);
                    setResult(Author_List_Activity.RES_INSERT_AUTHOR);
                    finish();
                }

            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
            //......
        });

    }


}