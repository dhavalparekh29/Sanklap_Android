package com.universalapp.sankalp.learningapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {

    private static final String APP_SETTINGS = "SankalpPref";
    private static SharedPreferences appSharedPrefs;
    private static  SharedPreferences.Editor prefsEditor;
    private static Context mContext;

    public static AppPrefs instance;

    // properties
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String IS_OTP_VERIFIED = "IS_OTP_VERIFIED";

    private static final String LOGIN_USER_DETAILS = "LOGIN_USER_DETAILS";
    private static final String IS_REMEMBER = "IS_REMEMBER";
    private static final String REMEMBER_EMAIL  = "REMEMBER_EMAIL";
    private static final String REMEMBER_PASSWORD  = "REMEMBER_PASSWORD";
    // other properties...


    private AppPrefs() {

    }

    public AppPrefs(Context context) {
        // TODO Auto-generated constructor stub
        this.appSharedPrefs = context.getSharedPreferences(APP_SETTINGS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }


    public static AppPrefs getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new AppPrefs();
        }
        appSharedPrefs = context.getSharedPreferences(APP_SETTINGS, Activity.MODE_PRIVATE);
        prefsEditor = appSharedPrefs.edit();
        return instance;
    }

    public void setIsLogin(boolean status) {
        // TODO Auto-generated method stub
        prefsEditor.putBoolean(IS_LOGIN, status).commit();
    }

    public Boolean getIsLogin() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getBoolean(IS_LOGIN, false);
    }
    public void setIsOtpVerified(boolean status) {
        // TODO Auto-generated method stub
        prefsEditor.putBoolean(IS_OTP_VERIFIED, status).commit();
    }

    public Boolean getOTPVerificed() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getBoolean(IS_OTP_VERIFIED, false);
    }


    public void setLoginUserDetails(String details) {
        // TODO Auto-generated method stub
        prefsEditor.putString(LOGIN_USER_DETAILS, details).commit();
    }

    public String getLoginUserDetails() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(LOGIN_USER_DETAILS, "");

    }

    public void setRememberEmail(String email) {
        // TODO Auto-generated method stub
        prefsEditor.putString(REMEMBER_EMAIL, email).commit();
    }

    public String getRememberEmail() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(REMEMBER_EMAIL, "");

    }

    public void setRememberPassword(String password) {
        // TODO Auto-generated method stub
        prefsEditor.putString(REMEMBER_PASSWORD, password).commit();
    }

    public String getRememberPassword() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(REMEMBER_PASSWORD, "");

    }
    public void setIsRemember(boolean remember) {
        // TODO Auto-generated method stub
        prefsEditor.putBoolean(IS_REMEMBER, remember).commit();
    }

    public boolean getIsRemember() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getBoolean(IS_REMEMBER, false);

    }
}
