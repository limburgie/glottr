package be.webfactor.polyglot.service.translation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.webfactor.polyglot.activity.InExerciseActivity;
import be.webfactor.polyglot.domain.Exercise;

public class TranslationServiceRandomImpl extends TranslationServiceResourceBundleImpl {

	public TranslationServiceRandomImpl(Exercise exercise, boolean repeated) {
		super(exercise, repeated);
	}

	protected String getNextKey() {
		List<String> keyList = new ArrayList<String>(getBundle(exercise.getFrom()).keySet());
		Collections.shuffle(keyList);
		return keyList.get(0);
	}
	
	protected boolean isLoopFinished() {
		return false;
	}

	public int getProgress() {
		return InExerciseActivity.PROGRESS_DISABLED;
	}

}
