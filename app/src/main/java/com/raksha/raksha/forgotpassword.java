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

public class forgotpassword extends AppCompatActivity {

    Button btnChange,btnCancel;
    EditText etNewpswd,etpswdcnfrmsn;
    String phone,password,password_before;
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
        setContentView(R.layout.activity_forgotpassword);

        btnChange = findViewById(R.id.btnChange3);
        btnCancel = findViewById(R.id.btnCancel);
        etNewpswd = findViewById(R.id.etNewpswd);
        etpswdcnfrmsn = findViewById(R.id.etpswdcnfrmsn);

        phone = getIntent().getStringExtra("mobile");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                password_before = snapshot.child(phone).child("password").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etNewpswd.getText().toString().trim().isEmpty()||etpswdcnfrmsn.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }else if(etNewpswd.getText().toString().trim().length() < 6){
                    Toast.makeText(getApplicationContext(),"Password should be min of length 6",Toast.LENGTH_SHORT).show();
                }else if(!etNewpswd.getText().toString().trim().equals(etpswdcnfrmsn.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(),"Password not matched",Toast.LENGTH_SHORT).show();
                }else{
                    password = etNewpswd.getText().toString().trim();
                    if(password.equals(password_before))
                    {
                        Toast.makeText(getApplicationContext(),"Password should not match with previous password",Toast.LENGTH_SHORT).show();
                    }else{
                        //storing in database
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference("Users");

                        reference1.child(phone).child("password").setValue(password);
                        Toast.makeText(getApplicationContext(),"Password has been changed successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Loginpage.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotpassword.this,Loginpage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}