package com.raksha.raksha;

import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class sendmessage {
    String location,phone;
    String message = "Help I am in danger\nCurrent location is\n"+location;
    boolean msgtransfer = false;
    DatabaseReference root;
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
}
