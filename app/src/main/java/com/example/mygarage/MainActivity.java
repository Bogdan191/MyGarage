package com.example.mygarage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button logOutButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutButton = findViewById(R.id.buttonLogOut);
        logOutButton.setOnClickListener(view -> gotoLoginActivity());

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            //handle the case when the user is already logged in
        } else {
            //handle the case when the user is not logged in, this means the app should go to login page
            Toast.makeText(this, "Please login in the Application with your account", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void gotoLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}