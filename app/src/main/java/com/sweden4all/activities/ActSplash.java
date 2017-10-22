package com.sweden4all.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sweden4all.activities.accounts.ActLogIn;
import com.sweden4all.constants.Constants;
import com.sweden4all.exclude.JSONParser;
import com.sweden4all.exclude.URLManager;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.SplashView;

import org.json.JSONObject;

import static com.sweden4all.constants.Constants.IS_LOGGED_IN;
import static com.sweden4all.constants.Constants.USER_ID;

public class ActSplash extends BaseActivity {

    private static final String TAG = "ActSplash";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        lockAppCheck();
//        resetPrefs();
        launchNextActivity();
    }

    private void resetPrefs() {
        String id = prefs.getString(USER_ID);
        boolean isLoggedIn = prefs.getBool(Constants.IS_LOGGED_IN);
        prefs.clear();
        prefs.insert(USER_ID, id);
        prefs.insert(IS_LOGGED_IN, isLoggedIn);
    }

    @Override
    public BaseView getView() {
        return new SplashView(this);
    }

    public void launchNextActivity() {
//        switchActivity(ActHome.class);
        switchActivity(ActLogIn.class);
//        switchActivity(ActUpdatePin.class);
//        switchActivity(ActManageCards.class);
//        switchActivity(ActTermsNConds.class);
//        switchActivity(ActSettings.class);
//        startActivity(new Intent(this, TSCardSetup.class));
    }

    void lockAppCheck() {
        if (hasInternet())
            new IncidentLockAppUtil().execute();
        else launchNextActivity();
    }

    private class IncidentLockAppUtil extends AsyncTask<Void, Void, Boolean> {

        String message = "Try Later!";

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JSONObject json = JSONParser.GET(URLManager.CHECK_FOR_INCIDENT_LOCK);
                if (json != null && !"".equals(json.toString())) {
                    Log.i(TAG, "json: " + json.toString());
                    message = json.optString("message");
                    return json.optBoolean("do_lock_app");
                }
            } catch (Exception e) {
            }

            Log.i(TAG, "Should not reach here");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean doLockApp) {
            super.onPostExecute(doLockApp);
            Log.i(TAG, "doLockApp: " + doLockApp);
            if (doLockApp) {
                showDialog("Error Occurred", message);
            } else launchNextActivity();
        }
    }
}