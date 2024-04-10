package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class notificationsendrequest extends AppCompatActivity {

    String phone;
    Button btnsend, btnnot;
    TextView tvtimmer;
    int count;
    Boolean issend = false;
    String message;
    boolean msgtransfer = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    int i = 10;
    Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationsendrequest);




        phone = getIntent().getStringExtra("mobile");

        btnnot = findViewById(R.id.btnnot);
        btnsend = findViewById(R.id.btnsend);
        tvtimmer = findViewById(R.id.tvtimer);


        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                tvtimmer.setText(""+i);
                i--;
            }

            @Override
            public void onFinish() {
                if(!issend)
                {
                    btnsend.performClick();
                }

            }
        }.start();


        btnnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                issend = true;
                Intent intent = new Intent(getApplicationContext(),pinvalidation.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               issend = true;
               sendmessages();
               Toast.makeText(notificationsendrequest.this, "Message sent succesfully", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getApplicationContext(),senddetailsview.class);
               intent.putExtra("mobile",phone);
               startActivity(intent);
               finish();

            }
        });





    }

    private void sendmessages() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(getApplication().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            message= "Help me\nI am in danger\nCurrent location is\n" + "https://maps.google.com/?q="+lat+","+lon;
                        }
                    }
                });
            }
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = snapshot.child(phone).child("count").getValue(int.class);
                switch (count)
                {
                    case 1:
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        break;
                    case 2:
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        break;
                    case 3:
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact3").child("phonec").getValue(String.class));
                        break;
                    case 4:
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact3").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact4").child("phonec").getValue(String.class));
                        break;
                    case 5:
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact3").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact4").child("phonec").getValue(String.class));
                        sendSMS(snapshot.child(phone).child("Contacts").child("contact5").child("phonec").getValue(String.class));
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendSMS(String phonecontact)
    {
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonecontact,null,message,null,null);
            msgtransfer = true;
        }catch (Exception e){
            e.printStackTrace();
            msgtransfer = false;
        }
    }
}