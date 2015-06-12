package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.usman.phaedra.feely_beta.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomListAdapter;
import database.DatabaseHandler;
import model.Contact;

/**
 * Created by PERSONAL on 6/9/2015.
 */
public class ShowContactsFromDB extends Activity {
    /** Called when the activity is first created. */
    private List<Contact> contactList = new ArrayList<Contact>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcontactsdb);
        DatabaseHandler db = new DatabaseHandler(this);
        contactList = db.getAllContacts();

        listView = (ListView) findViewById(R.id.list_contact);
        adapter = new CustomListAdapter(this, contactList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        for (Contact cn : contactList) {
            String log = "Id: "+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }

    }
}