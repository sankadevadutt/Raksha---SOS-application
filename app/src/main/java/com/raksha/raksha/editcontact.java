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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editcontact extends AppCompatActivity {

    Button btnback,btndn;
    EditText etcn,etpn,etrn;
    String phone,contact;
    String cn,pn,rn;
    String nw_cn,nw_pn,nw_rn;

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
        setContentView(R.layout.activity_editcontact);

        btnback = findViewById(R.id.btnback8);
        etcn = findViewById(R.id.etcn);
        etpn = findViewById(R.id.etpn);
        etrn = findViewById(R.id.etrn);
        btndn = findViewById(R.id.btndn);



        Bundle b = getIntent().getExtras();
        phone = b.getString("mobile");
        contact = b.getString("contact");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pn = snapshot.child(phone).child("Contacts").child(contact).child("phonec").getValue(String.class);
                cn = snapshot.child(phone).child("Contacts").child(contact).child("name").getValue(String.class);
                rn = snapshot.child(phone).child("Contacts").child(contact).child("reln").getValue(String.class);
                etcn.setText(cn);
                etpn.setText(pn);
                etrn.setText(rn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nw_pn = etpn.getText().toString();
                nw_cn = etcn.getText().toString();
                nw_rn = etrn.getText().toString();
                if(pn.equals(nw_pn) && cn.equals(nw_cn) && rn.equals(nw_rn))
                {
                    Toast.makeText(getApplicationContext(),"Nothing has been modified",Toast.LENGTH_SHORT).show();
                }else {
                    Pattern p = Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE);
                    Pattern ph = Pattern.compile("[6-9][0-9]{9}");
                    Matcher m = p.matcher(nw_cn);
                    Matcher m2 = p.matcher(nw_rn);
                    Matcher m3 = ph.matcher(nw_pn);


                    if(nw_cn.isEmpty()||nw_pn.isEmpty()||nw_rn.isEmpty()){
                        Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                    }else if(!m.find()){
                        Toast.makeText(getApplicationContext(),"Invalid!!contact name should contain only characters",Toast.LENGTH_SHORT).show();
                    }else if(!m2.find()){
                        Toast.makeText(getApplicationContext(),"Invalid!! relation should contain only characters",Toast.LENGTH_SHORT).show();
                    }else if(!m3.find()){
                        Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
                    }else {
                        if(phone.equals(nw_pn))
                        {
                            Toast.makeText(getApplicationContext(),"You cannot add your own number as emergency contact",Toast.LENGTH_SHORT).show();
                        }else {
                            rootNode = FirebaseDatabase.getInstance();
                            reference1 = rootNode.getReference("Users").child(phone).child("Contacts").child(contact);

                            reference1.child("name").setValue(nw_cn);
                            reference1.child("phonec").setValue(nw_pn);
                            reference1.child("reln").setValue(nw_rn);

                            Toast.makeText(getApplicationContext(),"Data has been changed successfully",Toast.LENGTH_SHORT).show();
                            Bundle bu = new Bundle();
                            bu.putString("mobile",phone);
                            bu.putString("contact",contact);
                            Intent intent = new Intent(getApplicationContext(),ContactDisplay.class);
                            intent.putExtras(bu);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bu = new Bundle();
                bu.putString("mobile",phone);
                bu.putString("contact",contact);
                Intent intent = new Intent(getApplicationContext(),ContactDisplay.class);
                intent.putExtras(bu);
                startActivity(intent);
                finish();
            }
        });



    }
}