package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ebookapp.AlertDialogUtil;
import com.example.ebookapp.CODE;
import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.DefineAction;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.OKAlert;
import com.example.ebookapp.R;

public class Author_Edit_Activity extends AppCompatActivity {

    Button saveAuthor;
    Button deleteAuthor;
    EditText txtAuthor;
    AuthorHandler handler;
    ImageView image_back;

    Author author;

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
            author = (Author) bundle.getSerializable("Author");
            txtAuthor.setText(author.getName());
            deleteAuthor.setVisibility(View.VISIBLE);
            deleteAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HandleDialog(DefineAction.DELETE);
                }
            });
        }
        else
        {
            deleteAuthor.setVisibility(View.GONE);
        }

        HandleSaveAuthor(isUpdate);



        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
            //......
        });

    }

    private boolean checkName()
    {
        if(txtAuthor.length() < 3) return  false;
        return true;
    }

    private void HandleSaveAuthor(Boolean isUpdate)
    {
        saveAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkName())
                {
                    if(isUpdate)
                    {
                        HandleDialog(DefineAction.UPDATE);
                    }
                    else {
                        HandleDialog(DefineAction.CREATE);
                    }
                }
                else{
                    OKAlert.ShowOkeAlert(Author_Edit_Activity.this);
                }

            }
        });
    }

    private void HandleDialog(String Action)
    {
        AlertDialogUtil.showYesNoAlertDialog(Author_Edit_Activity.this, "Xác nhận", "Bạn có muốn tiếp tục không?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = txtAuthor.getText().toString();
                        if ( Action == DefineAction.UPDATE)
                        {
                            author.setName(txtAuthor.getText().toString());
                            handler.updateData(author);
                        }
                        else if(Action == DefineAction.CREATE){

                            Author author = new Author(name);
                            handler.insertData(author);
                        }
                        else
                        {
                            handler.deleteData(author.getId());
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



}