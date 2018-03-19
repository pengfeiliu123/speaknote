package com.lpf.utilcode.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtil {

    private static PreferencesUtil instance;

    private static SharedPreferences sharedPreferences;

    private PreferencesUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtil getInstance(Context context) {
        if (instance == null)
            instance = new PreferencesUtil(context);
        return instance;
    }

    public String getTestName() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("user_name", "");
        }
        return "";
    }

    public boolean saveTestName(String name) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("user_name", name)
                    .commit();
        }
        return false;
    }


}
