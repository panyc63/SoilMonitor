package com.example.soilmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.soilmonitor.classes.AutoMode1;
import com.example.soilmonitor.classes.AutoMode2;
import com.example.soilmonitor.classes.AutoMode3;
import com.example.soilmonitor.classes.ManualMode;
import com.example.soilmonitor.classes.PlantData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Plant_Setting extends AppCompatActivity {

    private Switch mode1,mode2,mode3,mode4;
    private static final String TAG = "printtest";
    private Button update;
    private String SensorID;
    private static DatabaseReference DatabaseRef;
    private Toolbar toolbar;
    private int temp,humid,moist;
    private String pName,Email;
    private boolean topping;
    private FirebaseAuth mAuth;
    private ArrayList<PlantData> plantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant__setting);
        mode1 = (Switch) findViewById(R.id.mode1);
        mode2 = (Switch) findViewById(R.id.mode2);
        mode3 = (Switch) findViewById(R.id.mode3);
        mode4 = (Switch) findViewById(R.id.mode4);
        update = (Button) findViewById(R.id.btnUpdate);

        mAuth = FirebaseAuth.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar_psetting);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            SensorID= null;
        } else {
            SensorID= extras.getString("SensorID");
            temp = extras.getInt("Temp");
            humid = extras.getInt("Humid");
            moist = extras.getInt("Moist");
            pName = extras.getString("PlantName");
            Email = mAuth.getCurrentUser().getEmail();
            topping = true;
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Update Button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseRef = FirebaseDatabase.getInstance().getReference("Plants");
                PlantData pData = new PlantData(topping, moist, pName, Email, temp, humid);
                DatabaseRef.child(SensorID).setValue(pData);

                DatabaseRef = FirebaseDatabase.getInstance().getReference("Plants/" + SensorID+"/Settings");
                DatabaseReference pushRef = DatabaseRef.push();
                if (mode1.isChecked()) {

                    EditText topup = (EditText) findViewById(R.id.et_Mode1_topup);
                    int topup_value = Integer.valueOf(topup.getText().toString());

                    //Set true and topup value of topup
                    ManualMode Mm = new ManualMode(true, topup_value);

                    //Set the rest of the mode to off/false
                    AutoMode1 a1 = new AutoMode1(false, 0, 0);
                    AutoMode2 a2 = new AutoMode2(false, "00:00:00", 0);
                    AutoMode3 a3 = new AutoMode3(false, "00:00:00", 0, 0);

                    DatabaseRef.child("ManualMode").setValue(Mm);
                    DatabaseRef.child("AutoMode1").setValue(a1);
                    DatabaseRef.child("AutoMode2").setValue(a2);
                    DatabaseRef.child("AutoMode3").setValue(a3);
                    Log.d(TAG, "Button 1 ");

                } else if (mode2.isChecked())
                {
                    EditText topup,above_level;

                    topup = (EditText) findViewById(R.id.et_Mode2_topup);
                    above_level = (EditText) findViewById(R.id.et_Mode2_above);

                    int topup_value = Integer.valueOf(topup.getText().toString());
                    int above_level_value = Integer.valueOf(above_level.getText().toString());

                    AutoMode1 a1 = new AutoMode1(true, above_level_value, topup_value);

                    //Set the rest of the mode to off/false
                    ManualMode Mm = new ManualMode(false, 0);
                    AutoMode2 a2 = new AutoMode2(false, "00:00:00", 0);
                    AutoMode3 a3 = new AutoMode3(false, "00:00:00", 0, 0);

                    DatabaseRef.child("ManualMode").setValue(Mm);
                    DatabaseRef.child("AutoMode1").setValue(a1);
                    DatabaseRef.child("AutoMode2").setValue(a2);
                    DatabaseRef.child("AutoMode3").setValue(a3);



                } else if (mode3.isChecked())
                {
                    Log.d(TAG, "Button 3 ");

                } else if (mode4.isChecked())
                {
                    Log.d(TAG, "Button 4 ");

                }

                finish();
            }
        });

        //Manual Mode
        mode1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText topup = (EditText) findViewById(R.id.et_Mode1_topup);
                if(isChecked)
                {
                    mode2.setChecked(false);
                    mode3.setChecked(false);
                    mode4.setChecked(false);
                    topup.setEnabled(true);
                } else if (!isChecked)
                {
                    topup.setEnabled(false);
                }
            }
        });
        //Auto Mode 1
        mode2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText topup,below;

                topup = (EditText) findViewById(R.id.et_Mode2_topup);
                below = (EditText) findViewById(R.id.et_Mode2_above);
                if(isChecked)
                {
                    mode1.setChecked(false);
                    mode3.setChecked(false);
                    mode4.setChecked(false);
                    topup.setEnabled(true);
                    below.setEnabled(true);
                } else if (!isChecked)
                {
                    topup.setEnabled(false);
                    below.setEnabled(false);
                }
            }
        });
        //Auto Mode 2 ---> set a time and would water at that specific time
        mode3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText topup,time;

                topup = (EditText) findViewById(R.id.et_Mode3_topup);
                time = (EditText) findViewById(R.id.et_Mode3_time);
                if(isChecked)
                {
                    mode1.setChecked(false);
                    mode2.setChecked(false);
                    mode4.setChecked(false);
                    time.setEnabled(true);
                    topup.setEnabled(true);
                } else if (!isChecked)
                {
                    topup.setEnabled(false);
                    time.setEnabled(false);
                }
            }
        });
        //Auto mode 3 ---> Can set start time and every interval
        mode4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                EditText topup,start,interval;
                topup = (EditText) findViewById(R.id.et_Mode4_topup);
                start = (EditText) findViewById(R.id.et_Mode4_StartTime);
                interval = (EditText) findViewById(R.id.et_Mode4_Interval);
                if(isChecked)
                {
                    mode2.setChecked(false);
                    mode3.setChecked(false);
                    mode1.setChecked(false);
                    topup.setEnabled(true);
                    start.setEnabled(true);
                    interval.setEnabled(true);
                } else if (!isChecked)
                {


                    topup.setEnabled(false);
                    start.setEnabled(false);
                    interval.setEnabled(false);

                }
            }
        });

    }
}
