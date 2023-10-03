package com.example.ebookapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ebookapp.R;

public class ManagerActivity extends AppCompatActivity {
    private CardView cardAuthor, cardCategory, cardReader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);
        DoAuthorActivity();
        DoCategoryActivity();
        DoReaderActivity();

    }

    private void DoAuthorActivity()
    {
        cardAuthor = findViewById(R.id.cardAuthor);
        cardAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ManagerActivity.this, Author_List_Activity.class);
                startActivity(intent);
            }
        });
    }
    private void DoCategoryActivity()
    {
        cardCategory = findViewById(R.id.cardCategory);
        cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ManagerActivity.this, Category_List_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void DoReaderActivity()
    {
        cardReader = findViewById(R.id.cardReader);
        cardReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ManagerActivity.this, Reader_List_Activity.class);
                startActivity(intent);
            }
        });
    }

}
