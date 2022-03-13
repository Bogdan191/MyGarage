package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    @Override
    protected  void onCreate(Bundle savedInstande) {

        super.onCreate(savedInstande);
        setContentView(R.layout.activity_login);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonLogin:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
            case R.id.buttonRegister:
                    startActivity(new Intent(this, RegisterActivity.class));
                    break;
            case R.id.textViewForgotCredentials:
                Toast.makeText(getApplicationContext(), "Create page for resetting the password!", Toast.LENGTH_LONG);
        }

    }
}
