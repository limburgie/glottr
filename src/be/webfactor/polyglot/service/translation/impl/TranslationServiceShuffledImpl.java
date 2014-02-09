package be.webfactor.polyglot.service.translation.impl;

import java.util.Collections;
import java.util.List;

import be.webfactor.polyglot.domain.Exercise;

public class TranslationServiceShuffledImpl extends TranslationServiceResourceBundleImpl {

	private List<String> keys;
	
	public TranslationServiceShuffledImpl(Exercise exercise, boolean repeated) {
		super(exercise, repeated);
		resetKeys();
	}

	protected String getNextKey() {
		if(isLoopFinished()) {
			resetKeys();
		}
		Collections.shuffle(keys);
		String key = keys.get(0);
		keys.remove(key);
		return key;
	}
	
	protected boolean isLoopFinished() {
		return keys.isEmpty();
	}

	private void resetKeys() {
		keys = getKeys(exercise.getFrom());
	}

	public int getProgress() {
		return 0;
	}

}
