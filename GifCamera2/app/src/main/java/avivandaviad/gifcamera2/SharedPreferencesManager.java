package avivandaviad.gifcamera2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Aviad on 14/08/2017.
 */

public class SharedPreferencesManager {

    public static final String KEY_DURATION_BETWEEN_FRAMES = "KEY_DURATION_BETWEEN_FRAMES";
    public static final String KEY_DURATION_FOR_FRAME ="KEY_DURATION_FOR_FRAME" ;
    public static final String KEY_FRAME_COUNT = "KEY_FRAME_COUNT";
    public static final String KEY_TITLE = "KEY_TITLE";


    public static void saveValue(Context context,String key,String value){
        SharedPreferences sharedPref = context.getSharedPreferences("AppPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String loadValue(Context context,String key){
        SharedPreferences sharedPref = context.getSharedPreferences("AppPreferences",Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }


}
