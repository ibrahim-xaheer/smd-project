package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.usman.phaedra.feely_beta.R;
import com.usman.phaedra.myapplicationfb.DispatchActivity;

import java.io.Serializable;
import java.util.List;

import activity.Dispatch_Call_SMS;
import activity.SendSMS;
import model.Contact;

/**
 * Created by PERSONAL on 6/10/2015.
 */
public class CustomListAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Contact> contactList;
    private Context context=null;

    public static final String SER_KEY = "1L";

    public CustomListAdapter(Activity activity, List<Contact> contactItems) {
        this.activity = activity;
        this.contactList = contactItems;
        context=activity;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int location) {
        return contactList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.contact_sample, null);



        TextView name = (TextView) convertView.findViewById(R.id.contact_name);
        TextView number = (TextView) convertView.findViewById(R.id.contact_number);
         RelativeLayout newsItem=(RelativeLayout)convertView.findViewById(R.id.contact_layout);

        // getting movie data for the row
        Contact m = contactList.get(position);
        newsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Position", contactList.get(position)+"");
                Intent i = new Intent(context, Dispatch_Call_SMS.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(SER_KEY, (Serializable) contactList.get(position));
                i.putExtras(mBundle);
                context.startActivity(i);
            }
        });

        // title
        name.setText(m.getName());

        // rating
        number.setText("Number: " + String.valueOf(m.getPhoneNumber()));
        return convertView;

    }

}