package com.rugers285.ringtonePicker;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class setDurationActivity extends PreferenceActivity {
	final static String SET_DURATION_KEY = "set_duration";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sliderpreference);
        
        PreferenceScreen screen = this.getPreferenceScreen();

        Preference raction = (Preference) screen.findPreference();

    	raction.setOnPreferenceChangeListener(new OnPreferenceChangeListener(SET_DURATION_KEY) {
    	public boolean onPreferenceChange(Preference preference, Object newValue) {
    	return true;
    	}
    	});
               
	}
}
