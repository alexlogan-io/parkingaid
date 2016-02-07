package parkingaid.lpg.com.parkingadd.framework;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Deals with saving and deleting data from the shared preferences
 */
public class SharedPreferenceController {

    private SharedPreferences prefs;

    /**
     * Constructor for a SharedPreferenceController
     * @param context current activity context
     */
    public SharedPreferenceController(Context context) {
        this.prefs = context.getSharedPreferences("Preferences", context.MODE_PRIVATE);
    }

    /**
     * Establishes that the first time launch of an app is complete and sets the boolean to
     * false so that the app knows to get the data from the shared preferences on app reload
     */
    public void firstLaunchDone() {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstTime",false);
        edit.apply();
    }

    /**
     * Save info to the shared preferences as a JSON object
     * @param object the JSON object
     * @param tag the tag relating to the JSON object stored in shared preferences
     */
    public void saveLocalInfo(Object object, String tag){
        SharedPreferences.Editor edit = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        edit.putString(tag, json);
        edit.apply();
    }

    /**
     * Saves a string to the shared preferences
     * @param tag relates to the string to be saved
     * @param string the string to be saved
     */
    public void saveString(String tag, String string){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(tag,string);
        edit.apply();
    }

    /**
     * Set the boolean relating to the first time launch back to true
     */
    public void clearBool(){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstTime", true);
        edit.apply();
    }

    /**
     * Clears the current shared preferences
     */
    public void clearData(){
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
    }

}
