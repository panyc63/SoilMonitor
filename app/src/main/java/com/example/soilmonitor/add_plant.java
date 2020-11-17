package com.example.soilmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soilmonitor.classes.AutoMode1;
import com.example.soilmonitor.classes.AutoMode2;
import com.example.soilmonitor.classes.AutoMode3;
import com.example.soilmonitor.classes.ManualMode;
import com.example.soilmonitor.classes.PlantData;
import com.example.soilmonitor.classes.overwrite_topup;
import com.example.soilmonitor.classes.plantAdd;
import com.example.soilmonitor.classes.temphumid;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class add_plant extends AppCompatActivity {

    //declare
    private static DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private Button Addbtn;
    private EditText PlantName,PlantID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        Toolbar toolbar = findViewById(R.id.toolbar_addplant);
        setSupportActionBar(toolbar);


        PlantName = (EditText) findViewById(R.id.plantname);
        PlantID = (EditText) findViewById(R.id.plantid);
        mAuth = FirebaseAuth.getInstance();


        Addbtn = (Button) findViewById(R.id.plantbtn);

        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = mAuth.getUid();
                String Name = PlantName.getText().toString();
                String ID = PlantID.getText().toString();
                if (ID.isEmpty()) {
                 PlantID.setError("Please set Plant ID");
                 return;
                } else if ( Name.isEmpty())
                {
                    PlantName.setError("Please enter Plant Name");
                    return;
                }
                String Email = mAuth.getCurrentUser().getEmail();
                PlantData pData = new PlantData(false,  0,Name,Email,0,0);
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
                plantAdd ad = new plantAdd(Name,ID);
                mDatabaseRef.child(uid).setValue(ad);

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Plants");

                temphumid th = new temphumid(0,0);
                overwrite_topup topup = new overwrite_topup(false);

                mDatabaseRef.child(ID).setValue(pData);


                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Plants/"+ID+"/Settings");

                ManualMode Mm = new ManualMode(true,0);
                mDatabaseRef.child("ManualMode").setValue(Mm);

                AutoMode1 a1 = new AutoMode1(false,0,0);
                mDatabaseRef.child("AutoMode1").setValue(a1);

                SimpleDateFormat inputformat = new SimpleDateFormat("HH:mm:ss");
                String input = "00:00:00";

                AutoMode2 a2 = new AutoMode2(false,input,0);
                mDatabaseRef.child("AutoMode2").setValue(a2);

                AutoMode3 a3 = new AutoMode3(false,input,0,0);
                mDatabaseRef.child("AutoMode3").setValue(a3);


                Toast.makeText(add_plant.this,"Successfully added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }



    }

