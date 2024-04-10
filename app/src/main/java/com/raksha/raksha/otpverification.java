package com.raksha.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class otpverification extends AppCompatActivity {

    private EditText etcode1,etcode2,etcode3,etcode4,etcode5,etcode6;
    Button btnVerify;
    TextView tvResend,tvMobile;

    String name,address,bg,allergies,medications,mail,phone,password,pin,od,otp;
    Boolean msgtransfer= false;

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
        setContentView(R.layout.activity_otpverification);



        Bundle bundle = getIntent().getExtras();

        //linking of variables
        etcode1 = findViewById(R.id.etcode1);
        etcode2 = findViewById(R.id.etcode2);
        etcode3 = findViewById(R.id.etcode3);
        etcode4 = findViewById(R.id.etcode4);
        etcode5 = findViewById(R.id.etcode5);
        etcode6 = findViewById(R.id.etcode6);
        tvMobile = findViewById(R.id.tvMobile);
        btnVerify = findViewById(R.id.btnVerify);
        tvResend = findViewById(R.id.tvResend);



        phone = bundle.getString("mobile");
        otp = bundle.getString("otp");
        name = bundle.getString("name");
        mail = bundle.getString("email");
        address = bundle.getString("address");
        bg = bundle.getString("blood");
        od = bundle.getString("organ");
        password = bundle.getString("password");
        pin = bundle.getString("pin");
        allergies = bundle.getString("allergies");
        medications = bundle.getString("medications");
        setupOTPInputs();

        //getting mobile number from signup form
        tvMobile.setText(String.format(
                "+91-%s",phone
        ));

        //VerificationId = bundle.getString("VerificationId");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etcode1.getText().toString().trim().isEmpty()
                        ||etcode2.getText().toString().trim().isEmpty()
                        ||etcode3.getText().toString().trim().isEmpty()
                        ||etcode4.getText().toString().trim().isEmpty()
                        ||etcode5.getText().toString().trim().isEmpty()
                        ||etcode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(otpverification.this,"Please enter valid otp code",Toast.LENGTH_SHORT).show();
                    return;
                }

                String code =
                        etcode1.getText().toString() +
                                etcode2.getText().toString() +
                                etcode3.getText().toString() +
                                etcode4.getText().toString() +
                                etcode5.getText().toString() +
                                etcode6.getText().toString();

                if(code.equals(otp))
                {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("Users");
                    UserHelperClass helperClass = new UserHelperClass(name,password,mail,phone,address,bg,allergies,medications,pin,od,0);

                    reference.child(phone).setValue(helperClass);

                    Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), homepage.class);
                    intent.putExtra("mobile",phone);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid OTP!!!\nEnter valid otp",Toast.LENGTH_SHORT).show();
                }




            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = otp_generator();
                sendSMS();




            }
        });



    }
    public void setupOTPInputs(){
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
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode5.requestFocus();
                }else{
                    etcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode6.requestFocus();
                }else{
                    etcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etcode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(charSequence.toString().trim().isEmpty())
                {
                    etcode5.requestFocus();
                }else{
                    btnVerify.performClick();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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
}