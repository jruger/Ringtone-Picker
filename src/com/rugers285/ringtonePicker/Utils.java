package com.rugers285.ringtonePicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Utils {

	static int getIntPref(Context context, String name, int def) {
		SharedPreferences prefs =
		context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		if(prefs.contains(setDurationActivity.SET_DURATION_KEY)){
			Log.i("getIntPref","Found SET_DURATION_KEY");
			return prefs.getInt(name, def);
		}else{
			return prefs.getInt(name, 16);
		}
		
		}

	
}
