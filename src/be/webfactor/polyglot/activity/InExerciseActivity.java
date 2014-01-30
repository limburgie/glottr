package be.webfactor.polyglot.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import be.webfactor.polyglot.R;
import be.webfactor.polyglot.domain.Exercise;
import be.webfactor.polyglot.service.exercise.ExerciseActivity;
import be.webfactor.polyglot.service.exercise.ExerciseRunner;
import be.webfactor.polyglot.service.exercise.ExerciseRunnerFactory;

public class InExerciseActivity extends ExerciseActivity {
	
	private ExerciseRunner runner;
	private TextView spokenWord;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupLayout();

		Exercise exercise = (Exercise) getIntent().getSerializableExtra(Exercise.class.getName());
		runner = ExerciseRunnerFactory.forActivity(this);
		runner.start(exercise);
		
		initializeAds();
		keepScreenActive();
	}
	
	private void keepScreenActive() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void initializeAds() {
		AdView adView = (AdView)this.findViewById(R.id.ad_view);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setupLayout();
		initializeAds();
	}
	
	public void onBackPressed() {
		runner.stop();
		Intent intent = new Intent(this, PreparationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
	
	private void setupLayout() {
		setContentView(R.layout.in_exercise);
		spokenWord = (TextView) findViewById(R.id.spoken_word);
	}
	
	public void stopSpeaking(View view) {
		onBackPressed();
	}
	
	public void stopSpeaking() {
		onBackPressed();
	}

	public void setCurrentWord(String word) {
		spokenWord.setText(word);
	}

}
