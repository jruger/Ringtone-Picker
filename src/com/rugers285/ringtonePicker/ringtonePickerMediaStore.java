package com.rugers285.ringtonePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ringtonePickerMediaStore extends Activity {
	ListView musiclist;
	Cursor musiccursor;
	int music_column_index;
	int count;
	static MediaPlayer mMediaPlayer;
	static List<String> songArray;
	static List<String> playlist;
	static int i = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediastore);
		init_phone_music_grid();
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
			startActivity(new Intent(getApplicationContext(),
					setDurationActivity.class));
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

	private void init_phone_music_grid() {
		System.gc();
		String[] proj = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE };
		musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				proj, null, null, null);
		count = musiccursor.getCount();
		musiclist = (ListView) findViewById(R.id.mediastorelistview);
		musiclist.setAdapter(new MusicAdapter(getApplicationContext()));

		musiclist.setOnItemClickListener(musicgridlistener);
		mMediaPlayer = new MediaPlayer();
	}

	public static void playAudio(List<String> songArray2, int i2) {
		
		/*ok so do this
		right at the begining u start the track
		start the timer
		after the timer fires
		call getCurrentPosition()
		save that to a pfref and stop the track
		then on the next start call the pref seek to it
		and play
		timer again
		the if the track ends oncomplete load the next track
		and save the list position to a pref
		so you can remember what track its on
		and as perusal wait till the timer is up and stop and save to pref
		got me?*/

		Log.i("songArray", "Number of Songs: " + songArray.size());
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
				Utils.setIntPref(this.Context, "TIMER", mMediaPlayer.getCurrentPosition());
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
			if (i == songArray.size()) {
				Log.i("Play Audio", "Reset i");
				i = 0;
			}
			Log.i("onCompletionListener", "Song Completed: i is" + i);
			playAudio(songArray, i);

		}
	};
	private OnItemClickListener musicgridlistener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			System.gc();
			music_column_index = musiccursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
			musiccursor.moveToPosition(position);
			songArray.add(musiccursor.getString(music_column_index));
			Log.i("On click",
					"Added a Song:" + musiccursor.getString(music_column_index));
			music_column_index = musiccursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
			musiccursor.moveToPosition(position);
			String songname = musiccursor.getString(music_column_index);
			playlist.add(songname);
			Log.i("On click", "Added: " + songname);
		}
	};

	public class MusicAdapter extends BaseAdapter {
		private Context mContext;
		String DEBUG_TAG = "MusicAdapter";

		public MusicAdapter(Context c) {
			Log.i(DEBUG_TAG, "context");
			mContext = c;
		}

		public int getCount() {
			Log.i(DEBUG_TAG, "count");
			return count;
		}

		public Object getItem(int position) {
			Log.i(DEBUG_TAG, "item");
			return position;
		}

		public long getItemId(int position) {
			Log.i(DEBUG_TAG, "id");
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i(DEBUG_TAG, "view");
			System.gc();
			TextView tv = new TextView(mContext.getApplicationContext());
			String id = null;
			if (convertView == null) {
				music_column_index = musiccursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
				musiccursor.moveToPosition(position);
				id = musiccursor.getString(music_column_index);
				music_column_index = musiccursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
				musiccursor.moveToPosition(position);
				id += " Size(KB):" + musiccursor.getString(music_column_index);
				tv.setText(id);
			} else
				tv = (TextView) convertView;
			return tv;
		}
	}
}
