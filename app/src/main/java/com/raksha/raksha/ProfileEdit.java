package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerbg,spinnerod;
    String[] odtype = {"Choose","Yes","No"};
    String[] bloodgroups = {"Choose","AB+","AB-","A+","A-","B+","B-","O+","O-","H/H","Unknown"};
    Button btnback,btnSubmit;
    EditText etName,etAddress,etAllergies,etMedications,etMail;
    int spinnerposnbg,spinnerposnod;


    Boolean isbgvalid=false,isodvalid=false,isaddValid=false,isnameValid=false,isemailvalid=false;
    String bg,od,mail,medications,name,phone,address,allergies;
    String new_bg,new_mail,new_name,new_medications,new_address,new_allergies,new_od;



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
        setContentView(R.layout.activity_profile_edit);

        spinnerod = findViewById(R.id.spinnerod);
        spinnerbg = findViewById(R.id.spinnerbg);
        btnback = findViewById(R.id.btnback);
        btnSubmit = findViewById(R.id.btnSubmit);
        etMail = findViewById(R.id.etMail);
        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etName);
        etAllergies = findViewById(R.id.etAllergies);
        etMedications = findViewById(R.id.etMedications);


        phone = getIntent().getStringExtra("mobile");

        //spinner for blood groups
        spinnerbg.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,bloodgroups);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerbg.setAdapter(adapter);



        //spinner for Organ donor
        spinnerod.setOnItemSelectedListener(this);
        ArrayAdapter adapt = new ArrayAdapter(this, R.layout.color_spinner_layout,odtype);
        adapt.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerod.setAdapter(adapt);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mail = snapshot.child(phone).child("mail").getValue(String.class);
                address = snapshot.child(phone).child("address").getValue(String.class);
                allergies = snapshot.child(phone).child("allergies").getValue(String.class);
                medications = snapshot.child(phone).child("medications").getValue(String.class);
                bg = snapshot.child(phone).child("bg").getValue(String.class);
                od = snapshot.child(phone).child("od").getValue(String.class);
                name = snapshot.child(phone).child("name").getValue(String.class);

                etMail.setText(mail);
                etAddress.setText(address);
                etAllergies.setText(allergies);
                etMedications.setText(medications);
                spinnerposnbg = adapter.getPosition(bg);
                spinnerbg.setSelection(spinnerposnbg);
                spinnerposnod = adapt.getPosition(od);
                spinnerod.setSelection(spinnerposnod);
                etName.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_name = etName.getText().toString();
                new_mail = etMail.getText().toString();
                new_address = etAddress.getText().toString();
                new_allergies = etAllergies.getText().toString();
                new_medications = etMedications.getText().toString();
                new_bg = spinnerbg.getSelectedItem().toString();
                new_od = spinnerod.getSelectedItem().toString();
                if(new_name.equals(name) && new_mail.equals(mail) && new_od.equals(od) && new_bg.equals(bg)
                    && new_address.equals(address) && new_allergies.equals(allergies) && new_medications.equals(medications))
                {
                    Toast.makeText(getApplicationContext(),"Nothing has been modified",Toast.LENGTH_SHORT).show();
                }else{
                    Validateaddress(etAddress);
                    ValidateAller(etAllergies);
                    Validatebg(spinnerbg);
                    Validatemail(etMail);
                    Validateod(spinnerod);
                    Validatename(etName);
                    ValidateMed(etMedications);
                    if(isaddValid && isemailvalid && isodvalid && isbgvalid && isnameValid)
                    {
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference("Users");

                        reference1.child(phone).child("mail").setValue(new_mail);
                        reference1.child(phone).child("name").setValue(new_name);
                        reference1.child(phone).child("address").setValue(new_address);
                        reference1.child(phone).child("bg").setValue(new_bg);
                        reference1.child(phone).child("od").setValue(new_od);
                        reference1.child(phone).child("medications").setValue(new_medications);
                        reference1.child(phone).child("allergies").setValue(new_allergies);

                        Toast.makeText(getApplicationContext(),"Data has been changed successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),profile_selection.class);
                        intent.putExtra("mobile",phone);
                        startActivity(intent);
                        finish();

                    }
                }
            }
        });






        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(),profile_selection.class);
                back.putExtra("mobile",phone);
                startActivity(back);
                finish();
            }
        });
    }


    private void Validatebg(Spinner spinnerbg) {
        if(spinnerbg.getSelectedItem().toString().equals("Choose"))
        {
            Toast.makeText(getApplicationContext(),"Select your blood group",Toast.LENGTH_SHORT).show();
            isbgvalid=false;
        }else{
            new_bg=spinnerbg.getSelectedItem().toString();
            isbgvalid = true;
        }
    }

    private void Validateod(Spinner spinnerod)
    {
        if(spinnerod.getSelectedItem().toString().equals("Choose"))
        {
            Toast.makeText(getApplicationContext(),"Select valid organ donor option",Toast.LENGTH_SHORT).show();
            isodvalid=false;
        }else{
            new_od=spinnerod.getSelectedItem().toString();
            isodvalid  = true;
        }
    }

    public void Validateaddress(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            isaddValid = false;
            data.setError("Required field");
        }else {
            isaddValid = true;
            new_address = data.getText().toString();
        }
    }

    public void Validatename(EditText data) {
        Pattern p = Pattern.compile("^[a-z\\s]+$",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(data.getText().toString());
        String name_modifier="";
        if(data.getText().toString().trim().isEmpty())
        {
            isnameValid = false;
            data.setError("Required field");
        }else if(data.getText().toString().length()<=2){
            isnameValid = false;
            data.setError("Name should be min of 2 characters");
        }else if(data.getText().toString().length()>=32) {
            isnameValid = true;
            name_modifier = data.getText().toString().trim().substring(0,32);
        }else if(!m.find()){
            isnameValid = false;
            data.setError("Invalid type name should contain only letters and spaces");
        }else{
            isnameValid = true;
            name_modifier = data.getText().toString().trim();
            /*making capital letters after space
             *like name second
             * changes to
             * Name Second
             */
            name = "";
            String [] changer = name_modifier.split("\\s");
            for(String s: changer)
            {
                String first = s.substring(0,1).toUpperCase();
                name += first+s.substring(1)+" ";
            }
            new_name = name.trim();
        }
    }

    public void  Validatemail(EditText data) {
        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            isemailvalid = true;
            new_mail = "Nill";
        }else if(data.getText().toString().length()>=320) {
            isemailvalid = false;
            data.setError("Invalid type!!! email should not be greater than 320 leters");
        }else if(!m.find()){
            isemailvalid = false;
            data.setError("Invalid email type");
        }else{
            isemailvalid = true;
            new_mail = data.getText().toString().trim();
        }
    }

    public void  ValidateAller(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            new_allergies = "Nill";
        }else{
            new_allergies = data.getText().toString().trim();
        }
    }

    public void  ValidateMed(EditText data) {
        if (data.getText().toString().trim().isEmpty()) {
            new_medications = "Nill";
        } else {
            new_medications = data.getText().toString().trim();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}