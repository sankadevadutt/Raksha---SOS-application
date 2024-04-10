package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class pinvalidation extends AppCompatActivity {


    Button btnconfirm;
    EditText etcode1,etcode2,etcode3,etcode4;
    TextView tvtimer2;

    private FusedLocationProviderClient fusedLocationProviderClient;

    Double lat,lon;
    int i=30,count;
    String locn = "";
    String message;
    boolean msgtransfer = false;

    String phone,pin,pin_entered;
    Boolean halt = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinvalidation);

        btnconfirm = findViewById(R.id.btnconfirm);
        etcode1 = findViewById(R.id.etcode1);
        etcode2 = findViewById(R.id.etcode2);
        etcode3 = findViewById(R.id.etcode3);
        etcode4 = findViewById(R.id.etcode4);
        tvtimer2 = findViewById(R.id.tvtimer2);


        phone = getIntent().getStringExtra("mobile");
        setuppinInputs();


        new CountDownTimer(30000,1000){
            @Override
            public void onTick(long l) {
                tvtimer2.setText(""+i);
                i--;
            }

            @Override
            public void onFinish() {
                if(!halt)
                {
                    send();
                    Toast.makeText(getApplicationContext(), "Message sent succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),senddetailsview.class);
                    intent.putExtra("mobile",phone);
                    startActivity(intent);
                    finish();
                }

            }
        }.start();

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pin_entered =
                        etcode1.getText().toString() +
                                etcode2.getText().toString() +
                                etcode3.getText().toString() +
                                etcode4.getText().toString() ;
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                Query checkuser = reference.orderByChild("phone").equalTo(phone);

                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pin = snapshot.child(phone).child("pin").getValue(String.class);
                        if(pin.equals(pin_entered))
                        {
                            halt=true;
                            Toast.makeText(pinvalidation.this, "Message transfer halted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),homepage.class);
                            intent.putExtra("mobile",phone);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            halt = true;
                            send();
                            Toast.makeText(getApplicationContext(), "Message sent succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),senddetailsview.class);
                            intent.putExtra("mobile",phone);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void send() {
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


    private void setuppinInputs() {
        etcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode3.requestFocus();
                }else{
                    etcode1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode4.requestFocus();
                }else{
                    etcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty())
                {
                    etcode3.requestFocus();
                }else{
                    btnconfirm.performClick();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}