package com.the4k.milkteashop.utlis;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

public class PreferenseManager {

    private static final String PREF_NAME = "ordering";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_USER_ID = "User UID";
    private static PreferenseManager mInstance;
    private static Context mContext;
    private FirebaseUser user;

    public PreferenseManager(Context context){
        mContext = context;
    }

    public synchronized PreferenseManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferenseManager(context);
        }
        return mInstance;
    }


    public Boolean UserLogin(String Email,String User_UID){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL, Email);
        editor.putString(KEY_USER_ID,User_UID);


        editor.apply();
        return true;
    }

    public Boolean IsUserLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    public Boolean IsLogout(){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        return true;
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);
    }


}

