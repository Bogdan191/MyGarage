package com.example.mygarage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.mygarage.adapters.NewsAdapter;
import com.example.mygarage.models.CarModel;
import com.example.mygarage.models.DocumentsModel;
import com.example.mygarage.models.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ImageButton buttonNotifications;
    private FirebaseAuth mAuth;

    RecyclerView recyclerViewNews;

    DatabaseReference database;

    NewsAdapter newsAdapter;

    ArrayList<News> listNews;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.menu_bottom_home:
                        Toast.makeText(getApplicationContext(), "Esti deja pe pagina cu stiri din doemniul auto", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.menu_bottom_my_cars:
                        startActivity(new Intent(getApplicationContext(), MyCarsActivity.class));
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

            }

        });

        toolbar = findViewById(R.id.toolbar_activity_main);

        setSupportActionBar(toolbar);

        buttonNotifications = findViewById(R.id.buttonNotifications);
        buttonNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pushNotifications();
            }
        });

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
    @Override
    protected void onStart() {

        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            //handle the case when the user is already logged in


            recyclerViewNews = findViewById(R.id.newsList);
            database = FirebaseDatabase.getInstance().getReference("News");
            recyclerViewNews.setHasFixedSize(true);
            recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));

            listNews = new ArrayList<>();
            newsAdapter = new NewsAdapter(this, listNews);

            recyclerViewNews.setAdapter(newsAdapter);

            database.get();

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dS : snapshot.getChildren()){

                        News news = dS.getValue(News.class);
                        listNews.add(news);
                    }
                    newsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            //handle the case when the user is not logged in, this means the app should go to login page
            Toast.makeText(this, "Intra in aplicatie introducand datele contului dvs. Daca nu aveti un cont, va puteti inregistra.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void Logout() {

        try{

            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void pushNotifications() {
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        List<CarModel> cars;
        cars = dbHelper.getCars();

        List<String> messageNotifications = new ArrayList<>();
        for (CarModel c: cars) {
            DocumentsModel docsOfCar = dbHelper.getDocumentsOfCar(c.getId());

            Date endDateITP, endDateInsurance, endDateRoadTax;
            String nameOfCar = c.getMake() + " " + c.getModel();

            try {
                endDateITP = new SimpleDateFormat("dd/MM/yyyy").parse(docsOfCar.getItp_end_date());
                String nowDate =LocalDate.now().toString().replace('-', '/');
                Date now = new SimpleDateFormat("yyyy/MM/dd").parse(nowDate);

                long daysBettween = TimeUnit.DAYS.convert((endDateITP.getTime() - now.getTime()), TimeUnit.MILLISECONDS);
                if(daysBettween <= 7 && daysBettween >= 0) {
                    messageNotifications.add("Itp-ul masinii " +  nameOfCar + " expira in curand, mai exact pe data de " +
                            docsOfCar.getItp_end_date());

                }else if(daysBettween < 0){
                    messageNotifications.add("Itp-ul masinii " +  nameOfCar + " a expirat pe data de " +
                            docsOfCar.getItp_end_date());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                endDateInsurance = new SimpleDateFormat("dd/MM/yyyy").parse(docsOfCar.getInsurance_end_date());
                String nowDate =LocalDate.now().toString().replace('-', '/');
                Date now = new SimpleDateFormat("yyyy/MM/dd").parse(nowDate);;

                long daysBetween = TimeUnit.DAYS.convert(( endDateInsurance.getTime() - now.getTime()), TimeUnit.MILLISECONDS);
                if(daysBetween <= 7 && daysBetween >= 0) {
                    messageNotifications.add("Asigurarea masinii " +  nameOfCar + " expira in curand, mai exact pe data de " +
                            docsOfCar.getInsurance_end_date());

                }else if(daysBetween < 0){
                    messageNotifications.add("Asigurarea masinii " +  nameOfCar + " a expirat pe data de " +
                            docsOfCar.getInsurance_end_date());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                endDateRoadTax = new SimpleDateFormat("dd/MM/yyyy").parse(docsOfCar.getRoad_tax());
                String nowDate = LocalDate.now().toString().replace('-', '/');
                Date now = new SimpleDateFormat("yyyy/MM/dd").parse(nowDate);

                long daysBettween = TimeUnit.DAYS.convert((endDateRoadTax.getTime() - now.getTime()), TimeUnit.MILLISECONDS);
                if(daysBettween <= 7 && daysBettween >= 0) {
                    messageNotifications.add("Rovinieta masinii " +  nameOfCar + " expira in curand, mai exact pe data de " +
                            docsOfCar.getRoad_tax());

                }else if(daysBettween < 0){
                    messageNotifications.add("Itp-ul masinii " +  nameOfCar + " a expirat pe data de " +
                            docsOfCar.getRoad_tax());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        if(messageNotifications.size() == 0) {
            messageNotifications.add("Momentan, nu exista notificari!\n");
        }

        AlertDialog alertDeleteCarDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDeleteCarDialog.setTitle("Notificari documente");

        String notificationBody = "";
        for(String s : messageNotifications) {
            notificationBody = notificationBody.concat(s + "\n");
        }
        alertDeleteCarDialog.setMessage(notificationBody);
        alertDeleteCarDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDeleteCarDialog.show();
    }

    @Override
    public void onBackPressed() {

    }
}