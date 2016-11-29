package hr.foi.air.discountlocator.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import hr.foi.air.discountlocator.R;

public class GcmRegistrationIntentService extends IntentService {
    public static final String GCM_TOKEN = "gcmToken";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GcmRegistrationIntentService() {
        super("GcmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = getResources().getString(R.string.gcm_sender_id);

        try {
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            System.out.println("New registration token: " + token);
            preferences.edit().putString(GCM_TOKEN, token).apply();

            //TODO - Send registration token to backend

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
