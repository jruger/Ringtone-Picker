package com.rugers285.ringtonePicker;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class setDurationActivity extends PreferenceActivity {
	final static String SET_DURATION_KEY = "set_duration";
	boolean change = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sliderpreference);
        if(change){
    		finish();
    		}	
        PreferenceScreen screen = this.getPreferenceScreen();

        Preference raction = (Preference) screen.findPreference(SET_DURATION_KEY);

    	raction.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
    	public boolean onPreferenceChange(Preference preference, Object newValue) {
    		change = true;
    		return true;
    	}
    	});
    	
    	

               
	}
}
