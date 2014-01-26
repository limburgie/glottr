package be.webfactor.polyglot.service.exercise;

import android.app.Activity;

public abstract class ExerciseActivity extends Activity {

	abstract public void setCurrentWord(String word);
	
	abstract public void stopSpeaking();
	
}
