package com.postit.postit_.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {

        prefs = context.getSharedPreferences("UserData" , 0);
    }

    public void setSaved (boolean flag)
    {
        prefs.edit().putBoolean( "Saved"  , flag).commit();
    }

    public boolean getSaved ()
    {
        return prefs.getBoolean("Saved" , false);
    }

    public void LogOut () // to delete the log in info
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


    // to set the type of the user admin or registered student
    public void setType (String Type) //
    {
        prefs.edit().putString("Type" , Type).commit();
    }



    public String getType ()
    {
        return prefs.getString("Type" , "");
    }



}