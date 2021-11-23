package com.imeja.bmi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.imeja.bmi.models.LoginDetails;

public class Controller extends Application {
    public static final String MyPREFERENCES = "BMI";
    public static final String userToken = "userToken";
    public static final String isLoggedIn = "isLoggedIn";
    public static final String userName = "userName";
    public static final String userEmail = "userEmail";
    public static final String userJoined = "userJoined";
    static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    public static String getToken() {
        return sharedpreferences.getString(userToken, "");
    }

    public static void updateProfile(LoginDetails details) {
        editor.putBoolean(isLoggedIn, true);
        editor.putString(userToken, details.access_token);
        editor.putString(userEmail, details.email);
        editor.putString(userName, details.name);
        editor.putString(userJoined, details.created_at);
        editor.commit();
    }

    public static boolean isLoggedIn() {
        return sharedpreferences.getBoolean(isLoggedIn, false);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }
}
