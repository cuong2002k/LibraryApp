package com.example.ebookapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ebookapp.R;

public class ManagerActivity extends AppCompatActivity {
    private CardView cardbook;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);
        DoBookActivity();

    }

    private void DoBookActivity()
    {
        cardbook = findViewById(R.id.cardBook);
        cardbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ManagerActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });
    }
}
