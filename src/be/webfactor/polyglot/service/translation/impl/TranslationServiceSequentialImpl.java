package be.webfactor.polyglot.service.translation.impl;

import java.util.List;

import be.webfactor.polyglot.domain.Exercise;

public class TranslationServiceSequentialImpl extends TranslationServiceResourceBundleImpl {

	private int index;
	private List<String> keys;
	
	public TranslationServiceSequentialImpl(Exercise exercise, boolean repeated) {
		super(exercise, repeated);
		keys = getKeys(exercise.getFrom());
	}

	protected String getNextKey() {
		if(isLoopFinished()) {
			index = 0;
		}
		return keys.get(index++);
	}
	
	protected boolean isLoopFinished() {
		return index == keys.size();
	}

	public int getProgress() {
		return Math.round((float) MAX_PROGRESS * index / keys.size());
	}
	
}
