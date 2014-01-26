package be.webfactor.polyglot.service.preferences.impl;

import be.webfactor.polyglot.R;
import be.webfactor.polyglot.domain.Playback;
import be.webfactor.polyglot.domain.Preferences;
import be.webfactor.polyglot.service.preferences.PreferencesService;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesServiceImpl implements PreferencesService {

	private Preferences preferences;
	private SharedPreferences defaultPreferences;
	private Context context;
	
	public PreferencesServiceImpl(Context context) {
		this.context = context;
		reset();
	}

	public Preferences getPreferences() {
		if(preferences == null) {
			initPreferences();
		}
		return preferences;
	}

	private void initPreferences() {
		preferences = new Preferences();
		preferences.setPlayback(getPlaybackMode());
		preferences.setWaitMillis(getWaitMillis());
		preferences.setRepeatable(isRepeatable());
	}

	private boolean isRepeatable() {
		String repeatableKey = context.getResources().getString(R.string.repeated);
		return defaultPreferences.getBoolean(repeatableKey, Preferences.DEFAULT_REPEATABLE);
	}

	private int getWaitMillis() {
		String speedKey = context.getResources().getString(R.string.speed);
		String result = defaultPreferences.getString(speedKey, Integer.toString(Preferences.DEFAULT_WAIT_MILLIS));
		return Integer.parseInt(result);
	}

	private Playback getPlaybackMode() {
		String playbackKey = context.getResources().getString(R.string.word_playback);
		String playbackString = defaultPreferences.getString(playbackKey, Preferences.DEFAULT_PLAYBACK.name());
		return Playback.valueOf(playbackString);
	}

	public void reset() {
		preferences = null;
		defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
}
