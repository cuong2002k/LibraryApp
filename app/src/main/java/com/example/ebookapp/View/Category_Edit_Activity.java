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
import com.example.ebookapp.DefineAction;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.OKAlert;
import com.example.ebookapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class Category_Edit_Activity extends AppCompatActivity {

    Button saveCategory;
    Button deleteCategory;
    TextInputEditText txtCategory;
    CategoryHandler handler;
    ImageView image_back;
    Category category;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);
        doBackListCategory();
        deleteCategory = findViewById(R.id.btn_Delete);
        handler = new CategoryHandler(this);
        txtCategory = findViewById(R.id.txtNameCategory);

        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("isUpdate", false);

        if(isUpdate)
        {
            Bundle bundle = intent.getBundleExtra("Category");
            category = (Category) bundle.getSerializable("Category");
            txtCategory.setText(category.getName());

            deleteCategory.setVisibility(View.VISIBLE);
            deleteCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HandleDialog(DefineAction.DELETE);
                }
            });
        }
        else {
            deleteCategory.setVisibility(View.GONE);
        }


        HandleSaveCategory(isUpdate);
    }

    private Boolean checkCategoryName()
    {
        int length = txtCategory.getText().toString().trim().length();
        if(length == 0)
        {
            txtCategory.setError("Tên thể loại không được trống");
            return false;
        }
        else if(length < 5)
        {
            txtCategory.setError("Tên thể loại phai tồn tại ít nhất 5 ký tự");
            return false;
        }
        return true;
    }

    private void doBackListCategory()
    {
        image_back = findViewById(R.id.btn_Back_Category);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void HandleSaveCategory(Boolean isUpdate)
    {
        saveCategory = findViewById(R.id.btn_Save);
        saveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCategoryName())
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
        AlertDialogUtil.showYesNoAlertDialog(Category_Edit_Activity.this, "Xác nhận", "Bạn có muốn tiếp tục không?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = txtCategory.getText().toString();
                        if ( Action == DefineAction.UPDATE)
                        {
                            handler.updateData(new Category(category.getId(), name));
                        }
                        else if(Action == DefineAction.CREATE){
                            handler.insertData(new Category(name));
                        }
                        else
                        {
                            handler.deleteData(category.getId());
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