package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;
import com.example.mygarage.models.DocumentsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CarDocsActivity extends AppCompatActivity implements UpdateDialog.UpdateCarListener {

    private String carId;
    TextView tv_carDocs, tv_carITP, tv_carInsurance, tv_carRoadTax;
    Button buttonChangeITPEndDate, buttonChangeInsuranceEndDate, buttonChangeRoadTaxEndDate, buttonUpdateCarDocs;
    LinearLayout layout_carDocs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_docs);

        //gaseste documentele masinii dupa id-ul acesteia
        String carId = getIntent().getStringExtra("CAR_ID");
        DBHelper dbHelper = new DBHelper(CarDocsActivity.this);
        DocumentsModel carDocsModel = dbHelper.getDocumentsOfCar(carId);

        layout_carDocs = findViewById(R.id.layoutcarDocs);

        tv_carDocs = findViewById(R.id.tvCarDocs);
        tv_carITP = findViewById(R.id.tvCarITPEndDate);
        tv_carInsurance = findViewById(R.id.tvCarInsuranceEndDate);
        tv_carRoadTax = findViewById(R.id.tvCarRoadTaxEndDate);

        buttonChangeITPEndDate = findViewById(R.id.buttonChangeITPEndDate);
        buttonChangeInsuranceEndDate = findViewById(R.id.buttonChangeInsuranceEndDate);
        buttonChangeRoadTaxEndDate = findViewById(R.id.buttonChangeRoadTaxEndDate);

        buttonUpdateCarDocs = findViewById(R.id.buttonUpdateCarDocs);
        buttonUpdateCarDocs.setVisibility(View.INVISIBLE);

        tv_carITP.setText("     Inspectia tehnica pentru aceasta masina este valabila pana pe data de: " + carDocsModel.getItp_end_date());
        tv_carInsurance.setText("       Asigurarea acestei masini este valabila pana pe data de: " + carDocsModel.getInsurance_end_date());
        tv_carRoadTax.setText("     Taxa de drum pentru aceasta masina este valabila pana la data de: " + carDocsModel.getRoad_tax());


        //set onclick event pe butoane
        buttonChangeITPEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    openCarUpdateDocsDialog("UPDATE_ITP");

            }
        });

        buttonChangeInsuranceEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarUpdateDocsDialog("UPDATE_INSURANCE");
            }
        });
        buttonChangeRoadTaxEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarUpdateDocsDialog("UPDATE_ROAD_TAX");
            }
        });


        // bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu_my_car_details);
        bottomNavigationView.setSelectedItemId(R.id.my_car_docs);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.my_car_details:
                        Intent intent = new Intent(getApplicationContext(), CarRUDActivity.class);
                        intent.putExtra("CAR_ID", carId);
                        startActivity(intent);
                        return true;
                    case R.id.my_car_docs:
                        Toast.makeText(getApplicationContext(), "Sunteti deja pe aceasta sectiune!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.my_car_service_history:
                        Intent intentServiceActivity = new Intent(getApplicationContext(), CarServiceHistoryActivity.class);
                        intentServiceActivity.putExtra("CAR_ID", carId);
                        startActivity(intentServiceActivity);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

            }

        });
    }

    public void openCarUpdateDocsDialog(String updateType) {

        UpdateDialog dialog = new UpdateDialog();
        Bundle updateDialogBundle = new Bundle();
        updateDialogBundle.putString("type_of_update", updateType);
        dialog.setArguments(updateDialogBundle);
        dialog.show(getSupportFragmentManager(), "dialog");


    }

    @Override
    public void saveNewDataForCar(String carOometer, String carColor) {

    }

    @Override
    public void saveNewServiceInfo(String date, String details) {

    }

    @Override
    public void saveDocsNewEndDate(String newDate, String updateType) {

        DBHelper db = new DBHelper(CarDocsActivity.this);

        String carId = getIntent().getStringExtra("CAR_ID");

        db.updateCarDocs(updateType, carId, newDate);

        Toast.makeText(getApplicationContext(), "Noua data  " + updateType + ": " + newDate, Toast.LENGTH_SHORT).show();

        //refresh
        finish();
        startActivity(getIntent());

    }


}
