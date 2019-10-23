package com.example.kasir.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

    private static final String MY_PREFS_NAME = "SKRIPSI_DIANA";

    public static void setLogin(Context context, boolean isLogin) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", isLogin);
        editor.apply();
        editor.commit();
    }

    public static boolean isLogin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("isLogin", false);
    }
}
