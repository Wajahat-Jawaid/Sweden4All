package com.sweden4all.exclude;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import org.json.JSONObject;

public class IncidentLockAppUtil extends AsyncTask {

    private Context context;

    public IncidentLockAppUtil(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            JSONObject json = JSONParser.GET(URLManager.CHECK_FOR_INCIDENT_LOCK);
            if (json != null && !"".equals(json)) {
                boolean doLockApp = json.optBoolean("do_lock_app");
                doExpireApp(doLockApp);
            }
        } catch (Exception e) {
        }
        return null;
    }

    private void doExpireApp(boolean flag) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("do_lock_app", flag);
        editor.apply();
    }
}