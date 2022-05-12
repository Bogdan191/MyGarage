package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.ServiceHistoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CarServiceHistoryActivity extends AppCompatActivity {

    private String carId;
    private TextView tv_car_service_history;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_service_history);

        carId = getIntent().getStringExtra("CAR_ID");
        DBHelper dbHelper = new DBHelper(CarServiceHistoryActivity.this);
        ServiceHistoryModel carServiceHistory = dbHelper.getServiceHistoryOfCar(carId);

        tv_car_service_history = findViewById(R.id.tv_car_service_history);
        tv_car_service_history.setText("Data: " + carServiceHistory.getService_made_date() + "\n\n " + carServiceHistory.getDetails());


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
}
