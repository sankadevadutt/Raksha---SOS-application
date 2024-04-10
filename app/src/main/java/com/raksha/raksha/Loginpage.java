package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Loginpage extends AppCompatActivity {

    TextView tvsignup,tvpassforg;
    EditText etPhoneLgn,etPassLgn;
    Button btnLogin;
    String phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        tvsignup = findViewById(R.id.tvsignup);
        btnLogin = findViewById(R.id.btnLogin);
        tvpassforg = findViewById(R.id.tvpassforg);
        etPhoneLgn = findViewById(R.id.etPhoneLgn);
        etPassLgn = findViewById(R.id.etPassLgn);


        ActivityCompat.requestPermissions(Loginpage.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        tvpassforg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Loginpage.this,_numberentry_forgotpassword.class);
                startActivity(intent);
                finish();
            }
        });

        tvsignup.setOnClickListener(view -> {
            Intent intent = new Intent(Loginpage.this, Signup.class);
            startActivity(intent);
            finish();
            });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etPhoneLgn.getText().toString().trim();
                password = etPassLgn.getText().toString().trim();
                if(phone.isEmpty() && password.isEmpty())
                {
                    etPhoneLgn.setError("Required filed");
                    etPassLgn.setError("Required field");
                }else if(phone.isEmpty()){
                    etPhoneLgn.setError("Required filed");
                }else if(password.isEmpty()){
                    etPassLgn.setError("Required filed");
                }else{
                    isUser();
                }
            }
        });




    }

    private void isUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String pswdfromdb = snapshot.child(phone).child("password").getValue(String.class);

                    if(pswdfromdb.equals(password)){
                        Intent home = new Intent(Loginpage.this,homepage.class);
                        home.putExtra("mobile",phone);
                        startActivity(home);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_SHORT).show();
                        etPassLgn.requestFocus();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Account doesn't exist",Toast.LENGTH_LONG).show();
                    etPhoneLgn.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}