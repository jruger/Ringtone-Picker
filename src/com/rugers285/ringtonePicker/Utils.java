package com.rugers285.ringtonePicker;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

	static int getIntPref(Context context, String name, int def) {
		SharedPreferences prefs =
		context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return prefs.getInt(name, def);
		}

	
}
