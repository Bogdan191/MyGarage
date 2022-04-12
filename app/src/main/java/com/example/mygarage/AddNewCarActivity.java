package com.example.mygarage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddNewCarActivity extends AppCompatActivity {

    DatePicker manufacturedData;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_car);

        manufacturedData = findViewById(R.id.datePickerAddCarManufacturedData);
    }

}
