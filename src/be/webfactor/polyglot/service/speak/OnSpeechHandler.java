package be.webfactor.polyglot.service.speak;

import be.webfactor.polyglot.service.word.SpokenWord;

public interface OnSpeechHandler {

	void onSpeechFinished(SpokenWord spokenWord);
	
	void onSpeechStarted(SpokenWord spokenWord);
	
}
