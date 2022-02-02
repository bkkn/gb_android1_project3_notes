package me.bkkn;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import me.bkkn.data.dummy.SharedPreferencesNotes;
import me.bkkn.domain.repository.Notes;

public class App extends Application {
    private static final String APP_SHARED_PREFS_NAME = "APP_SHARED_PREFS_NAME";
    private static final String APP_SHARED_PREFS_COUNTER_KEY = "APP_SHARED_PREFS_COUNTER_KEY";
    public static final String TAG = "@@@";
    private static App sInstance = null;
    private static int launchCounter = 0;

    public Notes notes;

    public static App get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notes = new SharedPreferencesNotes(this);
        sInstance = this;
        int count = getLaunchCount();
        Log.d(TAG, "onCreate() called");
        Log.d(TAG, "Count: " + count + "\n");
        incrementCount();
    }

    private int getLaunchCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(APP_SHARED_PREFS_COUNTER_KEY, 0);
    }

    private void setLaunchCount(int count) {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(APP_SHARED_PREFS_COUNTER_KEY, count).apply();
    }

    public void incrementCount() {
        setLaunchCount(getLaunchCount() + 1);
    }
}
