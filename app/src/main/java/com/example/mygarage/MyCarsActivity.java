package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygarage.adapters.MyCarsAdapter;
import com.example.mygarage.models.CarModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MyCarsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button addNewCar;

    RecyclerView recyclerViewMyCars;
    List<CarModel> cars;
    MyCarsAdapter myCarsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_cars);

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_my_cars);

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.menu_bottom_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.menu_bottom_my_cars:
                        Toast.makeText(getApplicationContext(), "Esti deja pe pagina 'Masinile mele' ", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

            }

        });

        toolbar = findViewById(R.id.toolbar_activity_my_cars);
        setSupportActionBar(toolbar);

        addNewCar = findViewById(R.id.addNewCar);
        addNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewCarActivity.class));
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        recyclerViewMyCars = findViewById(R.id.myCarsList);
        recyclerViewMyCars.setHasFixedSize(true);
        recyclerViewMyCars.setLayoutManager(new LinearLayoutManager(this));

        //show the cars from DB my_cars
        DBHelper dbHelper = new DBHelper(MyCarsActivity.this);
        cars = dbHelper.getCars();

        myCarsAdapter = new MyCarsAdapter(this, (ArrayList<CarModel>) cars);
        recyclerViewMyCars.setAdapter(myCarsAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.side_menu_settings:
                Toast.makeText(this, "Ai ales sa mergi spre setari! Momentan, acest serviciu, nu este disponibil!", Toast.LENGTH_LONG).show();
                break;
            case R.id.side_menu_log_out:
                Logout();
                break;
            default:
                break;
        }
        return true;
    }

    private void Logout() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        try{
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
