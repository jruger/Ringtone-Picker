package com.rugers285.ringtonePicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ringtonePickerViewPlaylist extends Activity {
	String tag = "ViewPlaylist";
	Cursor musiccursor;
	int music_column_index;
	int count;
	ListView musiclist;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(tag, "entered Ringtone view Playlist");
		super.onCreate(savedInstanceState);
		Log.i(tag, "create contentview");
		setContentView(R.layout.viewplaylist);
		Log.i(tag, "create song array");
		listingPlaylist();
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
									startActivity(new Intent(getApplicationContext(),
											ringtonePickerViewPlaylist.class));
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

	private void listingPlaylist() {
		Log.i("lp", "Entered");
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.info);
		Log.i("lp", "created layout");
		Log.i("lp", "created text view");

		if (ringtonePickerMediaStore.playlist.isEmpty()) {
			TextView valueTV = new TextView(this);
			valueTV.setText("Your Playlist is Empty. Please select Set Playlist from the Menu!");
			valueTV.setId(5);
			((LinearLayout) linearLayout).addView(valueTV);
		} else {

			for (int i = 0; i < ringtonePickerMediaStore.playlist.size(); i++) {
				TextView valueTV = new TextView(this);
				valueTV.setText(ringtonePickerMediaStore.playlist.get(i));
				valueTV.setId(5);
				((LinearLayout) linearLayout).addView(valueTV);
			}
			
			TextView valueTV = new TextView(this);
			valueTV.setText(Utils.getIntPref(this, setDurationActivity.SET_DURATION_KEY, 15));
			valueTV.setId(5);
			((LinearLayout) linearLayout).addView(valueTV);
		}

	}
}