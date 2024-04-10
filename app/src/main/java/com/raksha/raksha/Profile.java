package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    Button btnback;
    TextView tvusername,tvuseraddress,tvuserbg,tvallergies,tvmedications,tvorgan,tvusermail,tvuserphone;
    String phone,name,address,bg,allergies,medications,od,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        phone = getIntent().getStringExtra("mobile");

        tvallergies = findViewById(R.id.tvallergies);
        tvusername = findViewById(R.id.tvusername);
        btnback = findViewById(R.id.btnback6);
        tvuseraddress = findViewById(R.id.tvuseraddress);
        tvuserbg = findViewById(R.id.tvuserbg);
        tvmedications = findViewById(R.id.tvmedications);
        tvorgan = findViewById(R.id.tvorgan);
        tvusermail = findViewById(R.id.tvusermail);
        tvuserphone = findViewById(R.id.tvuserphone);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mail = snapshot.child(phone).child("mail").getValue(String.class);
                allergies = snapshot.child(phone).child("allergies").getValue(String.class);
                bg = snapshot.child(phone).child("bg").getValue(String.class);
                name = snapshot.child(phone).child("name").getValue(String.class);
                od = snapshot.child(phone).child("od").getValue(String.class);
                address = snapshot.child(phone).child("address").getValue(String.class);
                medications = snapshot.child(phone).child("medications").getValue(String.class);

                tvallergies.setText(allergies);
                tvmedications.setText(medications);
                tvuseraddress.setText(address);
                tvorgan.setText(od);
                tvuserphone.setText(phone);
                tvusername.setText(name);
                tvusermail.setText(mail);
                tvuserbg.setText(bg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),profile_selection.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });
    }
}