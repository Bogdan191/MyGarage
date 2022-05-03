package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CarRUDActivity extends AppCompatActivity {

    TextView textViewCarName;
    TextView tvCarManufacturedData;
    TextView tvCarOdometer, tvCarEngine, tvCarHP, tvCarEmission, tvCarRimSize, tvCarGearbox, tvCarMarketValue;

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

        textViewCarName = findViewById(R.id.tvCarName);
        ivCarDetails = findViewById(R.id.imageViewCarDetails);
        tvCarManufacturedData = findViewById(R.id.tvCarManufacturedData);
        tvCarOdometer = findViewById(R.id.tvCarOdometer);
        tvCarEngine = findViewById(R.id.tvCarEngine);
        tvCarHP = findViewById(R.id.tvCarHP);
        tvCarEmission = findViewById(R.id.tvCarEmission);
        tvCarRimSize = findViewById(R.id.tvCarRimSize);
        tvCarGearbox = findViewById(R.id.tvCarGearbox);
        tvCarMarketValue = findViewById(R.id.tvCarMarketValue);

        buttonDeleteCar = findViewById(R.id.buttonDeleteCarFromDB);

        //afiseaza datele masinii in campurile aferente
        textViewCarName.setText(car.getMake() + " " + car.getModel());
        ivCarDetails.setImageBitmap(Utils.getImage(car.getCarImage()));
        tvCarManufacturedData.setText(car.getManufactured_date());
        tvCarOdometer.setText(car.getOdometer()+" km");
        tvCarEngine.setText(car.getEngine_capacity());
        tvCarHP.setText(car.getHorse_power() + " CP");
        tvCarEmission.setText(car.getEmission_standard());
        tvCarRimSize.setText(car.getRim_size());
        tvCarGearbox.setText(car.isManual_gearbox()  == true ? "Cutie de viteze manuala" : "Cutie de viteze automata");
        tvCarMarketValue.setText("Valoarea curenta a masinii: " + car.getCurrent_market_value() + "€");

        buttonDeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCarFromDB(car.getId());
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu_my_car_details);
        bottomNavigationView.setSelectedItemId(R.id.my_car_details);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.my_car_details:
                        Toast.makeText(getApplicationContext(), "Sunteti deja pe aceasta sectiune!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.my_car_docs:
                        Intent intent = new Intent(getApplicationContext(), CarDocsActivity.class);
                        intent.putExtra("CAR_ID", car.getId());
                        startActivity(intent);
                        return true;
                    case R.id.my_car_service_history:
                        Intent intent2 = new Intent(getApplicationContext(), CarServiceHistoryActivity.class);
                        intent2.putExtra("CAR_ID", car.getId());
                        startActivity(intent2);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

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
