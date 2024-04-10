package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.Random;
import java.util.regex.*;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    //Variable Declaration
    String[] bloodgroups = {"Choose","AB+","AB-","A+","A-","B+","B-","O+","O-","H/H","Unknown"};
    String[] odtype = {"Choose","Yes","No"};
    Spinner spinnerbg,spinnerod;
    Button btnback,btnSubmit;
    EditText etName,etAddress,etAllergies,etMedications,etMail,etPhone,etPassword,etConfirmPass,etPin,etPinVer;
    Boolean isnameValid = false,isphonevalid=false,isemailvalid=false,ispswdvalid=false,ispinvalid=false,isbgvalid=false,isodvalid=false,isaddValid=false,isdatapswdvalid = false,isdatapinvalid=false,msgtransfer=false;

    String name,address,bg,allergies,medications,mail,phone,password,pin,od,otp;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //linking of variables
        spinnerbg = findViewById(R.id.spinnerbg);
        spinnerod = findViewById(R.id.spinnerod);
        btnback = findViewById(R.id.btnback);
        btnSubmit = findViewById(R.id.btnSubmit);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etAllergies = findViewById(R.id.etAllergies);
        etMedications = findViewById(R.id.etMedications);
        etMail = findViewById(R.id.etMail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        etPin = findViewById(R.id.etPin);
        etPinVer = findViewById(R.id.etPinVer);

        etPhone.setError("Required field");
        etName.setError("Required field");
        etPin.setError("Required field");
        etPassword.setError("Required field");
        etConfirmPass.setError("Required field");
        etPinVer.setError("Required field");
        etAddress.setError("Required field");





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



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validating data
                Validatename(etName);
                Validateaddress(etAddress);
                ValidateAller(etAllergies);
                ValidateMed(etMedications);
                Validatemail(etMail);
                ValidateDataphone(etPhone);
                Validatepass(etPassword);
                ValidateDatapswd(etConfirmPass);
                Validatepin(etPin);
                ValidateDatapin(etPinVer);
                Validatebg(spinnerbg);
                Validateod(spinnerod);

                //Password validations
                if(ispswdvalid && isdatapswdvalid)
                {
                    if(!etPassword.getText().toString().equals(etConfirmPass.getText().toString()))
                    {
                        ispswdvalid = false;
                        etConfirmPass.setError("Passwords doesn't match");
                    }else
                    {
                        ispswdvalid = true;
                        password = etPassword.getText().toString().trim();
                    }
                }


                //pin Validation
                if(ispinvalid && isdatapinvalid)
                {
                    if(!etPin.getText().toString().equals(etPinVer.getText().toString()) )
                    {
                        ispinvalid = false;
                        etPinVer.setError("Pin's doesn't match");
                    }else
                    {
                        ispinvalid = true;
                        pin = etPin.getText().toString().trim();
                    }

                }


                //proceeding with the sign up if its valid
                if(ispinvalid && ispswdvalid && isbgvalid && isodvalid && isemailvalid && isphonevalid && isnameValid && isdatapswdvalid && isdatapinvalid && isaddValid)
                {
                    Accountexist(etPhone);
                }

            }
        });



        //back button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Signup.this,Loginpage.class);
                startActivity(back);
                finish();
            }
        });


    }

    private void Accountexist(EditText etPhone) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(),"Account already created",Toast.LENGTH_SHORT).show();
                }else{
                    otp = otp_generator();
                    sendSMS();
                    if(msgtransfer)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile",phone);
                        bundle.putString("name",name);
                        bundle.putString("email",mail);
                        bundle.putString("address",address);
                        bundle.putString("blood",bg);
                        bundle.putString("organ",od);
                        bundle.putString("password",password);
                        bundle.putString("pin",pin);
                        bundle.putString("allergies",allergies);
                        bundle.putString("medications",medications);
                        bundle.putString("otp",otp);
                        Intent intent = new Intent(getApplicationContext(),otpverification.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Validatebg(Spinner spinnerbg) {
        if(spinnerbg.getSelectedItem().toString().equals("Choose"))
        {
            Toast.makeText(getApplicationContext(),"Select your blood group",Toast.LENGTH_SHORT).show();
            isbgvalid=false;
        }else{
            bg=spinnerbg.getSelectedItem().toString();
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
            od=spinnerod.getSelectedItem().toString();
            isodvalid  = true;
        }
    }
    //validating confirm password
    public void ValidateDatapswd(EditText data){
        if(data.getText().toString().trim().isEmpty())
        {
            isdatapswdvalid = false;
            data.setError("Required field");
        }else{
            isdatapswdvalid = true;
        }
    }

    //validating confirm pin
    public void ValidateDatapin(EditText data){
        if(data.getText().toString().trim().isEmpty())
        {
            isdatapinvalid = false;
            data.setError("Required field");
        }else{
            isdatapinvalid = true;
        }
    }

    private void Validatepin(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            ispinvalid = false;
            data.setError("Required field");
        }else if(data.getText().toString().trim().length()!=4)
        {
            ispinvalid = false;
            data.setError("Pin should be of length 4");
        }else {
            ispinvalid = true;
        }
    }

    private void Validatepass(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            ispswdvalid = false;
            data.setError("Required field");
        }else if(data.getText().toString().trim().length()<6)
        {
            ispswdvalid = false;
            data.setError("Password should be minimum of length 6");
        }else if(data.getText().toString().trim().length()>15){
            ispswdvalid = false;
            data.setError("Maximum Password length is 15");
        }else{
            ispswdvalid = true;
        }
    }

    public void Validateaddress(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            isaddValid = false;
            data.setError("Required field");
        }else {
            isaddValid = true;
            address = data.getText().toString();
        }
    }

    public void ValidateDataphone(EditText data) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            isphonevalid = false;
            data.setError("Required field");
        }else if(data.getText().toString().length()!=10) {
            isphonevalid = false;
            data.setError("Invalid type Phone number should be of length 10");
        }else if(!m.find()){
            isphonevalid = false;
            data.setError("Invalid type Phone number should be of the form [6,7,8,9]*********");
        }else{
            isphonevalid = true;
            phone = data.getText().toString().trim();
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
            name = name.trim();
        }
    }

    public void  Validatemail(EditText data)
    {
        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            isemailvalid = true;
            mail = "Nill";
        }else if(data.getText().toString().length()>=320) {
            isemailvalid = false;
            data.setError("Invalid type!!! email should not be greater than 320 leters");
        }else if(!m.find()){
            isemailvalid = false;
            data.setError("Invalid email type");
        }else{
            isemailvalid = true;
            mail = data.getText().toString().trim();
        }
    }

    public void  ValidateAller(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            allergies = "Nill";
        }else{
            allergies = data.getText().toString().trim();
        }
    }

    public void  ValidateMed(EditText data)
    {
        if (data.getText().toString().trim().isEmpty()) {
            medications = "Nill";
        } else {
            medications = data.getText().toString().trim();
        }
    }

    private void sendSMS()
    {
        try{
            String message = "Your OTP for sign up in Raksha is:\t"+otp;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
            msgtransfer = true;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"OTP failed to deliver",Toast.LENGTH_SHORT).show();
            msgtransfer = false;
        }
    }

    public String otp_generator()
    {
        Random rndm_method = new Random();

        int num=0;
        String numbers = "0123456789";
        String otp;
        char[] otp1 = new char[6];

        for (int i = 0; i < 6; i++)
        {
            otp1[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        otp = String.valueOf(otp1);
        return otp;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}