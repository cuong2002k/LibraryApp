package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

public class Reader_Edit_Activity extends AppCompatActivity {

    Button save;
    Button delete;
    EditText txtName, txtAddress, txtPhone, txtCity;
    ReaderHandler handler;
    ImageView image_back;
    Reader readerData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_edit);
        txtName = findViewById(R.id.nameReader);
        txtAddress = findViewById(R.id.addressReader);
        txtPhone = findViewById(R.id.phoneReader);
        txtCity = findViewById(R.id.cityReader);
        save = findViewById(R.id.btn_Save);
        delete = findViewById(R.id.btn_Delete);
        image_back = findViewById(R.id.btn_Back);
        handler = new ReaderHandler(Reader_Edit_Activity.this);


        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("isUpdate", false);

        if(isUpdate)
        {
            Bundle bundle = intent.getBundleExtra("Reader");
            readerData = (Reader)bundle.getSerializable("Reader");
            txtName.setText(readerData.getName().toString());
            txtAddress.setText(readerData.getAddress().toString());
            txtPhone.setText(readerData.getPhone().toString());
            txtCity.setText(readerData.getCity().toString());
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.deleteData(readerData.getReaderId());
                    setResult(Reader_List_Activity.RES_INSERT);
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
                    String name = txtName.getText().toString();
                    String address = txtAddress.getText().toString();
                    String phone = txtPhone.getText().toString();
                    String city = txtCity.getText().toString();
                    Reader reader = new Reader(readerData.getReaderId(),name,address,phone,city);
                    handler.updateData(reader);
                }
                else {
                    String name = txtName.getText().toString();
                    String address = txtAddress.getText().toString();
                    String phone = txtPhone.getText().toString();
                    String city = txtCity.getText().toString();
                    Reader reader = new Reader(name,address,phone,city);
                    handler.insertData(reader);
                }
                setResult(Reader_List_Activity.RES_INSERT);
                finish();
            }
        });

    }


}