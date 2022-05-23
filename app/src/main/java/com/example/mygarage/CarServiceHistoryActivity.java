package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygarage.adapters.MyCarsAdapter;
import com.example.mygarage.adapters.ServiceInfoAdapter;
import com.example.mygarage.models.CarModel;
import com.example.mygarage.models.ServiceHistoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CarServiceHistoryActivity extends AppCompatActivity implements UpdateDialog.UpdateCarListener {

    private String carId;

    RecyclerView recyclerViewServiceInfoCar;
    ServiceInfoAdapter myServiceInfoAdapter;


    Button buttonAddNewServiceInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_service_history);

        carId = getIntent().getStringExtra("CAR_ID");
        DBHelper dbHelper = new DBHelper(CarServiceHistoryActivity.this);
        List<ServiceHistoryModel> carServiceHistory = dbHelper.getServiceHistoryOfCar(carId);
        recyclerViewServiceInfoCar = findViewById(R.id.serviceHistoryCar);
        recyclerViewServiceInfoCar.setHasFixedSize(true);
        recyclerViewServiceInfoCar.setLayoutManager(new LinearLayoutManager(this));



        myServiceInfoAdapter = new ServiceInfoAdapter(this, (ArrayList<ServiceHistoryModel>) carServiceHistory);
        recyclerViewServiceInfoCar.setAdapter(myServiceInfoAdapter);

        buttonAddNewServiceInfo = findViewById(R.id.buttonAddNewServiceInfo);
        buttonAddNewServiceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateDialog dialog = new UpdateDialog();
                Bundle updateDialogBundle = new Bundle();
                updateDialogBundle.putString("type_of_update", "ADD_SERVICE_INFO");
                dialog.setArguments(updateDialogBundle);
                dialog.show(getSupportFragmentManager(), "dialog");

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu_my_car_details);
        bottomNavigationView.setSelectedItemId(R.id.my_car_service_history);

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
                        Intent intent1 = new Intent(getApplicationContext(), CarDocsActivity.class);
                        intent1.putExtra("CAR_ID", carId);
                        startActivity(intent1);
                        return true;
                    case R.id.my_car_service_history:
                        Toast.makeText(getApplicationContext(), "Sunteti deja pe aceasta sectiune!", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

            }

        });

    }

    @Override
    public void saveNewDataForCar(String carOometer, String carColor, String carPrice) {

    }

    @Override
    public void saveNewServiceInfo(String date, String details) {

        DBHelper db = new DBHelper(CarServiceHistoryActivity.this);
        String carId = getIntent().getStringExtra("CAR_ID");
        db.AddNewServiceInfoCar(date, details, carId);

        //refresh the activity
        finish();
        startActivity(getIntent());
    }

    @Override
    public void saveDocsNewEndDate(String newDate, String updateType) {


    }

    @Override
    public void onBackPressed() {

        finish();
        startActivity(new Intent(getApplicationContext(), MyCarsActivity.class));

    }
}
