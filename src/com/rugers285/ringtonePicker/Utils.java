package com.rugers285.ringtonePicker;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.util.Log;

public class Utils {

	/**
	 * Method: getIntPref()
	 * Params: context, string, int
	 * Purpose: Returns the integer stored in the preference
	 * Output: The integer stored in the pref
	 */
	static int getIntPref(Context context, String name, int def) {
		SharedPreferences prefs = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
			return prefs.getInt(name, def);
		}

	/**
	 * Method: setIntPref()
	 * Params: context,string,int
	 * Purpose: Stores an int in the pref titled name
	 * Output: N/A
	 */
	static void setIntPref(Context context, String name, int value) {
		SharedPreferences prefs = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt(name, value);
		editor.commit();
	}
	
	/**
	 * Method: setLists
	 * Params: int
	 * Purpose: creates everything needed to keep track at the beginning
	 * Output: N/A
	 */
	static void setLists(int a){
		if (a==0){
			ringtonePickerSetPlaylist.songArray = new ArrayList<String>();
			ringtonePickerSetPlaylist.playlist = new ArrayList<String>();
			ringtonePickerSetPlaylist.mMediaPlayer = new MediaPlayer();
			ringtonePickerMain.time = 1;
		}
		}
}
