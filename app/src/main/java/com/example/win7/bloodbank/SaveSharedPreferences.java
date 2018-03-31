package com.example.win7.bloodbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Win 7 on 12/24/2017.
 */

public class SaveSharedPreferences {
    static final String PREF_USER_ID= "username";
    static final String USER_NAME="user";
    static final String B_GROUP="bloodgroup";
    static final String ADDRESS="address";
    static  final  String EMAIL="email";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUser(Context ctx,String userId,String userName,String email,String bgroup,String address)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, userId);

        editor.putString(USER_NAME, userName);
        editor.putString(B_GROUP, bgroup);
        editor.putString(ADDRESS, address);
        editor.putString(EMAIL, email);

        editor.commit();
    }

    public static String getUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_NAME, "");
    }
    public static String getbGroup(Context ctx)
    {
        return getSharedPreferences(ctx).getString(B_GROUP, "");
    }
    public static String getAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(ADDRESS, "");
    }
    public static String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(EMAIL, "");
    }


    public static void clearUser(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

}
