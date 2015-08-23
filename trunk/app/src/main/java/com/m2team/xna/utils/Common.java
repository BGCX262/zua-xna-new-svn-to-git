package com.m2team.xna.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Hoang Minh on 2/11/2015.
 */
public class Common {
    public static final String ROBOTO_REGULAR = "RobotoCondensed-Regular.ttf";
    public static final String ROBOTO_LIGHT = "RobotoCondensed-Light.ttf";
    public static final String ROBOTO_BOLD = "RobotoCondensed-Bold.ttf";
    public static final String FONT_PATH = "fonts/";

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void setFont(final Context context, final View v, String font) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setFont(context, child, font);
                }
            } else if (v instanceof TextView ) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_PATH + font));
            }
        } catch (Exception e) {
        }
    }

    public static void putSharedPrefStringValue(Context context,  String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(Constant.SHARE_PREF_FILE_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPrefStringValue(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(Constant.SHARE_PREF_FILE_NAME, Context.MODE_MULTI_PROCESS);
        return pref.getString(key, "");
    }

    public static void removeSharedPrefKey(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(Constant.SHARE_PREF_FILE_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void removeSharedPref(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constant.SHARE_PREF_FILE_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
