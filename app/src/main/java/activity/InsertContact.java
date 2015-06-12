package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usman.phaedra.feely_beta.R;

import java.util.List;

import database.DatabaseHandler;
import model.Contact;

/**
 * Created by PERSONAL on 6/9/2015.
 */
public class InsertContact extends Activity {
    /** Called when the activity is first created. */
    private  String nameValue="";
    private  String contact_number="";
    EditText name;
    EditText number;
    Button register;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertcontact);
          name= (EditText)findViewById(R.id.editText_name);
          number= (EditText)findViewById(R.id.editText_number);
         register= (Button) findViewById(R.id.button_insertNumber);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    contact_number = number.getText().toString();
                    nameValue = name.getText().toString();

                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting contact..");
                    db.addContact(new Contact(nameValue, contact_number));
            }
        });




        // Reading all contacts
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }
    }
}