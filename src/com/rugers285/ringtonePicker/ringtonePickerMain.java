package com.rugers285.ringtonePicker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ringtonePickerMain extends Activity {
	/** Called when the activity is first created. */
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
		ringtonePickerMediaStore.songArray = new ArrayList<String>();
		ringtonePickerMediaStore.playlist = new ArrayList<String>();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.playlistmenu, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.setPlaylist:
			startActivity(new Intent(getApplicationContext(),
					ringtonePickerMediaStore.class));
			return true;

		case R.id.setDuration:
			return true;

		case R.id.clearPlaylist:
			showDialog(clearPlaylistAlert);
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
									ringtonePickerMediaStore.playlist.clear();
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
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main);
		TextView checkExternalAvailability = new TextView(this);

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
			checkExternalAvailability.setText("TRUE");
			// Read more:
			// http://www.brighthub.com/mobile/google-android/articles/48845.aspx#ixzz1E3YdaV2f
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
			checkExternalAvailability.setText("TRUE");
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
			checkExternalAvailability.setText("FALSE");
		}
		checkExternalAvailability.setId(5);
		linearLayout.addView(checkExternalAvailability);
	}
}
