package com.example.mygarage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button logOutButton;
    private FirebaseAuth mAuth;

    RecyclerView recyclerViewNews;

    DatabaseReference database;

    NewsAdapter newsAdapter;

    ArrayList<News> listNews;


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
                        Toast.makeText(getApplicationContext(), "Momentan, aceasta sectiune este in dezvoltare!", Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, "Please login in the Application with your account", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void goToLoginActivity(){
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


}