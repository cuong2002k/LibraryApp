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
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.DefineAction;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.OKAlert;
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
                    HandleDialog(DefineAction.DELETE);
                }
            });
        }
        else {
            delete.setVisibility(View.GONE);
        }

        handleSaveButton(isUpdate);


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean checkAllAction()
    {

        boolean check = (txtName.length() > 3 && txtAddress.length() > 3 && txtPhone.length() > 3 && txtCity.length()>3) ;
        return check;
    }

    private void handleSaveButton(Boolean isUpdate)
    {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllAction())
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
                    OKAlert.ShowOkeAlert(Reader_Edit_Activity.this);
                }

            }
        });
    }

    private void HandleDialog(String Action)
    {
        AlertDialogUtil.showYesNoAlertDialog(Reader_Edit_Activity.this, "Xác nhận", "Bạn có muốn tiếp tục không?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = txtName.getText().toString();
                        String address = txtAddress.getText().toString();
                        String phone = txtPhone.getText().toString();
                        String city = txtCity.getText().toString();
                        if ( Action == DefineAction.UPDATE)
                        {
                            Reader reader = new Reader(readerData.getReaderId(),name,address,phone,city);
                            handler.updateData(reader);
                        }
                        else if(Action == DefineAction.CREATE){

                            Reader reader = new Reader(name,address,phone,city);
                            handler.insertData(reader);
                        }
                        else
                        {
                            handler.deleteData(readerData.getReaderId());
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