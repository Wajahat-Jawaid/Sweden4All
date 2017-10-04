package com.sweden4all.constants;

import android.content.Intent;

import com.sweden4all.R;

public final class Constants {

    /**
     * Keys
     */
    public static final String CITY = "city";
    public static final String COUNTRY = "country";

    public static final String DEVICE_TOKEN = "deviceToken";

    public static final String EMAIL = "email";

    public static final String NAME = "name";

    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";

    public static final String USER_DOB = "userDOB";
    public static final String USER_ID = "userID";

    /**
     * Actions
     */
    public static final String ACTION_OK = "OK";

    /**
     * Error messages
     */
    public static final String ERROR_FILL_ALL_FIELDS = "Please provide all fields";
    public static final String ERROR_NO_INTERNET = "Please enable internet connection";

    /**
     * Flags
     */
    public static final int FLAG_ACTIVITY_LAUNCH_BY_FLUSHING_STACK =
            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
    public static final String HAS_DELETED = "has_deleted";
    public static final String IS_SWAP_OFFER_MODE = "is_swap_offer_mode";
    public static final String DO_SET_AS_FAV = "do_set_as_fav";
    public static final String IS_SESSION = "is_session";
    public static final int SUCCESS = 1;
    public static final String STATUS_CODE = "status_code";
    public static final int FAILURE_RESULT = 2;
    public static final int SECURITY_PIN_LENGTH = 6;
    public static final int TRANSACTION_PIN_LENGTH = 4;
    public static final int BTN_ENABLED_BG = R.drawable.solid_blue_no_stroke_corner_20;
    public static final int BTN_DISABLED_BG = R.drawable.solid_gray_no_stroke_corner_20;

    /**
     * Messages & Titles
     */
    public static final String PD_MESSAGE = "Loading...";
    public static final String PD_TITLE = "Please wait";

    /**
     * Request Codes
     */
    public static final class RequestCodes {
        public static final int ACTION_IMAGE = 1;
        public static final int PERMISSION_EXTERNAL_STORAGE = 101;
        public static final int PERMISSION_ACCESS_CAMERA = 104;
        public static final int DISPLAY_FULL_IMG = 201;
        public static final int SIGN_IN = 301;
    }
}