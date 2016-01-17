package com.example.theone.virtualbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "rohit";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, String username, String type) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString("username", username);
        editor.putString("type", type);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setDetails(String username, String pass, String mob, String email, String Account) {


        editor.putString("password", pass);
        editor.putString("mobile", mob);
        editor.putString("email", email);
        editor.putString("Account", Account);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setChildDetails(String username, String pass,  String email, String Account, String parent) {


        editor.putString("cpassword", pass);
        editor.putString("cusername", username);
        editor.putString("cemail", email);
        editor.putString("cAccount", Account);
        editor.putString("cparent", parent);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String Getusername() {
        return pref.getString("username", "fasd");
    }

    public String Getpass() {
        return pref.getString("password", null);
    }

    public String Getemail() {
        return pref.getString("email", null);
    }

    public String GetMobile() {
        return pref.getString("mobile", null);
    }

    public String GetAccount() {
        return pref.getString("Account", null);
    }

    public String GetChildAccount() {
        return pref.getString("cAccount", null);
    }


    public String GetChildusername() {
        return pref.getString("cusername", null);
    }

    public String GetChildpass() {
        return pref.getString("cpassword", null);
    }

    public String GetChildemail() {
        return pref.getString("cemail", null);
    }
    public String GetChildparent() {
        return pref.getString("cparent", null);
    }
}
