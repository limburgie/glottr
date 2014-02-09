package be.webfactor.polyglot.service.translation;

import be.webfactor.polyglot.service.word.SpokenWord;

public interface TranslationService {

	public static final int MAX_PROGRESS = 10000;
	public static final int NO_PROGRESS = -1;
	
	SpokenWord getNextWord();
	
	boolean isFinished();
	
	int getProgress();
	
}
