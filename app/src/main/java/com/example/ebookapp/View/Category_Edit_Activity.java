package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.R;

public class Category_Edit_Activity extends AppCompatActivity {

    Button saveCategory;
    Button deleteCategory;
    EditText txtCategory;
    CategoryHandler handler;
    ImageView image_back;
    Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);
        doBackListCategory();
        deleteCategory = findViewById(R.id.btn_Delete_Category);
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
                    handler.deleteData(category.getId());
                    setResult(Category_List_Activity.RES_INSERT_CATEGORY);
                    finish();
                }
            });
        }
        else {
            deleteCategory.setVisibility(View.GONE);
        }
        saveCategory = findViewById(R.id.btn_Save_Category);
        saveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( isUpdate == true)
                {
                    String name = txtCategory.getText().toString();
                    handler.updateData(new Category(category.getId(), name));
                }
                else {
                    String name = txtCategory.getText().toString();
                    handler.insertData(new Category(name));
                }
                setResult(Category_List_Activity.RES_INSERT_CATEGORY);
                finish();
            }
        });

    }

    private void doBackListCategory()
    {
        image_back = findViewById(R.id.btn_Back_Category);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
            //......
        });
    }


}