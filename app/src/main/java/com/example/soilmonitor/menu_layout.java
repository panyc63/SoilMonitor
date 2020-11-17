package com.example.soilmonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soilmonitor.classes.PlantData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class menu_layout extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, plants_adapter.OnItemClickListener {

    private NavigationView int1;
    private static final String TAG = "error print";
    private plants_adapter.OnItemClickListener int2;
    private FirebaseAuth mAuth;
    private TextView email;
    private ArrayList<PlantData> plantsList;
    private DatabaseReference databaseRef;
    private RecyclerView recyclerView;
    private plants_adapter adapter;
    private List<String> SensorID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_layout);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("Plants");
        Toolbar toolbar = findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.menu_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);
        setTitle("Home Page");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        email = headerView.findViewById(R.id.tvEmail);
        email.setText(mAuth.getCurrentUser().getEmail());




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }//on create

    @Override
    protected void onStart() {
        super.onStart();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantsList = new ArrayList<PlantData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    PlantData p = dataSnapshot1.getValue(PlantData.class);
                    if (p.getOwner_Email().equals(mAuth.getCurrentUser().getEmail())) {
                        plantsList.add(p);
                        String name = dataSnapshot1.getKey().toString();
                        SensorID.add(name);
                        try {
                            TextView humidity,temperature;

                            humidity = (TextView) findViewById(R.id.humid_numb);
                            temperature = (TextView) findViewById(R.id.temp_numb);
                            humidity.setText(String.valueOf(plantsList.get(0).getHumidity()));
                            temperature.setText(String.valueOf(plantsList.get(0).getTemperature()));
                        }catch (Exception e) {
                            Log.d(TAG, e.toString());
                        }
                    }
                }
                adapter = new plants_adapter(menu_layout.this,plantsList,int2);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(menu_layout.this,Setting.class);
            startActivity(intent);
        } else if (id == R.id.action_add) {
            startActivity(new Intent(menu_layout.this,add_plant.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            // Handle the camera action

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            Intent intent = new Intent(menu_layout.this , MainActivity.class);
            startActivity(intent);
            finish();
        /*
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
*/
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    } // on back pressed

    private void Intent(Context c, Class cl, String S_ID)
    {
        android.content.Intent intent = new Intent(c,cl);
        intent.putExtra("SensorID",S_ID);
        c.startActivity(intent);
    }


    @Override
    public void onItemClick(int Position) {
        String Sensor_ID = SensorID.get(Position);
        Intent(getApplication(), Plant_Setting.class, Sensor_ID);
    }
}//end of class
