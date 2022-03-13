package com.example.mygarage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button logOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutButton = findViewById(R.id.buttonLogOut);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginActivity();
            }
        });

    }

    private void gotoLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}