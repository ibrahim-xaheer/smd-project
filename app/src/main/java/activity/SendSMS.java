package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.usman.phaedra.feely_beta.R;

/**
 * Created by PERSONAL on 6/12/2015.
 */
public class SendSMS extends Activity{
    TextView number;
    EditText smsContent;
    Button sendMsg;
    private  String smsString="" ;
    String phoneNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        number= (TextView) findViewById(R.id.textView_contact_number);
        smsContent=(EditText) findViewById(R.id.edittext_enter_sms);
        sendMsg=(Button)findViewById(R.id.button_send_sms);

        Intent intent = getIntent();
         phoneNumber = intent.getExtras().getString("number");
        number.setText(phoneNumber);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });
    }

    protected void sendSMSMessage() {
        Log.i("Send SMS", "");

        smsString=smsContent.getText().toString();


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, smsString, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
