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

/** Called when the activity is first created. */

public class ringtonePickerMain extends Activity {

	static int duration = 25000;// This is the duration the file plays for
	static Context context;
	public static int time = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		checkExternal();//checks if there is valid storage available

		context = this;
		
		Utils.setLists(time);//creates Lists and MediaPlayer Once when program is first ran
		
		
		/**
		 * Creates the phoneStateListener from the PhonestateListener class
		 */
		myPhoneStateListener phoneListener = new myPhoneStateListener();
		TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	/**
	 * Creates the option Menu whenever the menu button is called 
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.playlistmenu, menu);
		return true;

	}

	/**
	 * Tells what happens when the options menu item is pressed 
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.i("OptionsMenu", " Item selected entered");

		switch (item.getItemId()) {
		case R.id.setPlaylist:
			Log.i("setPlaylist", " Item selected entered");
			startActivity(new Intent(getApplicationContext(),
					ringtonePickerSetPlaylist.class));
			return true;

		case R.id.clearPlaylist:
			showDialog(clearPlaylistAlert);
			ringtonePickerSetPlaylist.songArray = new ArrayList<String>();
			ringtonePickerSetPlaylist.grrr = 0;
			return true;

		case R.id.viewPlaylist:
			startActivity(new Intent(getApplicationContext(),
					ringtonePickerViewPlaylist.class));
			return true;

		}

		return true;

	}

	final static int clearPlaylistAlert = 0;//Alert Dialog Switch case 

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		
		/**
		 * Asks user if they are sure they want to clear their play list. 
		 * If they select yes it clears the play list. If they select no it
		 * cancels the dialog
		 */
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
	
		default:
			dialog = null;
		}

		return dialog;
	}

	/**
	 * Method:Check External Params: N/A Purpose: Checks to see if there is a sd
	 * card Output: Toast Message telling whether sd card is present
	 */
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
