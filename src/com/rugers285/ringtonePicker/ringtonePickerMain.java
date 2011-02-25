package com.rugers285.ringtonePicker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ringtonePickerMain extends Activity {
	/** Called when the activity is first created. */
	static int duration;
	static Context context;
	public static int time = 0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i("test", "entered correctly");
		String c = "onCREATE";
		super.onCreate(savedInstanceState);
		Log.i(c, "create saved instance state");
		setContentView(R.layout.main);
		Log.i(c, "setContentView");
		checkExternal();
		Log.i(c, "checked external");
		duration = 25000;
		ringtonePickerSetPlaylist.mMediaPlayer = new MediaPlayer();
		context = this;
		Utils.setLists(time);
		myPhoneStateListener phoneListener=new myPhoneStateListener();
		TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	    telManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.playlistmenu, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.i("OptionsMenu"," Item selected entered");

		switch (item.getItemId()) {
		case R.id.setPlaylist:
			Log.i("setPlaylist"," Item selected entered");
			startActivity(new Intent(getApplicationContext(),
					ringtonePickerSetPlaylist.class));
			return true;


		case R.id.clearPlaylist:
			showDialog(clearPlaylistAlert);
			ringtonePickerSetPlaylist.songArray = new ArrayList<String>();
			ringtonePickerSetPlaylist.grrr=0;
			return true;

		case R.id.viewPlaylist:
			startActivity(new Intent(getApplicationContext(),
					ringtonePickerViewPlaylist.class));
			return true;

		}

		return true;

	}

	final static int clearPlaylistAlert = 0;

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case clearPlaylistAlert:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to clear your playlist?")
					.setCancelable(true)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									ringtonePickerSetPlaylist.playlist.clear();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			dialog = alert;
			break;
		/*
		 * case DIALOG_GAMEOVER_ID: // do the work to define the game over
		 * Dialog break;
		 */
		default:
			dialog = null;
		}

		return dialog;
	}

	public void checkExternal() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
			Context context = getApplicationContext();
			CharSequence text = "Readable Storage Available";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			// Read more:
			// http://www.brighthub.com/mobile/google-android/articles/48845.aspx#ixzz1E3YdaV2f
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
			Context context = getApplicationContext();
			CharSequence text = "Readable Storage Available";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
			Context context = getApplicationContext();
			CharSequence text = "Readable Storage Not Available. Please Check SD card";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		}
	}
}
