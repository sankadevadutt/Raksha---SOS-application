package com.raksha.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _numberentry_forgotpassword extends AppCompatActivity {
    Button btnback2,btnnext;
    EditText etphonenumb;
    String phone,otp ;
    Boolean msgtransfer = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__numberentry_forgotpassword);

        btnback2 = findViewById(R.id.btnback2);
        btnnext = findViewById(R.id.btnnext);
        etphonenumb = findViewById(R.id.etphonenumb2);

        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Loginpage.class);
                startActivity(intent);
                finish();
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etphonenumb.getText().toString();
                Pattern p = Pattern.compile("[6-9][0-9]{9}");
                Matcher m = p.matcher(etphonenumb.getText().toString());
                if(etphonenumb.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Required field",Toast.LENGTH_SHORT).show();
                }else if(etphonenumb.getText().toString().length()!=10) {
                    Toast.makeText(getApplicationContext(),"Invalid type!!\n Phone number should be of length 10",Toast.LENGTH_SHORT).show();
                }else if(!m.find()){
                    Toast.makeText(getApplicationContext(),"Invalid type!!\n Phone number should be of the form [6,7,8,9]*********",Toast.LENGTH_SHORT).show();
                }else{
                    Accountexist(etphonenumb);
                }
            }
        });
    }

    private void Accountexist(EditText etphonenum) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(),"Account doesn't exist",Toast.LENGTH_SHORT).show();
                }else{
                    otp = otp_generator();
                    sendSMS();
                    if(msgtransfer)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile",etphonenumb.getText().toString());
                        bundle.putString("otp",otp);
                        Intent intent = new Intent(getApplicationContext(),otpverificationforpasswordchange.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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