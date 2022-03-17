package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity  {

    Button loginButton;
    Button registerActivity;
    Button forgotPassword;

    @Override
    protected  void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.buttonLogin);
        registerActivity = findViewById(R.id.buttonGoToRegister);
        forgotPassword = findViewById(R.id.buttonGoToForgotPassword);
        try {
            loginButton.setOnClickListener(view-> Login());

            registerActivity.setOnClickListener(view -> gotoRegisterActivity());

            forgotPassword.setOnClickListener(view -> gotoForgotPassActivity());
        }catch(Exception e){
            Toast.makeText(this, "Error on clicking buttons event!", Toast.LENGTH_LONG).show();

        }


    }


    private void Login() {



        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void gotoRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void gotoForgotPassActivity() {
        Toast.makeText(this, "The 'Forgot Password' form is not implemented yet!", Toast.LENGTH_LONG).show();
    }

}
