package nexustech.com.ng.nexusquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPref {
    // LogCat tag
    private static String TAG = SharedPref.class.getSimpleName();
    private static SharedPref mInstance;

    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }


    SharedPreferences pref;
    Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences constants
    private static final String PREF_NAME = "EDG";
    private static final String KEY_QUEST = "question";
    private static final String KEY_IP = "ipaddress";
    private static final String KEY_SCH = "school";
    private static final String KEY_STAGE = "stage";

    public SharedPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setQuest(String quest) {

        editor.putString(KEY_QUEST, quest);
        // commit changes
        editor.commit();
    }


    public String getQuest() {
        return pref.getString(KEY_QUEST, null);
    }

    public void setSch(String quest) {

        editor.putString(KEY_SCH, quest);
        // commit changes
        editor.commit();
    }


    public String getSch() {
        return pref.getString(KEY_SCH, null);
    }

    public void setIP(String ip) {

        editor.putString(KEY_IP, ip);
        // commit changes
        editor.commit();
    }


    public String getIP() {
        return pref.getString(KEY_IP, null);
    }

    public void setStage(String stage1) {

        editor.putString(KEY_STAGE, stage1);
        // commit changes
        editor.commit();
    }

    public String getStage() {
        return pref.getString(KEY_STAGE, null);
    }
}
