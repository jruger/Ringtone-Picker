package com.rugers285.ringtonePicker;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class setDurationActivity extends PreferenceActivity {
	final static String SET_DURATION_KEY = "set_duration";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sliderpreference);
        
       
	}
}
