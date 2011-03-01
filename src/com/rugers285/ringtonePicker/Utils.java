package com.rugers285.ringtonePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.util.Log;

public class Utils {
	static MediaPlayer mMediaPlayer;
	static int i = 0;

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
			mMediaPlayer = new MediaPlayer();
			ringtonePickerMain.time = 1;
		}
		}
	
	/**
	 * Method:playAudio()
	 * Params:List<String> int
	 * Purpose:to play songs for duration of 25 seconds then stop 
	 * Output: song played 
	 */
	public static void playAudio(List<String> songArray2, int i2) {

		Log.i("songArray", "Number of Songs: " + songArray2.size());
        String debug = "playAudio";
        final Handler mHandler = new Handler();
		try {
			Log.i("MediaPalyer", "Song Number: " + i2);
			mMediaPlayer.reset();
			Log.i(debug,"resetMediaPlayer");
			Log.i(debug,"setting data source: " + songArray2.get(i2));
			mMediaPlayer.setDataSource(songArray2.get(i2));
			Log.i(debug,"setMediaPlayerDataSource");
			mMediaPlayer.prepare();
			Log.i(debug,"prepareMediaPlayer");
			mMediaPlayer.seekTo(Utils.getIntPref(ringtonePickerMain.context, "TIMER", 0));
			Log.i(debug,"seekToMediaPlayer at time: " + Utils.getIntPref(ringtonePickerMain.context, "TIMER", 0));
			mMediaPlayer.start();
			Log.i(debug,"startMediaPlayer");
			
			new Thread(new Runnable() {
				@Override
				public void run() {
				try {
				Thread.sleep(ringtonePickerMain.duration);
				mHandler.post(new Runnable() {

				@Override
				public void run() {
					
				Utils.setIntPref(ringtonePickerMain.context, "TIMER", mMediaPlayer.getCurrentPosition());
				Log.i("thread","current postion before stop is: " + mMediaPlayer.getCurrentPosition());
				mMediaPlayer.stop();
				}
				});
				} catch (Exception e) {
				}
				}
				}).start();


			mMediaPlayer.setOnCompletionListener(listener);

		} catch (IOException e) {
			Log.w("IO", "IO Exception Thrown");
		} catch (IllegalArgumentException e) {
			Log.w("IllegalArguement", "IllegalArguement Thrown");
		} catch (Exception e) {

		}
	}

	private static OnCompletionListener listener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			i++;
			if (i == ringtonePickerSetPlaylist.songArray.size()) {
				Log.i("Play Audio", "Reset i");
				i = 0;
				Utils.setIntPref(ringtonePickerMain.context, "TIMER", 0);
			}
			Log.i("onCompletionListener", "Song Completed: i is" + i);
			playAudio(ringtonePickerSetPlaylist.songArray, i);

		}
	};
}
