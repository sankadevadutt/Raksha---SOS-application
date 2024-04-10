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

public class ContactDisplay extends AppCompatActivity {

    TextView tvname,tvphone,tvreln;
    Button btnback,btnedit;
    String phone,name,contact,reln,phonec;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display);


        phone = getIntent().getExtras().getString("mobile");
        contact = getIntent().getExtras().getString("contact");

        tvname = findViewById(R.id.tvName);
        tvphone = findViewById(R.id.tvphone);
        tvreln = findViewById(R.id.tvreln);
        btnback = findViewById(R.id.btnback5);
        btnedit = findViewById(R.id.btnedit);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phonec = snapshot.child(phone).child("Contacts").child(contact).child("phonec").getValue(String.class);
                name = snapshot.child(phone).child("Contacts").child(contact).child("name").getValue(String.class);
                reln = snapshot.child(phone).child("Contacts").child(contact).child("reln").getValue(String.class);
                tvname.setText(name);
                tvphone.setText(phonec);
                tvreln.setText(reln);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(),homepage.class);
                home.putExtra("mobile",phone);
                startActivity(home);
                finish();
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("contact",contact);
                Intent intent = new Intent(getApplicationContext(),editcontact.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }
}