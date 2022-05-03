package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygarage.models.CarModel;
import com.example.mygarage.models.DocumentsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CarDocsActivity extends AppCompatActivity {

    private String carId;
    TextView tv_carDocs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_docs);

        //gaseste documentele masinii dupa id-ul acesteia
        String carId = getIntent().getStringExtra("CAR_ID");

        tv_carDocs = findViewById(R.id.tvCarDocs);
        DBHelper dbHelper = new DBHelper(CarDocsActivity.this);
        DocumentsModel carDocsModel = dbHelper.getDocumentsOfCar(carId);
        tv_carDocs.setText("Itp valabil pana pe data de: " +   carDocsModel.getItp_end_date() + "\n Asigarare valabila pana pe data de: " +
                        carDocsModel.getInsurance_end_date()+ "\n Rovigneta valabila pana pe:" + carDocsModel.getRoad_tax());

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


}
