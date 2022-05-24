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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;
import com.example.mygarage.models.DocumentsModel;
import com.example.mygarage.models.ServiceHistoryModel;

import java.io.ByteArrayOutputStream;


public class AddNewCarActivity extends AppCompatActivity {


    private static final int SELECT_PICTURE = 100;
    Button btn_addCarToDB;
    ImageButton btn_selectCarImage;

    //Campurile cu date despre masina
    private EditText et_make;
    private EditText et_model;
    private EditText et_color;
    private DatePicker manufactured_data;
    private EditText et_emission_standard;
    private EditText et_engine_capacity;
    private Spinner spinner_fuel_type;
    private EditText et_horse_power;
    private EditText et_rim_size;
    private EditText et_current_market_value;
    private EditText et_odometer;
    private CheckBox manual_gearbox_checkbox;
    private ImageView iv_carImage;

    //Campurile cu date despre documentele masinii
    private DatePicker itp_end_date;
    private DatePicker insurance_end_date;
    private DatePicker road_tax;

    //Campurile care contin date despre istoricul service
    private DatePicker service_date;
    private EditText et_service_details;
    private Spinner spinner_type_service_intervention;

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
        et_color = findViewById(R.id.editTextAddCarColor);
        manufactured_data = findViewById(R.id.datePickerAddCarManufacturedData);
        et_emission_standard = findViewById(R.id.editTextAddCarEmissionStandard);
        et_engine_capacity = findViewById(R.id.editTextAddCarEngineCapacity);
        //et_fuel_type = findViewById(R.id.editTextAddCarFuelType);
        spinner_fuel_type = findViewById(R.id.spinnerChooseCarFuelType);
        et_horse_power = findViewById(R.id.editTextAddCarHP);
        et_rim_size = findViewById(R.id.editTextAddCarRimSize);
        et_current_market_value = findViewById(R.id.editTextAddCarCurrMarketValue);
        et_odometer = findViewById(R.id.editTextAddCarOdometer);
        manual_gearbox_checkbox = findViewById(R.id.checkboxAddCarManualGearbox);
        iv_carImage = findViewById(R.id.addCarImageView);

        // adauga referinte catre campurile care contin date despre documentele aferente masinii
        itp_end_date = findViewById(R.id.datePickerAddCarItpEndDate);
        insurance_end_date = findViewById(R.id.datePickerAddCarInsuranceEndDate);
        road_tax = findViewById(R.id.datePickerAddCarRoadTaxEndDate);

        // adauga referinte catre campurile care contin date despre istoricul service
        service_date = findViewById(R.id.datePickerAddCarServiceDate);
        et_service_details = findViewById(R.id.editTextAddCarServiceDetails);
        spinner_type_service_intervention = findViewById(R.id.spinnerChooseTypeOfServiceWork);


    }
    //Adauga in baza de date 'cars.db', informatiile depsre masina, documente si detaliile despre istoric service in tabelele aferente
    private void addTheCarInfoToDB(){

        String carMake, carModel, carColor, carEmission, carEngine, carFuelType, carRimSize, carCurrentMarketValue;
        byte[] carImage= new byte[0];
        int carHP, carOdometer;
        boolean carHasManualGearbox;
        String carManufacturedData;

        carMake = et_make.getText().toString();
        if(carMake.isEmpty()) {
            et_make.setError("Completati marca masinii!");
            et_make.requestFocus();
            return;
        }

        carModel = et_model.getText().toString();
        if(carModel.isEmpty()) {
            et_model.setError("Completati modelul masinii!");
            et_model.requestFocus();
            return;
        }

        carColor = et_color.getText().toString();
        carManufacturedData = manufactured_data.getDayOfMonth() + "/" + manufactured_data.getMonth() + "/" + manufactured_data.getYear();
        carEmission = et_emission_standard.getText().toString();
        if(carEmission.isEmpty()) {
            et_emission_standard.setError("Completati in mod corect acest camp! (Ex: Euro 5, EURO 5)");
            et_emission_standard.requestFocus();
            return;
        }
        carEngine = et_engine_capacity.getText().toString();
        if(carEngine.isEmpty()) {
            et_engine_capacity.setError("Completati acest camp cu capacitatea motorului masinii!");
            et_engine_capacity.requestFocus();
            return;
        }

        carFuelType = spinner_fuel_type.getSelectedItem().toString();
        if(carFuelType.equals("-")){
            spinner_fuel_type.requestFocus();
            Toast.makeText(getApplicationContext(), "Alegeti tipul de combustibil!", Toast.LENGTH_SHORT).show();
            return;
        }

        carRimSize = et_rim_size.getText().toString();
        if(carRimSize.isEmpty() || !carRimSize.matches("^\\d+\\/\\d+\\/\\s?R\\d+")) {
                et_rim_size.setError("Scrieti o valoare corecta pentru dimensiunea rotilor!");
                et_rim_size.requestFocus();
                return;
        }

        carCurrentMarketValue = et_current_market_value.getText().toString();
        if(carCurrentMarketValue.isEmpty()) {
            et_current_market_value.setError("Scrieti ce valoare credeti ca are masina acum.");
            et_current_market_value.requestFocus();
            return;
        }

        carHasManualGearbox = manual_gearbox_checkbox.isChecked();

        try {
            carOdometer = Integer.parseInt(et_odometer.getText().toString());

        }catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Te rog introduce rulajul masinii(valoare numerica)", Toast.LENGTH_SHORT).show();
            et_odometer.requestFocus();
            return;
        }
        try {
            carHP = Integer.parseInt(et_horse_power.getText().toString());

        } catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Te rog defineste caii putere(valoare numerica)", Toast.LENGTH_SHORT).show();
            et_horse_power.requestFocus();
            return;
        }

        //seteaza imaginea masinii in model
        if(iv_carImage.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Alegeti o imagine pentru masina dvs.", Toast.LENGTH_SHORT).show();
            return;
        }


        Bitmap bitmapCarImage = ((BitmapDrawable) iv_carImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapCarImage.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        carImage = baos.toByteArray();


        //obitne datele despre documente din UI pentru a putea fi salvate
        String docITP, docInsurance, docRoadTax;
        docITP = itp_end_date.getDayOfMonth() + "/" + itp_end_date.getMonth() + "/" + itp_end_date.getYear();
        docInsurance = insurance_end_date.getDayOfMonth() + "/" + (insurance_end_date.getMonth() + 1) + "/" + insurance_end_date.getYear();
        docRoadTax = road_tax.getDayOfMonth() + "/" + (road_tax.getMonth() + 1) + "/" + road_tax.getYear();

        //obtine datele despre istoricul service
        String serviceDate;
        String serviceDetails, serviceTypeIntervention;
        serviceDate = service_date.getDayOfMonth() + "/" + (service_date.getMonth() + 1) + "/" + service_date.getYear();
        serviceDetails = et_service_details.getText().toString();

        serviceTypeIntervention = spinner_type_service_intervention.getSelectedItem().toString();

        CarModel newCar;
        DocumentsModel documents;
        ServiceHistoryModel serviceHistory = null;
        try {
            newCar = new CarModel(
                        carMake+carModel+carManufacturedData+"CarID",
                        carMake,
                        carModel,
                        carColor,
                        carManufacturedData,
                        carEmission,
                        carEngine,
                        carFuelType,
                        carHP,
                        carRimSize,
                        carCurrentMarketValue,
                        carOdometer,
                        carHasManualGearbox,
                        carImage,
                        carMake + carModel + carManufacturedData + "DocID",
                        carMake+carModel+carManufacturedData+"CarIDserviceHistory"
                );
                documents = new DocumentsModel(
                        carMake + carModel + carManufacturedData + "DocID",
                        docITP,
                        docInsurance,
                        docRoadTax,
                        carMake + carModel + carManufacturedData+"CarID"
                );
                if(!serviceTypeIntervention.equals("N/A")) {
                    serviceHistory = new ServiceHistoryModel(
                            carMake+carModel+carManufacturedData+"CarIDserviceHistory",
                            serviceDate,
                            serviceTypeIntervention,
                            serviceDetails,
                            carMake+carModel+carManufacturedData+"CarID"

                    );
                }
        }catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Eraore la crearea unei noi masini pentru db!", Toast.LENGTH_SHORT).show();
                newCar = new CarModel(
                        "-1",
                        "error",
                        "error",
                        "error",
                        "erorr",
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
                documents = new DocumentsModel("0",
                        "0",
                        "0",
                        "0",
                        "0");
                serviceHistory = new ServiceHistoryModel("0",
                        "0",
                        "0",
                        "0",
                        "-1");
        }

        DBHelper dbHelper = new DBHelper(AddNewCarActivity.this);
        boolean addCarSuccess = dbHelper.addCarToDB(newCar);
        dbHelper = new DBHelper(AddNewCarActivity.this);
        boolean addDocuments = dbHelper.addDocsToDB(documents);

        dbHelper = new DBHelper(AddNewCarActivity.this);
        boolean addServiceHistory = dbHelper.addServiceHistoryToDB(serviceHistory);
        if(addCarSuccess && addDocuments && addServiceHistory) {
            startActivity(new Intent(getApplicationContext(), CarRUDActivity.class));
            Toast.makeText(getApplicationContext(), "Success " + addCarSuccess + " " +  addDocuments + " " + addServiceHistory, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Eraore la salvarea datelor in baza de date. Va rugam, reincercati! ", Toast.LENGTH_SHORT).show();
            return;
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
                    iv_carImage.setBackground(null);
                    iv_carImage.setImageURI(selectImageUri);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
