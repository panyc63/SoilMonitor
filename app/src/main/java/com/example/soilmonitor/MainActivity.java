package com.example.soilmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btn_login,btnForget;
    private TextView Reg;
    private EditText Nme,Passw;
    private CheckBox saveRem;
    private SharedPreferences loginPref;
    private SharedPreferences.Editor loginPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        Nme = (EditText) findViewById(R.id.etName);
        Passw = (EditText) findViewById(R.id.etPass);

        Reg = (TextView) findViewById(R.id.tvReg);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btnForget = (Button) findViewById(R.id.forgetbtn);

        saveRem = (CheckBox) findViewById(R.id.cbRemember);
        loginPref = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();

        saveRem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(saveRem.isChecked())
                {
                    Nme.setText(loginPref.getString("username",""));
                    Passw.setText(loginPref.getString("password",""));
                    saveRem.setChecked(true);

                } else if (!saveRem.isChecked())
                {
                    Nme.setText("");
                    Passw.setText("");
                    saveRem.setChecked(false);
                }
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgetPassword.class));

            }
        });

    }//end of oncreate
    public void startSignIn() {
        final String nme = Nme.getText().toString();
        final String pass = Passw.getText().toString();

        if (nme.isEmpty())
        {
            Nme.setError("Please enter email");
            Nme.requestFocus();
        } else if ( pass.isEmpty())
        {
            Passw.setError("Please enter password");
            Passw.requestFocus();
        } else {

            mAuth.signInWithEmailAndPassword(nme, pass)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sign In Problem", Toast.LENGTH_SHORT).show();
                            } else  if(!mAuth.getCurrentUser().isEmailVerified())
                            {
                                Toast.makeText(MainActivity.this,"Email not verified",Toast.LENGTH_SHORT).show();
                            } else
                            {
                                Toast.makeText(MainActivity.this,"Login Successfully, Welcome Back!",Toast.LENGTH_SHORT).show();
                                if (saveRem .isChecked()) {
                                    loginPrefEditor.putBoolean("saveLogin", true);
                                    loginPrefEditor.putString("username", nme);
                                    loginPrefEditor.putString("password", pass);
                                    loginPrefEditor.apply();
                                } else {
                                    loginPrefEditor.clear();
                                    loginPrefEditor.apply();
                                }
                                Intent intent = new Intent(MainActivity.this,menu_layout.class );
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }

    }
}
