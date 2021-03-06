package be.webfactor.polyglot.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.webfactor.polyglot.R;
import be.webfactor.polyglot.domain.Exercise;
import be.webfactor.polyglot.service.exercise.ExerciseActivity;
import be.webfactor.polyglot.service.exercise.ExerciseRunner;
import be.webfactor.polyglot.service.exercise.ExerciseRunnerFactory;
import be.webfactor.polyglot.service.translation.TranslationService;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class InExerciseActivity extends ExerciseActivity {
	
	private ExerciseRunner runner;
	private TextView spokenWord;
	private ProgressBar progressBar;
	
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
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
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

	public void setExerciseProgress(int progress) {
		if(progress == TranslationService.NO_PROGRESS) {
			progressBar.setVisibility(View.GONE);
		} else {
			progressBar.setProgress(progress);
		}
	}

}
