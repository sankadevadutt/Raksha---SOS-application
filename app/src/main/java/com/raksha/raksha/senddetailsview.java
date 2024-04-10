package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class senddetailsview extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    Button btnback;
    TextView tvusername,tvuseraddress,tvuserbg,tvallergies,tvmedications,tvorgan,tvusermail,tvuserphone;
    String phone,name,address,bg,allergies,medications,od,mail;
    int count;
    CardView cv1,cv2,cv3,cv4,cv5;
    TextView tvcn,tvpn,tvrn,tvcn2,tvpn2,tvrn2,tvcn3,tvpn3,tvrn3,tvcn4,tvpn4,tvrn4,tvcn5,tvpn5,tvrn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddetailsview);



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
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        cv4 = findViewById(R.id.cv4);
        cv5 = findViewById(R.id.cv5);
        tvcn = findViewById(R.id.tvcn);
        tvcn2 = findViewById(R.id.tvcn2);
        tvcn3 = findViewById(R.id.tvcn3);
        tvcn4 = findViewById(R.id.tvcn4);
        tvcn5 = findViewById(R.id.tvcn5);
        tvpn = findViewById(R.id.tvpn);
        tvrn = findViewById(R.id.tvrn);
        tvpn2 = findViewById(R.id.tvpn2);
        tvrn2 = findViewById(R.id.tvrn2);
        tvpn3 = findViewById(R.id.tvpn3);
        tvrn3 = findViewById(R.id.tvrn3);
        tvpn4 = findViewById(R.id.tvpn4);
        tvrn4 = findViewById(R.id.tvrn4);
        tvpn5 = findViewById(R.id.tvpn5);
        tvrn5 = findViewById(R.id.tvrn5);



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
                count = snapshot.child(phone).child("count").getValue(int.class);


                tvallergies.setText(allergies);
                tvmedications.setText(medications);
                tvuseraddress.setText(address);
                tvorgan.setText(od);
                tvuserphone.setText(phone);
                tvusername.setText(name);
                tvusermail.setText(mail);
                tvuserbg.setText(bg);

                switch (count)
                {
                    case 1 :
                        cv1.setVisibility(View.VISIBLE);
                        cv2.setVisibility(View.GONE);
                        cv3.setVisibility(View.GONE);
                        cv4.setVisibility(View.GONE);
                        cv5.setVisibility(View.GONE);
                        tvcn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("name").getValue(String.class));
                        tvpn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        tvrn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("reln").getValue(String.class));
                        break;
                    case 2 :
                        cv1.setVisibility(View.VISIBLE);
                        cv2.setVisibility(View.VISIBLE);
                        cv3.setVisibility(View.GONE);
                        cv4.setVisibility(View.GONE);
                        cv5.setVisibility(View.GONE);
                        tvcn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("name").getValue(String.class));
                        tvpn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        tvrn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("reln").getValue(String.class));
                        tvcn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("name").getValue(String.class));
                        tvpn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        tvrn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("reln").getValue(String.class));
                        break;
                    case 3:
                        cv1.setVisibility(View.VISIBLE);
                        cv2.setVisibility(View.VISIBLE);
                        cv3.setVisibility(View.VISIBLE);
                        cv4.setVisibility(View.GONE);
                        cv5.setVisibility(View.GONE);
                        tvcn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("name").getValue(String.class));
                        tvpn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        tvrn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("reln").getValue(String.class));
                        tvcn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("name").getValue(String.class));
                        tvpn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        tvrn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("reln").getValue(String.class));
                        tvcn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("name").getValue(String.class));
                        tvpn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("phonec").getValue(String.class));
                        tvrn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("reln").getValue(String.class));
                        break;
                    case 4:
                        cv1.setVisibility(View.VISIBLE);
                        cv2.setVisibility(View.VISIBLE);
                        cv3.setVisibility(View.VISIBLE);
                        cv4.setVisibility(View.VISIBLE);
                        cv5.setVisibility(View.GONE);
                        tvcn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("name").getValue(String.class));
                        tvpn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        tvrn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("reln").getValue(String.class));
                        tvcn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("name").getValue(String.class));
                        tvpn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        tvrn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("reln").getValue(String.class));
                        tvcn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("name").getValue(String.class));
                        tvpn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("phonec").getValue(String.class));
                        tvrn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("reln").getValue(String.class));
                        tvcn4.setText(snapshot.child(phone).child("Contacts").child("contact4").child("name").getValue(String.class));
                        tvpn4.setText(snapshot.child(phone).child("Contacts").child("contact4").child("phonec").getValue(String.class));
                        tvrn4.setText(snapshot.child(phone).child("Contacts").child("contact4").child("reln").getValue(String.class));
                        break;
                    case 5 :
                        cv1.setVisibility(View.VISIBLE);
                        cv2.setVisibility(View.VISIBLE);
                        cv3.setVisibility(View.VISIBLE);
                        cv4.setVisibility(View.VISIBLE);
                        cv5.setVisibility(View.VISIBLE);
                        tvcn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("name").getValue(String.class));
                        tvpn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("phonec").getValue(String.class));
                        tvrn.setText(snapshot.child(phone).child("Contacts").child("contact1").child("reln").getValue(String.class));
                        tvcn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("name").getValue(String.class));
                        tvpn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("phonec").getValue(String.class));
                        tvrn2.setText(snapshot.child(phone).child("Contacts").child("contact2").child("reln").getValue(String.class));
                        tvcn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("name").getValue(String.class));
                        tvpn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("phonec").getValue(String.class));
                        tvrn3.setText(snapshot.child(phone).child("Contacts").child("contact3").child("reln").getValue(String.class));
                        tvcn4.setText(snapshot.child(phone).child("Contacts").child("contact4").child("name").getValue(String.class));
                        tvpn4.setText(snapshot.child(phone).child("Contacts").child("contact4").child("phonec").getValue(String.class));
                        tvrn4.setText(snapshot.child(phone).child("Contacts").child("contact4").child("reln").getValue(String.class));
                        tvcn5.setText(snapshot.child(phone).child("Contacts").child("contact5").child("name").getValue(String.class));
                        tvpn5.setText(snapshot.child(phone).child("Contacts").child("contact5").child("phonec").getValue(String.class));
                        tvrn5.setText(snapshot.child(phone).child("Contacts").child("contact5").child("reln").getValue(String.class));
                        break;
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}