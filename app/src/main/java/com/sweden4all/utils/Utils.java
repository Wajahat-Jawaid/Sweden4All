package com.sweden4all.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class Utils {

    private static final String TAG = "Utils";

    /**
     * Check of the passed value is null or empty. If in any of those, return the second
     * param string, otherwise return the nullable string passed in the first param
     */
    public String getValidStr(@Nullable String str,
                              @NonNull String retStrIfActualEmptyOrNull) {
        return (str == null || str.equals("")) ? retStrIfActualEmptyOrNull : str;
    }

    public String getValidStr(int id,
                              @NonNull String retStrIfActualEmptyOrNull) {
        return id == 0 ? retStrIfActualEmptyOrNull : id + "";
    }

    /**
     * Return int value of the provided String. This method accepts only those strings
     * which are the equivalent to integer in string format. If the String is not a
     * valid integer, 0 is returned
     *
     * @param val A string value that must be an integer equivalent.
     * @return int If the parametrized string is valid i.e. if it has an integer value,
     * then return the parsed int. Otherwise return 0 as default
     */
    public int getValidParsedInt(@Nullable final String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception ignored) {
        }

        return 0;
    }

    /**
     * Return String from the given edittext
     */
    public String EToS(EditText et) {
        return et.getText().toString().trim();
    }

    /**
     * @param fields TextView or EditText array that has to be marked red
     */
    public boolean ifFieldMissing(String... fields) {
        for (String v : fields) {
            if (TextUtils.isEmpty(v)) return true;
        }

        return false;
    }

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public String getTodaysMDYDate() {
        return toMDYFormat(
                getCalendar().get(Calendar.YEAR),
                getCalendar().get(Calendar.MONTH),
                getCalendar().get(Calendar.DAY_OF_MONTH));
    }

    public String getTodaysYMDDate() {
        return toYMDFormat(
                getCalendar().get(Calendar.YEAR),
                getCalendar().get(Calendar.MONTH),
                getCalendar().get(Calendar.DAY_OF_MONTH));
    }

    public String getWeekDay() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date date = new Date(getCalendar().get(Calendar.YEAR),
                getCalendar().get(Calendar.MONTH),
                (getCalendar().get(Calendar.DAY_OF_MONTH) - 1));
        return simpledateformat.format(date);
    }

    public String toMDYFormat(int y, int m, int d) {
        return (m + 1) + "-" + d + "-" + y;
    }

    public String toYMDFormat(int y, int m, int d) {
        return y + "-" + (m + 1) + "-" + d;
    }
}