package com.example.mygarage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CarRUDActivity extends AppCompatActivity implements UpdateDialog.ExampleDialogListener {

    TextView textViewCarName;
    TextView tvCarManufacturedData;
    TextView tvCarOdometer, tvCarEngine, tvCarHP, tvCarEmission, tvCarRimSize, tvCarGearbox, tvCarMarketValue;

    ImageView ivCarDetails;
    ImageButton buttonDeleteCar;
    ImageButton buttonEditCar;

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

        buttonEditCar = findViewById(R.id.buttonEditCarFromDB);
        buttonDeleteCar = findViewById(R.id.buttonDeleteCarFromDB);

        //afiseaza datele masinii in campurile aferente
        textViewCarName.setText(new StringBuilder().append(car.getMake()).append(" ").append(car.getModel()).toString());
        ivCarDetails.setImageBitmap(Utils.getImage(car.getCarImage()));
        tvCarManufacturedData.setText(car.getManufactured_date());
        tvCarOdometer.setText(car.getOdometer()+" km");
        tvCarEngine.setText(car.getEngine_capacity());
        tvCarHP.setText(car.getHorse_power() + " CP");
        tvCarEmission.setText(car.getEmission_standard());
        tvCarRimSize.setText(car.getRim_size());
        tvCarGearbox.setText(car.isManual_gearbox()  == true ? "Cutie de viteze manuala" : "Cutie de viteze automata");
        tvCarMarketValue.setText("Valoarea curenta a masinii: " + car.getCurrent_market_value() + "€");

        buttonEditCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeDialogForEditCar(car.getId());
            }
        });
        buttonDeleteCar.setOnClickListener(v -> deleteCarFromDB(car.getId()));

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
    //TODO: implementeaza functia de deschis dialogul pentru editarea masinii si salvarea in baza de date dupa ce a fost creat dialogul
    private void seeDialogForEditCar(String carId){
            openCarEditDialog(carId);
    }
    private void deleteCarFromDB(String carId) {

        DBHelper dbHelper = new DBHelper(CarRUDActivity.this);
        CarModel carModel = dbHelper.getCarById(carId);
        AlertDialog alertDeleteCarDialog = new AlertDialog.Builder(CarRUDActivity.this).create();

        alertDeleteCarDialog.setTitle("Atentie!");
        alertDeleteCarDialog.setMessage("Sunteti sigur ca vreti sa stergeti aceasta masina, " + carModel.getMake() + " "
              + carModel.getModel()  + ", din lista?");
        alertDeleteCarDialog.setButton(AlertDialog.BUTTON_POSITIVE, "NU",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDeleteCarDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DA",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper1 = new DBHelper(CarRUDActivity.this);
                        dbHelper1 = new DBHelper(CarRUDActivity.this);
                        boolean successOnDeleteCar = dbHelper1.deleteCar(carId);
                        if(successOnDeleteCar) {
                            startActivity(new Intent(getApplicationContext(), MyCarsActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "Eoare! Masina nu a putut fi stearsa! Te rugam incarca din nou.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDeleteCarDialog.show();
    }

    public void openCarEditDialog(String carId) {

        UpdateDialog dialog = new UpdateDialog();
        dialog.show(getSupportFragmentManager(), "dialog");


    }

    @Override
    public void applyTexts(String username, String password) {

    }
}
