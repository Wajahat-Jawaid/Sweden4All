package com.sweden4all.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Base64;

import static com.sweden4all.constants.Constants.INVALID_INT;

public class SharedPrefs {

    private final SharedPreferences prefs;

    /**
     * Constructor
     *
     * @param context Context of the calling class
     */
    public SharedPrefs(final Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Insert data to the SharedPreference.
     *
     * @param key  The key of the data using which the data retrieval can be done
     * @param data The actual data against which, the key has been provided
     */
    public void insert(@NonNull String key, @NonNull String data) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, data);
        editor.apply();
    }

    /**
     * @see #insert(String, String)
     * For the Integer type value
     */
    public void insert(@NonNull String key, int data) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, data);
        editor.apply();
    }

    /**
     * @see #insert(String, String)
     * For the Long type value
     */
    public void insert(@NonNull String key, long data) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, data);
        editor.apply();
    }

    /**
     * @see #insert(String, String)
     * For the Long type value
     */
    public void insert(@NonNull String key, @NonNull Boolean data) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, data);
        editor.apply();
    }

    /**
     * Method to get the value against the provided key
     *
     * @param key A non-nullable key against which the data has to be retrieved.
     */
    public String getString(String key) {
        if (null != key && !"".equals(key))
            return prefs.getString(key, "");
        return "";
    }

    /**
     * Method to get the value against the provided key
     *
     * @param key A non-nullable key against which the data has to be retrieved.
     */
    public Boolean getBool(String key) {
        if (null != key && !"".equals(key))
            return prefs.getBoolean(key, false);
        return false;
    }

    /**
     * @see #getString(String) for getting int
     */
    public int getInt(@NonNull String key) {
        int res;
        try {
            res = prefs.getInt(key, INVALID_INT);
        } catch (Exception e) {
            res = -1;
        }
        return res;
    }

    /**
     * @see #getString(String) for getting Long
     */
    public long getLong(@NonNull String key) {
        long res;
        try {
            res = prefs.getLong(key, 0);
        } catch (Exception e) {
            res = 0;
        }
        return res;
    }

    public void clear() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * CRYPTO
     */
    private String encode(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private String decode(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}