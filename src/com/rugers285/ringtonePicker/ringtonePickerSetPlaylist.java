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
import android.widget.Toast;

public class ringtonePickerSetPlaylist extends Activity {
	ListView musiclist;
	Cursor musiccursor;
	int music_column_index;
	int count;
	static MediaPlayer mMediaPlayer;
	static List<String> songArray;
	static List<String> playlist;
	static int i = 0;
	static String debug = "SetPlaylistOnCreate";
	static int grrr = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(debug,"saveInstance");
		setContentView(R.layout.mediastore);
		Log.i(debug,"Set Content View");
		init_phone_music_grid();
		Log.i(debug,"call music grid");
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.playlistmenu, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.setPlaylist:
			startActivity(new Intent(getApplicationContext(),
					ringtonePickerSetPlaylist.class));
			return true;

		case R.id.clearPlaylist:
			showDialog(clearPlaylistAlert);
			grrr=0;
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

	/**
	 * Method:init_phone_music_grid()
	 * Params:n/a
	 * Purpose:set up the media store and create a list view of all the audio items on sd card
	 * Output:List View
	 */
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

	/**
	 * Method:playAudio()
	 * Params:List<String> int
	 * Purpose:to play songs for duration of 25 seconds then stop 
	 * Output: song played 
	 */
	public static void playAudio(List<String> songArray2, int i2) {

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
			mMediaPlayer.seekTo(Utils.getIntPref(ringtonePickerMain.context, "TIMER", 0));
			Log.i(debug,"seekToMediaPlayer");
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
			if(grrr==0){
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
			grrr=1;
			}else{
				Context context = getApplicationContext();
				CharSequence text = "For the Free Alpha Release only one song is allowed in the playlist\n To see your currently selected song please visit the View Playlist in the menu";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
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
