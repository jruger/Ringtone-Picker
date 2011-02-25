package com.rugers285.ringtonePicker;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/*
 This is the PhoneStateListener class. This listens for a callStateChange and if it is ringing 
 calls the playAudio
 */
public class myPhoneStateListener extends PhoneStateListener {

	String DEBUG = "myPhoneStateListener";

	public void onCallStateChanged(int state, String incomingNumber) {

		switch (state) {

		case TelephonyManager.CALL_STATE_IDLE:

			Log.d(DEBUG, "IDLE");

			break;

		case TelephonyManager.CALL_STATE_OFFHOOK:

			Log.d(DEBUG, "OFF HOOK");

			break;

		case TelephonyManager.CALL_STATE_RINGING:

			Log.d(DEBUG, "RINGING");
			ringtonePickerSetPlaylist.playAudio(
					ringtonePickerSetPlaylist.songArray, 0);
			break;

		}

	}

}
