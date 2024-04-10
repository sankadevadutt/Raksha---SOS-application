package com.raksha.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class profile_selection extends AppCompatActivity {

    TextView tvpswdchange,tvpinchange,tvprofile,tveditprofile,tvlogout;
    Button btnback4;
    ImageView ivpswdchange,ivpinchange,ivprofile,iveditprofile,ivlogout;

    String phone,otp;
    Boolean msgtransfer = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        tvpswdchange = findViewById(R.id.tvpswdchange);
        tvpinchange = findViewById(R.id.tvpinchange);
        tvprofile = findViewById(R.id.tvProfile);
        tveditprofile = findViewById(R.id.tveditprofile);
        tvlogout = findViewById(R.id.tvlogout);
        ivpswdchange = findViewById(R.id.ivpswdchange);
        ivpinchange = findViewById(R.id.ivpinchange);
        ivprofile = findViewById(R.id.ivProfile);
        iveditprofile = findViewById(R.id.iveditprofile);
        ivlogout = findViewById(R.id.ivlogout);
        btnback4 = findViewById(R.id.btnback4);

        phone = getIntent().getStringExtra("mobile");

        tvpswdchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = otp_generator();
                sendSMS();
                if(msgtransfer)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile",phone);
                    bundle.putString("otp",otp);
                    Intent intent = new Intent(getApplicationContext(),otpverificationforpasswordchange.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ivpswdchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = otp_generator();
                sendSMS();
                if(msgtransfer)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile",phone);
                    bundle.putString("otp",otp);
                    Intent intent = new Intent(getApplicationContext(),otpverificationforpasswordchange.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tveditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileEdit.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });
        iveditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileEdit.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        tvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });
        ivprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnback4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Loginpage.class);
                startActivity(intent);
                finish();
            }
        });

        ivlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Loginpage.class);
                startActivity(intent);
                finish();
            }
        });

        ivpinchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),pinchange_phoneselection.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        tvpinchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),pinchange_phoneselection.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

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

    private void sendSMS()
    {
        try{
            String message = "Your OTP for password change in Raksha is:\t"+otp;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
            msgtransfer = true;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"OTP failed to deliver",Toast.LENGTH_SHORT).show();
            msgtransfer = false;
        }
    }
}