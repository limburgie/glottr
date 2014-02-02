package be.webfactor.polyglot.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import be.webfactor.polyglot.R;
import be.webfactor.polyglot.domain.Exercise;
import be.webfactor.polyglot.domain.Language;
import be.webfactor.polyglot.domain.Topic;
import be.webfactor.polyglot.service.translation.CurrentLanguageContainer;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class PreparationActivity extends Activity {

	private static final Topic DEFAULT_TOPIC = Topic.COUNTING;
	private static final Language DEFAULT_FROM_LANGUAGE = Language.EN;
	private static final Language DEFAULT_TO_LANGUAGE = Language.ES;
	
	private static final String TOPIC_PREF_KEY = "TOPIC";
	private static final String FROM_LANG_PREF_KEY = "FROM_LANG";
	private static final String TO_LANG_PREF_KEY = "TO_LANG";

	private Spinner fromLanguageSpinner;
	private Spinner toLanguageSpinner;
	private Spinner topicSpinner;

	private int currentTopicIndex;
	private int currentFromIndex;
	private int currentToIndex;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeSpinnerValues();
		setupLayout();
		initializeAds();
	}

	private void initializeSpinnerValues() {
		currentTopicIndex = getEnumPreference(TOPIC_PREF_KEY, DEFAULT_TOPIC, Topic.class);
		currentFromIndex = getEnumPreference(FROM_LANG_PREF_KEY, DEFAULT_FROM_LANGUAGE, Language.class);
		currentToIndex = getEnumPreference(TO_LANG_PREF_KEY, DEFAULT_TO_LANGUAGE, Language.class);
	}

	private void initializeAds() {
		AdView adView = (AdView) this.findViewById(R.id.ad_view);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setupLayout();
		initializeAds();
	}

	private void setupLayout() {
		setContentView(R.layout.preparation);

		fromLanguageSpinner = (Spinner) findViewById(R.id.from_language);
		toLanguageSpinner = (Spinner) findViewById(R.id.to_language);
		topicSpinner = (Spinner) findViewById(R.id.exercise);

		fromLanguageSpinner.setAdapter(createEnumArrayAdapter(Language.class));
		fromLanguageSpinner.setSelection(currentFromIndex);
		toLanguageSpinner.setAdapter(createEnumArrayAdapter(Language.class));
		toLanguageSpinner.setSelection(currentToIndex);
		topicSpinner.setAdapter(createEnumArrayAdapter(Topic.class));

		topicSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				currentTopicIndex = position;
				saveEnumPreference(TOPIC_PREF_KEY, currentTopicIndex, Topic.class);
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		fromLanguageSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Language lang = (Language) parent
								.getItemAtPosition(position);
						CurrentLanguageContainer.set(lang);
						topicSpinner
								.setAdapter(createEnumArrayAdapter(Topic.class));
						topicSpinner.setSelection(currentTopicIndex);

						switchPositionsIfNecessary(toLanguageSpinner,
								currentFromIndex, position);
						currentFromIndex = position;
						
						saveEnumPreference(FROM_LANG_PREF_KEY, currentFromIndex, Language.class);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		toLanguageSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						switchPositionsIfNecessary(fromLanguageSpinner,
								currentToIndex, position);
						currentToIndex = position;
						saveEnumPreference(TO_LANG_PREF_KEY, currentToIndex, Language.class);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
	}
	
	private <T extends Enum<T>> int getEnumPreference(String key, Enum<T> defaultValue, Class<T> enumType) {
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String result = prefs.getString(key, defaultValue.name());
		try {
			return Enum.valueOf(enumType, result).ordinal();
		} catch(IllegalArgumentException e) {
			return defaultValue.ordinal();
		}
	}
	
	private <T extends Enum<T>> void saveEnumPreference(String key, int index, Class<T> enumType) {
		Editor prefs = getPreferences(MODE_PRIVATE).edit();
		prefs.putString(key, enumType.getEnumConstants()[index].name());
		prefs.commit();
	}
	
	private void switchPositionsIfNecessary(Spinner target, int otherPosition,
			int newPosition) {
		if (newPosition == target.getSelectedItemPosition()) {
			target.setSelection(otherPosition);
		}
	}

	private <T extends Enum<T>> ArrayAdapter<T> createEnumArrayAdapter(
			Class<T> enumType) {
		ArrayAdapter<T> adapter = new ArrayAdapter<T>(this,
				android.R.layout.simple_spinner_item,
				enumType.getEnumConstants());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public void startSpeaking(View view) {
		Language from = (Language) fromLanguageSpinner.getSelectedItem();
		Language to = (Language) toLanguageSpinner.getSelectedItem();
		Topic type = (Topic) topicSpinner.getSelectedItem();

		Exercise exercise = new Exercise(from, to, type);

		Intent i = new Intent(getApplicationContext(), InExerciseActivity.class);
		i.putExtra(Exercise.class.getName(), exercise);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (resultCode != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1);
		}
	}

}