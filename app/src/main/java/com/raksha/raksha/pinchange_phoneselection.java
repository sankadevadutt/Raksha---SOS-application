package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class pinchange_phoneselection extends AppCompatActivity {

    Button btnChange,btnCancel2,btnChange2,btnCancel3;
    String phone,password,pin;
    EditText etNewpswd3,etNewpswd2,etpswdcnfrmsn2;

    FirebaseDatabase rootNode;
    DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinchange_phoneselection);

        btnChange = findViewById(R.id.btnChange);
        btnCancel2 = findViewById(R.id.btnCancel2);
        btnChange2 = findViewById(R.id.btnChange2);
        btnCancel3 = findViewById(R.id.btnCancel3);
        etpswdcnfrmsn2 = findViewById(R.id.etpswdcnfrmsn2);
        etNewpswd2 = findViewById(R.id.etNewpswd2);
        etNewpswd3 = findViewById(R.id.etNewpswd3);

        phone = getIntent().getStringExtra("mobile");
        findViewById(R.id.textView41).setVisibility(View.VISIBLE);
        etNewpswd2.setVisibility(View.VISIBLE);
        btnChange2.setVisibility(View.VISIBLE);
        btnCancel3.setVisibility(View.VISIBLE);
        findViewById(R.id.textView43).setVisibility(View.GONE);
        etNewpswd3.setVisibility(View.GONE);
        findViewById(R.id.textView45).setVisibility(View.GONE);
        etpswdcnfrmsn2.setVisibility(View.GONE);
        btnChange.setVisibility(View.GONE);
        btnCancel2.setVisibility(View.GONE);

        btnCancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),profile_selection.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });
        btnCancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),profile_selection.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnChange2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                Query checkuser = reference.orderByChild("phone").equalTo(phone);

                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        password = snapshot.child(phone).child("password").getValue(String.class);
                        if(password.equals(etNewpswd2.getText().toString()))
                        {
                            findViewById(R.id.textView41).setVisibility(View.GONE);
                            etNewpswd2.setVisibility(View.GONE);
                            btnChange2.setVisibility(View.GONE);
                            btnCancel3.setVisibility(View.GONE);
                            findViewById(R.id.textView43).setVisibility(View.VISIBLE);
                            etNewpswd3.setVisibility(View.VISIBLE);
                            findViewById(R.id.textView45).setVisibility(View.VISIBLE);
                            etpswdcnfrmsn2.setVisibility(View.VISIBLE);
                            btnChange.setVisibility(View.VISIBLE);
                            btnCancel2.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(pinchange_phoneselection.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNewpswd3.getText().toString().trim().isEmpty()||etpswdcnfrmsn2.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }else if(etNewpswd3.getText().toString().trim().length() != 4){
                    Toast.makeText(getApplicationContext(),"Pin should be of length 4",Toast.LENGTH_SHORT).show();
                }else if(!etNewpswd3.getText().toString().trim().equals(etpswdcnfrmsn2.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(),"Pin not matched",Toast.LENGTH_SHORT).show();
                }else{
                    pin = etNewpswd3.getText().toString().trim();
                        //storing in database
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference("Users");

                        reference1.child(phone).child("pin").setValue(pin);
                        Toast.makeText(getApplicationContext(),"Pin has been changed successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),profile_selection.class);
                        intent.putExtra("mobile",phone);
                        startActivity(intent);
                        finish();
                    }
            }
        });

    }
}