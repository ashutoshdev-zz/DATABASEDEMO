package gcm.com.gcmapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends FragmentActivity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    String SENDER_ID = "529314510718";
    static final String TAG = "L2C";
    GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    Context context;
    String regid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Chat", 0);
        context = getApplicationContext();

        if(checkPlayServices()){

            new Register().execute();

        }else{
            Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("TAG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;

    }

    private class Register extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    regid = gcm.register(SENDER_ID);
                    Log.e("RegId",regid);

                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("REG_ID", regid);
                    edit.commit();

                }

                return  regid;

            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
                return "Fails";

            }
        }
        @Override
        protected void onPostExecute(String json) {

        }
    }


}
