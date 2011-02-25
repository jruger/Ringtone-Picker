package com.rugers285.ringtonePicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class smsReciever extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.d("SMSReciever", "Recieved text");
	      ringtonePickerSetPlaylist.playAudio(ringtonePickerSetPlaylist.songArray, ringtonePickerSetPlaylist.i);
		
	}

}
