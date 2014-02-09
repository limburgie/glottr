package be.webfactor.polyglot.service.translation;

import be.webfactor.polyglot.service.word.SpokenWord;

public interface TranslationService {

	SpokenWord getNextWord();
	
	boolean isFinished();
	
	int getProgress();
	
}
