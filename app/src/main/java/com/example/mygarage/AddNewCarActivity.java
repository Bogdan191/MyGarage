package com.example.mygarage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;

import java.io.ByteArrayOutputStream;


public class AddNewCarActivity extends AppCompatActivity {


    private static final int SELECT_PICTURE = 100;
    Button btn_addCarToDB;
    ImageButton btn_selectCarImage;

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
    private ImageView iv_carImage;
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
                addTheCarInfoToDB();
            }
        });

        btn_selectCarImage = findViewById(R.id.buttonAddCarImage);
        btn_selectCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
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
        iv_carImage = findViewById(R.id.addCarImageView);

    }
    //Adauga in baza de date 'cars.db', informatiile depsre masina, documente si detaliile despre istoric service in tabelele aferente
    private void addTheCarInfoToDB(){

        String carMake, carModel , carEmission, carEngine, carRimSize, carCurrentMarketValue;
        byte[] carImage= new byte[0];
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

        //set carImage var
        Bitmap bitmapCarImage = ((BitmapDrawable) iv_carImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapCarImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        carImage = baos.toByteArray();


        if(carModel.length() == 0 || carMake.length() == 0 || carEmission.length() == 0 || carEngine.length() == 0
                || carRimSize.length() == 0 || carCurrentMarketValue.length() == 0) {
            Toast.makeText(getApplicationContext(), "Te rog completeaza toate campurile!", Toast.LENGTH_SHORT).show();
        } else {

            CarModel newCar;
            try {
                newCar = new CarModel(
                        carMake+carModel+carManufacturedData+"CarID",
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
                        carImage,
                        "-1",
                        "-1"
                );

            }catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Eraore la crearea unei noi masini pentru db!", Toast.LENGTH_SHORT).show();
                newCar = new CarModel(
                        "-1",
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
                        carImage,
                        "0",
                        "0"
                );
            }


            DBHelper dbHelper = new DBHelper(AddNewCarActivity.this);
            boolean addCarSuccess = dbHelper.addCarToDB(newCar);

            if(addCarSuccess) {
                startActivity(new Intent(getApplicationContext(), MyCarsActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Eraore la adaugarea masinii in baza de date. Reincearca! ", Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void chooseImage() {
       Intent intent = new Intent();

       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent, "Selecteaza imaginea cu masina dvs."), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK) {
            if(requestCode == SELECT_PICTURE) {
                Uri selectImageUri = data.getData();
                if(selectImageUri != null) {
                    iv_carImage.setImageURI(selectImageUri);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
