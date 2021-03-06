package com.runupstdio.lumbungdesa;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;

import com.onesignal.OneSignal;

public class LumbungDesa extends Application {

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        settings = getSharedPreferences("RUNUP", getApplicationContext().MODE_PRIVATE);

        // LumbungDesa Initialization
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.startInit(this)
                .autoPromptLocation(true)
                .setNotificationReceivedHandler(new ChatActivity())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);
                editor = settings.edit();
                editor.putString("notifToken", userId);
                editor.commit();
            }
        });
    }
}