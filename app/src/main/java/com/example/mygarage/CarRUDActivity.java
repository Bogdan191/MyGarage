package com.example.mygarage;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;

public class CarRUDActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_rud);

        tv = findViewById(R.id.textViewCarDetails);

        //select car from db
        String carId = getIntent().getStringExtra("CAR_ID");
        DBHelper dbHelper = new DBHelper(CarRUDActivity.this);
        CarModel car = dbHelper.getCarById(carId);

        tv.setText(car.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
