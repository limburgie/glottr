package be.webfactor.polyglot.activity;

import be.webfactor.polyglot.R;
import be.webfactor.polyglot.service.preferences.PreferencesServiceFactory;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private static final int[] PREF_KEYS = new int[] {R.string.speed, R.string.word_playback, R.string.repeated};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);

		for (int prefKey : PREF_KEYS) {
			String key = getResources().getString(prefKey);
			onSharedPreferenceChanged(getPreferenceManager()
					.getSharedPreferences(), key);
		}
	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference preference = findPreference(key);
		if(preference instanceof ListPreference) {
			CharSequence value = ((ListPreference)preference).getEntry();
			preference.setSummary(value);
			if("Word Playback".equals(key)) {
				CheckBoxPreference repeated = (CheckBoxPreference) findPreference("Repeated");
				repeated.setEnabled(!value.equals("Random"));
			}
		} else if(preference instanceof CheckBoxPreference) {
			String yes = getResources().getString(R.string.yes);
			String no = getResources().getString(R.string.no);
			boolean checked = ((CheckBoxPreference)preference).isChecked();
			preference.setSummary(checked ? yes : no);
		}
		PreferencesServiceFactory.forContext(this).reset();
	}
	
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		super.onResume();
	}
	
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

}
