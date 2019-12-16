package com.example.kasir.utils;

import android.content.Context;
import android.widget.Toast;

public class UtilsDialog {

    public  static  void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public  static  void showLongToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
