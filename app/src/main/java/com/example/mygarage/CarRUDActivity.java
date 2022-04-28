package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;

public class CarRUDActivity extends AppCompatActivity {

    TextView textViewCarDetails;
    ImageView ivCarDetails;
    Button buttonDeleteCar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_rud);

        //select car from db
        String carId = getIntent().getStringExtra("CAR_ID");
        DBHelper dbHelper = new DBHelper(CarRUDActivity.this);
        CarModel car = dbHelper.getCarById(carId);

        textViewCarDetails = findViewById(R.id.textViewCarDetails);
        ivCarDetails = findViewById(R.id.imageViewCarDetails);
        buttonDeleteCar = findViewById(R.id.buttonDeleteCarFromDB);

        textViewCarDetails.setText(car.toString());
        ivCarDetails.setImageBitmap(Utils.getImage(car.getCarImage()));
        buttonDeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCarFromDB(car.getId());
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void deleteCarFromDB(String carId) {

        DBHelper dbHelper = new DBHelper(CarRUDActivity.this);
        boolean successOnDeleteCar = dbHelper.deleteCar(carId);
        if(successOnDeleteCar) {
            startActivity(new Intent(getApplicationContext(), MyCarsActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "Eoare! Masina nu a putut fi stearsa! Te rugam incarca din nou.", Toast.LENGTH_SHORT).show();
        }
    }
}
