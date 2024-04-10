package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.regex.*;

public class AddContact extends AppCompatActivity {

    Button btnback3,btnsubmit;
    EditText etcontactname,etreln,etcontactphone;
    String phone,name,reln,contact,phone_rel;
    int count,i;
    String contact_check;
    String phone_val;
    Boolean isvalidnum = true;
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
        setContentView(R.layout.activity_add_contact);

        //linking of variables

        btnback3 = findViewById(R.id.btnback3);
        btnsubmit = findViewById(R.id.btnsubmit);
        etcontactname = findViewById(R.id.etcontactname);
        etcontactphone = findViewById(R.id.etcontactphone);
        etreln = findViewById(R.id.etreln);

        phone = getIntent().getExtras().getString("mobile");
        count = getIntent().getExtras().getInt("count");

        //back button
        btnback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();

            }
        });

        //submit button
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pattern p = Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE);
                Pattern ph = Pattern.compile("[6-9][0-9]{9}");
                Matcher m = p.matcher(etcontactname.getText().toString());
                Matcher m2 = p.matcher(etreln.getText().toString());
                Matcher m3 = ph.matcher(etcontactphone.getText().toString());


                if(etcontactname.getText().toString().trim().isEmpty()||
                        etcontactphone.getText().toString().trim().isEmpty()||
                        etreln.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }else if(!m.find()){
                    Toast.makeText(getApplicationContext(),"Invalid!!contact name should contain only characters",Toast.LENGTH_SHORT).show();
                }else if(!m2.find()){
                    Toast.makeText(getApplicationContext(),"Invalid!! relation should contain only characters",Toast.LENGTH_SHORT).show();
                }else if(!m3.find()){
                    Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
                }else{


                    name = etcontactname.getText().toString();
                    phone_rel = etcontactphone.getText().toString();
                    reln = etreln.getText().toString();

                    if(phone.equals(phone_rel))
                    {
                        Toast.makeText(getApplicationContext(),"You cannot add your own number as emergency contact",Toast.LENGTH_SHORT).show();
                    }else{
                        /*checkuser();
                        if(isvalidnum){

                        }*/
                        count++;
                        contact = "contact"+(count);
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference("Users");
                        usersecondhelper helperClass = new usersecondhelper(name,phone_rel,reln);

                        reference1.child(phone).child("Contacts").child(contact).setValue(helperClass);

                        Toast.makeText(getApplicationContext(),"Contact created successfully",Toast.LENGTH_SHORT).show();
                        reference1.child(phone).child("count").setValue(count);
                        //need to validate through database whether the contact has been added previously or data is same as login
                        Intent intent = new Intent(getApplicationContext(),homepage.class);
                        intent.putExtra("mobile",phone);
                        startActivity(intent);
                        finish();


                    }


                }
            }
        });

    }

    private void checkuser() {
        for(i = 1;i<=count;i++)
        {
            contact_check="contact"+i;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            Query checkuser = reference.orderByChild("phone").equalTo(phone);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    phone_val  = snapshot.child(phone).child("Contacts").child(contact_check).child("phone").getValue(String.class);
                    if(phone_rel.equals(phone_val))
                    {
                        Toast.makeText(AddContact.this, "Number already added as an emergency contact", Toast.LENGTH_SHORT).show();
                        isvalidnum=false;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            if(!isvalidnum)
                break;

        }
    }
}