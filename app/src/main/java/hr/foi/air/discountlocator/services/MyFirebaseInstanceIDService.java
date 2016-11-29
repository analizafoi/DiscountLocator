package hr.foi.air.discountlocator.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Zlatko on 29.11.2016..
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String TAG = "DISCOUNT_LOCATOR";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        saveToken(refreshedToken);
    }

    private void saveToken(String token)
    {
        //save to shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString(FCM_TOKEN, token).apply();

        //send to your own web service
        //TODO
    }
}
