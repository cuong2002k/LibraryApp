package com.example.ebookapp.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebookapp.Database.DatabaseHandler;
import com.example.ebookapp.DatabaseHandler.AdminHandler;
import com.example.ebookapp.Model.Admin;
import com.example.ebookapp.OKAlert;
import com.example.ebookapp.R;

public class Login extends AppCompatActivity {
    Button login;
    EditText username, password;
    AdminHandler db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = findViewById(R.id.btnlogin);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);

        db = new AdminHandler(Login.this);
        if(db.checkAdmin() == 0)
        {
            db.insertData(new Admin("ADMIN","admin", "admin", "Binh Duong"));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checklogin())
                {
                    Intent intent = new Intent();
                    intent.setClass(Login.this, ManagerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private  Boolean checklogin(){
        String userNametxt = username.getText().toString().trim();
        String passWordtxt = password.getText().toString().trim();
        if(userNametxt.length() == 0 || passWordtxt.length() == 0)
        {
            OKAlert.ShowOkeAlert(Login.this, "Tài khoản hoặc mật khẩu không được trống");
            return false;
        }
        if(db.checkLogin(userNametxt, passWordtxt) == 0)
        {
            OKAlert.ShowOkeAlert(Login.this, "Tài khoản hoặc mật khẩu không chính xác");
            return false;
        }
        return true;
    }
}
