package be.webfactor.polyglot.service.exercise.impl;

import android.os.Handler;
import be.webfactor.polyglot.domain.Exercise;
import be.webfactor.polyglot.domain.Playback;
import be.webfactor.polyglot.domain.Preferences;
import be.webfactor.polyglot.service.exercise.ExerciseActivity;
import be.webfactor.polyglot.service.exercise.ExerciseRunner;
import be.webfactor.polyglot.service.preferences.PreferencesServiceFactory;
import be.webfactor.polyglot.service.speak.OnSpeechHandler;
import be.webfactor.polyglot.service.speak.SpeachService;
import be.webfactor.polyglot.service.speak.SpeachServiceFactory;
import be.webfactor.polyglot.service.translation.TranslationService;
import be.webfactor.polyglot.service.translation.TranslationServiceFactory;
import be.webfactor.polyglot.service.word.SpokenWord;

public class ExerciseRunnerImpl implements ExerciseRunner, OnSpeechHandler {

	private boolean running;
	private ExerciseActivity activity;
	private TranslationService translationService;
	private SpeachService speakService;
	
	public ExerciseRunnerImpl(ExerciseActivity activity) {
		this.activity = activity;
		speakService = SpeachServiceFactory.forActivity(activity);
		speakService.setSpeechHandler(this);
	}
	
	public void speakSomething() {
		SpokenWord word = translationService.getNextWord();
		speakService.speak(word);
		activity.setExerciseProgress(translationService.getProgress());
	}

	public void start(Exercise exercise) {
		Preferences preferences = PreferencesServiceFactory.forContext(activity).getPreferences();
		Playback playback = preferences.getPlayback();
		boolean repeated = preferences.isRepeatable();
		translationService = TranslationServiceFactory.forPlaybackMode(playback, exercise, repeated);
		speakSomething();
		running = true;
	}

	public void stop() {
		speakService.stop();
		running = false;
	}

	public void onSpeechFinished(SpokenWord spokenWord) {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				if(translationService.isFinished()) {
					activity.stopSpeaking();
					return;
				}
				if(running) {
					speakSomething();
				}
			}
		}, PreferencesServiceFactory.forContext(activity).getPreferences().getWaitMillis());
	}
	
	public void onSpeechStarted(SpokenWord spokenWord) {
		activity.setCurrentWord(spokenWord.getWord());
	}

}
