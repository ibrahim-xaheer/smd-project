package activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.usman.phaedra.feely_beta.R;

import model.Contact;

/**
 * Created by PERSONAL on 6/12/2015.
 */
public class Dispatch_Call_SMS extends Activity{
    Contact currentContact= new Contact();
    TextView conact_number;
    Button call;
    Button sms;
    private  final String TAG="Dispatch CALL SMS";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_call_sms);

        Log.v(TAG, "reached in dispatch");

        conact_number=(TextView) findViewById(R.id.textView_phone_number);
        call=  (Button) findViewById(R.id.button_call);
        sms=  (Button) findViewById(R.id.button_dispatch_sms);
        currentContact= (Contact)getIntent().getSerializableExtra("1L");
        conact_number.setText(currentContact.getPhoneNumber());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call();
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSMS=new Intent(Dispatch_Call_SMS.this, SendSMS.class);
                sendSMS.putExtra("number",currentContact.getPhoneNumber());
                startActivity(sendSMS);
            }
        });



    }

    private void call() {
        String number = currentContact.getPhoneNumber();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" +number));
        startActivity(intent);
    }


}
