package com.elink.fusion.util;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context.getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context.getApplicationContext(), info, Toast.LENGTH_LONG).show();
    }
}
