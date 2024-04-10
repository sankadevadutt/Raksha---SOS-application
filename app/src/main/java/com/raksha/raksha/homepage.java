package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class homepage extends AppCompatActivity {

    Button btncontact1,btncontact2,btncontact3,btncontact4,btncontact5,btnsos;
    ImageView profile,addcontact;
    TextView t2,t3,t4;
    int count ,count_int;
    String phone ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        addcontact = findViewById(R.id.addcontact);
        profile = findViewById(R.id.profile);
        btncontact1 = findViewById(R.id.btncontact1);
        btncontact2 = findViewById(R.id.btncontact2);
        btncontact3 = findViewById(R.id.btncontact3);
        btncontact4 = findViewById(R.id.btncontact4);
        btncontact5 = findViewById(R.id.btncontact5);
        btnsos = findViewById(R.id.btnsos);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);

        phone = getIntent().getStringExtra("mobile");

        ActivityCompat.requestPermissions(homepage.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = snapshot.child(phone).child("count").getValue(int.class);
                count_int = count;
                if(count == 0)
                {
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                    btnsos.setVisibility(View.INVISIBLE);
                    btncontact1.setVisibility(View.GONE);
                    btncontact2.setVisibility(View.GONE);
                    btncontact3.setVisibility(View.GONE);
                    btncontact4.setVisibility(View.GONE);
                    btncontact5.setVisibility(View.GONE);
                }else{
                    t2.setVisibility(View.GONE);
                    t3.setVisibility(View.GONE);
                    t4.setVisibility(View.GONE);
                    btnsos.setVisibility(View.VISIBLE);
                }
                if(count == 1){
                    btncontact1.setVisibility(View.VISIBLE);
                    btncontact2.setVisibility(View.GONE);
                    btncontact3.setVisibility(View.GONE);
                    btncontact4.setVisibility(View.GONE);
                    btncontact5.setVisibility(View.GONE);
                }else if (count == 2){
                    btncontact1.setVisibility(View.VISIBLE);
                    btncontact2.setVisibility(View.VISIBLE);
                    btncontact3.setVisibility(View.GONE);
                    btncontact4.setVisibility(View.GONE);
                    btncontact5.setVisibility(View.GONE);
                }else if(count == 3){
                    btncontact1.setVisibility(View.VISIBLE);
                    btncontact2.setVisibility(View.VISIBLE);
                    btncontact3.setVisibility(View.VISIBLE);
                    btncontact4.setVisibility(View.GONE);
                    btncontact5.setVisibility(View.GONE);
                }else if(count == 4){
                    btncontact1.setVisibility(View.VISIBLE);
                    btncontact2.setVisibility(View.VISIBLE);
                    btncontact3.setVisibility(View.VISIBLE);
                    btncontact4.setVisibility(View.VISIBLE);
                    btncontact5.setVisibility(View.GONE);
                }else if(count == 5){
                    btncontact1.setVisibility(View.VISIBLE);
                    btncontact2.setVisibility(View.VISIBLE);
                    btncontact3.setVisibility(View.VISIBLE);
                    btncontact4.setVisibility(View.VISIBLE);
                    btncontact5.setVisibility(View.VISIBLE);
                    addcontact.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                Intent intent = new Intent(getApplicationContext(),AddContact.class);
                b.putString("mobile",phone);
                b.putInt("count",count_int);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }

        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),profile_selection.class);
                intent1.putExtra("mobile",phone);
                startActivity(intent1);
                finish();
            }
        });

        btnsos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),notificationsendrequest.class);
                intent2.putExtra("mobile",phone);
                startActivity(intent2);
                finish();
            }
        });

        btncontact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("contact","contact1");
                Intent intent2 = new Intent(getApplicationContext(),ContactDisplay.class);
                intent2.putExtras(b);
                startActivity(intent2);
                finish();
            }
        });

        btncontact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("contact","contact2");
                Intent intent2 = new Intent(getApplicationContext(),ContactDisplay.class);
                intent2.putExtras(b);
                startActivity(intent2);
                finish();
            }
        });

        btncontact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("contact","contact3");
                Intent intent2 = new Intent(getApplicationContext(),ContactDisplay.class);
                intent2.putExtras(b);
                startActivity(intent2);
                finish();
            }
        });

        btncontact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("contact","contact4");
                Intent intent2 = new Intent(getApplicationContext(),ContactDisplay.class);
                intent2.putExtras(b);
                startActivity(intent2);
                finish();
            }
        });

        btncontact5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("contact","contact5");
                Intent intent2 = new Intent(getApplicationContext(),ContactDisplay.class);
                intent2.putExtras(b);
                startActivity(intent2);
                finish();
            }
        });




    }

}