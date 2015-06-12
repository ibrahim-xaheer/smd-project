package com.usman.phaedra.myapplicationfb;

import android.app.Activity;
import android.os.Bundle;

import model.Contact;


/**
 * Created by PERSONAL on 5/13/2015.
 */
public class DispatchActivity extends Activity{
    private Contact currentContact=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle = getIntent().getExtras();
        currentContact= (Contact)getIntent().getSerializableExtra("1L");

        if (mBundle != null) {
            String mData = mBundle.getString("com.parse.Data");
            System.out.println("DATA : xxxxx : " + mData);
        }

    }
}
