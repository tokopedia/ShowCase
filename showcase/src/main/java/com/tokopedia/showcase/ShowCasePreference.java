package com.tokopedia.showcase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hendry on 4/11/2017.
 */

public class ShowCasePreference {
    private static final String SHOWCASE_PREFERENCES = "show_case_pref";

    public static boolean hasShown(Context context, String tag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHOWCASE_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(tag, false);
    }

    public static void setShown(Context context, String tag, boolean hasShown){
        SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(SHOWCASE_PREFERENCES,
                Context.MODE_PRIVATE).edit();
        sharedPreferencesEditor.putBoolean (tag, hasShown);
        sharedPreferencesEditor.apply();
    }
}
