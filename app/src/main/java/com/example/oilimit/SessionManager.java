package com.example.oilimit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.oilimit.model.cari.CariData;
import com.example.oilimit.model.login.LoginData;

import java.util.HashMap;

public class SessionManager {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID = "user_id";
    public static final String HANDPHONE = "handphone";
    public static final String NAMA = "nama";
    public static final String PASSWORDD = "passwordd";
    public static final String LITER = "liter";

    public static final String IS_SCANED = "isScaned";
    public static final String NAMAA = "namaa";
    public static final String LITERR = "literr";

    public SessionManager (Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createSession(String liter, String nama){
        editor.putBoolean(IS_SCANED, true);
        editor.putString(LITERR, liter);
        editor.putString(NAMAA, nama);
        editor.apply();
    }

    public void createCariSession(CariData user){
        editor.putBoolean(IS_SCANED, true);
        editor.putString(NAMAA, user.getNamaa());
        editor.putString(LITERR, user.getLiterr());
        editor.commit();
    }

    public HashMap<String,String> getUserScanDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAMAA, sharedPreferences.getString(NAMAA, null));
        user.put(LITERR, sharedPreferences.getString(LITERR, null));
        return user;
    }


    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getUserId());
        editor.putString(HANDPHONE, user.getUserId());
        editor.putString(NAMA, user.getNama());
        editor.putString(PASSWORDD, user.getPasswordd());
        editor.putString(LITER, user.getLiter());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(HANDPHONE, sharedPreferences.getString(USER_ID, null));
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(PASSWORDD, sharedPreferences.getString(PASSWORDD, null));
        user.put(LITER, sharedPreferences.getString(LITER, null));
        return user;
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public boolean isScaned(){
        return sharedPreferences.getBoolean(IS_SCANED, false);
    }

}
