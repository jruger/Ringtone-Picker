package com.rugers285.ringtonePicker;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Utils {

	static int getIntPref(Context context, String name, int def) {
		SharedPreferences prefs = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		if (prefs.contains(setDurationActivity.SET_DURATION_KEY)) {
			Log.i("getIntPref", "Found SET_DURATION_KEY");
			return prefs.getInt(name, def);
		} else {
			return prefs.getInt(name, 16);
		}

	}

	// Store int prefs
	static void setIntPref(Context context, String name, int value) {
		SharedPreferences prefs = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt(name, value);
		editor.commit();
	}
	
	static void setLists(int a){
		if (a==0){
			ringtonePickerSetPlaylist.songArray = new ArrayList<String>();
			ringtonePickerSetPlaylist.playlist = new ArrayList<String>();
			ringtonePickerMain.time = 1;
		}
		}
}
