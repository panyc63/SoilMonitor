package com.example.soilmonitor;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btn_Reg;
    private ImageView back;
    private Toolbar toolbar;
    private DatabaseReference mDatabaseRef;
    private EditText Nme, Passw, cfm;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        Nme = this.findViewById(R.id.etEmail);
        Passw = this.findViewById(R.id.etPassw);
        cfm = this.findViewById(R.id.etCfmPwd);
        btn_Reg = this.findViewById(R.id.btnRegi);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");



        toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        setTitle("Register");
        toolbar.setNavigationIcon(R.drawable.ic_logout);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,MainActivity.class));
                finish();
            }
        });
        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regUser();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final String email = Nme.getText().toString().trim();
                final String pass = Passw.getText().toString().trim();
                String uid = mAuth.getCurrentUser().getUid();
                UserData uData = new UserData(email, pass, uid);
                mDatabaseRef.child(uid).setValue(uData);

            }
        };

    }//end of onCreate


    private void regUser() {

        final String email = Nme.getText().toString().trim();
        final String pass = Passw.getText().toString().trim();
        final String confirm = cfm.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Nme.setError("Email is empty");
            return;
        } else if (TextUtils.isEmpty(pass)) {
            Passw.setError("Password is empty");
            return;
        } else if (TextUtils.isEmpty(confirm)) {
            cfm.setError("Confirm Password is empty");
            return;
        } else if (!confirm.equals(pass)) {
            Passw.setError("Password not the same");
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                task.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if (e instanceof FirebaseAuthException) {
                                            String error = ((FirebaseAuthException) e).getErrorCode();
                                            Toast.makeText(Register.this,error, Toast.LENGTH_SHORT).show();
                                            if(error == "ERROR_EMAIL_ALREADY_IN_USE")
                                            {Toast.makeText(Register.this,"Email already existing",Toast.LENGTH_LONG).show();}
                                        }
                                    }
                                });

                                if (null != mAuth.getCurrentUser()) {
                                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    mAuth.signInWithEmailAndPassword(email,pass);

                                    verification();
                                    String uid = mAuth.getCurrentUser().getUid();
                                    UserData uData = new UserData(email, pass, uid);
                                    mDatabaseRef.child(uid).setValue(uData);
                                    mAuth.signOut();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


                    }

                });

    }//end of regUser


    public void verification() {
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Email Verification Sent", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Verification not sent", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }
    public void onBackPressed()
    {
        startActivity(new Intent(Register.this,MainActivity.class));
        finish();
    } // back pressed

}
