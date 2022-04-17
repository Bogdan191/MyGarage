package com.example.mygarage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class AddNewCarActivity extends AppCompatActivity {


    Button btn_addCarToDB;

    //Car fields
    private EditText et_make;
    private EditText et_model;
    private DatePicker manufactured_data;
    private EditText et_emission_standard;
    private EditText et_engine_capacity;
    private EditText et_horse_power;
    private EditText et_rim_size;
    private EditText et_current_market_value;
    private EditText et_odometer;
    private CheckBox manual_gearbox_checkbox;
    private EditText et_documents_id;
    private EditText et_service_history_id;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_car);

        btn_addCarToDB = findViewById(R.id.addCarToDB);
        btn_addCarToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTheCarToDB();
            }
        });

        //adauga referinte catre campurile care contin date despre masina care trebuie adaugata in baza de date

        et_make = findViewById(R.id.editTextAddCarMark);
        et_model = findViewById(R.id.editTextAddCarModel);
        manufactured_data = findViewById(R.id.datePickerAddCarManufacturedData);
        et_emission_standard = findViewById(R.id.editTextAddCarEmissionStandard);
        et_engine_capacity = findViewById(R.id.editTextAddCarEngineCapacity);
        et_horse_power = findViewById(R.id.editTextAddCarHP);
        et_rim_size = findViewById(R.id.editTextAddCarRimSize);
        et_current_market_value = findViewById(R.id.editTextAddCarCurrMarketValue);
        et_odometer = findViewById(R.id.editTextAddCarOdometer);
        manual_gearbox_checkbox = findViewById(R.id.checkboxAddCarManualGearbox);



    }

    private void addTheCarToDB(){

        String carMake, carModel , carEmission, carEngine, carRimSize, carCurrentMarketValue;
        int carHP, carOdometer;
        boolean carHasManualGearbox;
        String carManufacturedData;

        //in cazul in care caii putere si rulajul nu sunt definite, seteaza textul la -1 pentru a evita anumite erori
        if(et_horse_power.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Te rog defineste caii putere", Toast.LENGTH_SHORT).show();
            et_horse_power.setText("-1");
        }
        if(et_odometer.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Te rog introduce rulajul masinii", Toast.LENGTH_SHORT).show();
            et_odometer.setText("-1");
        }


        carMake = et_make.getText().toString();
        carModel = et_model.getText().toString();
        carManufacturedData = manufactured_data.getDayOfMonth() + "/" + manufactured_data.getMonth() + "/" + manufactured_data.getYear();
        carEmission = et_emission_standard.getText().toString();
        carEngine = et_engine_capacity.getText().toString();
        carHP = Integer.parseInt(et_horse_power.getText().toString());
        carRimSize = et_rim_size.getText().toString();
        carCurrentMarketValue = et_current_market_value.getText().toString();
        carOdometer = Integer.parseInt(et_odometer.getText().toString());
        carHasManualGearbox = manual_gearbox_checkbox.isChecked();


        if(carModel.length() == 0 || carMake.length() == 0 || carEmission.length() == 0 || carEngine.length() == 0
                || carRimSize.length() == 0 || carCurrentMarketValue.length() == 0) {
            Toast.makeText(getApplicationContext(), "Te rog completeaza toate campurile!", Toast.LENGTH_SHORT).show();
        }
        else {
            CarModel newCar;

            try {
                newCar = new CarModel(
                        -1,
                        carMake,
                        carModel,
                        carManufacturedData,
                        carEmission,
                        carEngine,
                        carHP,
                        carRimSize,
                        carCurrentMarketValue,
                        carOdometer,
                        carHasManualGearbox,
                        -1,
                        -1
                );

            }catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Eraore la crearea unei noi masini pentru db!", Toast.LENGTH_SHORT).show();
                newCar = new CarModel(
                        -1,
                        "error",
                        "error",
                        "error",
                        "error",
                        "error",
                        0,
                        "error",
                        "error",
                        0,
                        false,
                        -1,
                        -1
                );
            }


            DBHelper dbHelper = new DBHelper(AddNewCarActivity.this);
            boolean addCarSuccess = dbHelper.addCarToDB(newCar);
            Toast.makeText(getApplicationContext(), "Success " + addCarSuccess, Toast.LENGTH_SHORT).show();
        }



    }

}
