package org.atctech.sms_accountant.preferences;

import android.content.SharedPreferences;

/**
 * Created by Jacky on 3/3/2018.
 */

public class Session {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static Session INSTANCE = null;

    private Session(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized Session getInstance(SharedPreferences prefs)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Session(prefs);
        }
        return INSTANCE;
    }



    public void setLoggedIn(boolean isLogged)
    {
        editor.putBoolean("loggedIn",isLogged);
        editor.commit();
    }

    public boolean isLoggedIn()
    {
        return prefs.getBoolean("loggedIn",false);
    }




    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean("IS_FIRST_TIME_LAUNCH", isFirstTimeLaunch);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }
}
