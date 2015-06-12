package com.usman.phaedra.myapplicationfb;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import com.splunk.mint.Mint;
import com.usman.phaedra.feely_beta.R;


import org.json.JSONException;
import org.json.JSONObject;


import activity.BlueToothMain;
import activity.Camera;
import activity.InsertContact;
import activity.SetBluetooth;
import activity.ShowContactsFromAPI;
import activity.ShowContactsFromDB;
import texteditor.TextEditor;


public class MainActivity extends Activity implements OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener , ResultCallback<People.LoadPeopleResult> {

    CallbackManager callbackManager;
    TextView tv;
    TextView tv_email;
    ImageView displayImage;
    String userEmail= "";

    //fb
    LoginButton loginButton;
    Button openTextEditor;
    Button openShowContactsFromAPI;
    Button openShowContactsFromDB;
    Button insertContact;
    Button blueTooth;
    Button cameraActivity;
    //GoogleLogin related



    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private SignInButton googleButton;

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            //Access Token

            //Graph Request
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                            try {
                                userEmail=object.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int i=10;
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
            if(profile!=null)
            {
                tv.setText(profile.getName());
                tv_email.setText(profile.getId());
                //ImageVIew setting profile pic


            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(MainActivity.this, "07daa631");

//        Parse.initialize(getApplicationContext(), getString(R.string.parse_app_id), getString(R.string.parse_client_id));
//        final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        final String  androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        FacebookSdk.sdkInitialize(getApplicationContext());
//        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
        setContentView(R.layout.activity_main);
//        PushService.setDefaultPushCallback(this, MainActivity.class);
        //Application Class related tasks

        //end of Application class related tasks
//        ParseAnalytics.trackAppOpenedInBackground(getIntent());
//
//        PushService.setDefaultPushCallback(this, MainActivity.class);
//        ParseInstallation.getCurrentInstallation().saveInBackground();

        // Add code to print out the key hash
        blueTooth=(Button)findViewById(R.id.button_blue_tuth);
         loginButton = (LoginButton)findViewById(R.id.facebook_login_button);
         googleButton = (SignInButton) findViewById(R.id.google_login_button);
        openTextEditor = (Button) findViewById(R.id.button_text_editor);
        cameraActivity= (Button) findViewById(R.id.button_camera);
        openShowContactsFromAPI = (Button) findViewById(R.id.button_show_contactAPI);
        openShowContactsFromDB= (Button) findViewById(R.id.button_contacts_db);
        insertContact=(Button) findViewById(R.id.button_insert_contact);
        tv_email=(TextView)findViewById(R.id.textView_email);


        tv = (TextView) findViewById(R.id.tv2);

        displayImage=(ImageView)findViewById(R.id.displayImage);
        loginButton.setReadPermissions("user_friends,email");
        callbackManager = CallbackManager.Factory.create();

        // Initializing google plus api client
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
//                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        loginButton.registerCallback(callbackManager, mCallBack);

        openTextEditor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goEditor = new Intent(MainActivity.this, TextEditor.class);
//                ParseObject user = new ParseObject("User");

//                user.put("emailId", userEmail);
//                user.saveInBackground();
                //done with parse user registeration
                goEditor.putExtra("email", userEmail);
                startActivity(goEditor);

            }
        });

        openShowContactsFromAPI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goShowContactAPI = new Intent(MainActivity.this, ShowContactsFromAPI.class);
                startActivity(goShowContactAPI);

            }
        });
        insertContact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goinsertContact = new Intent(MainActivity.this, InsertContact.class);
                startActivity(goinsertContact);

            }
        });
        openShowContactsFromDB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goShowContactDB = new Intent(MainActivity.this, ShowContactsFromDB.class);
                startActivity(goShowContactDB);

            }
        });
        blueTooth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goShowBluetooth = new Intent(MainActivity.this, BlueToothMain.class);
                startActivity(goShowBluetooth);

            }
        });
        cameraActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCamera = new Intent(MainActivity.this, Camera.class);
                startActivity(goCamera);
            }
        });

        //user data second phase
        //Gmail Integration
        googleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.connect();

            }
        });
        //Parse related job


        // Post the uniqueId delayed
//        ParseUser.enableAutomaticUser();
//        ParseACL defaultACL= new ParseACL();
//        defaultACL.setPublicReadAccess(true);
//        ParseACL.setDefaultACL(defaultACL, true);


//        ParseInstallation.getCurrentInstallation().saveInBackground();
//        ParsePush.subscribeInBackground("", new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//                } else {
//                    Log.e("com.parse.push", "failed to subscribe for push", e);
//                }
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Parse.initialize(getApplicationContext(), getString(R.string.parse_app_id), getString(R.string.parse_client_id));
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult( requestCode,  resultCode,  data);
        callbackManager.onActivityResult(requestCode,  resultCode,  data);

        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
//        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
//                .setResultCallback(this);
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            tv.setText(personName);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    @Override
    public void onResult(People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    Log.d(TAG, "Display name: " + personBuffer.get(i).getDisplayName());
                    tv.setText(personBuffer.get(i).getId());
                }
            } finally {
                personBuffer.close();
            }
        } else {
            Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
        }
    }
}
