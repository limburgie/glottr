package be.webfactor.polyglot.service.speak;

import be.webfactor.polyglot.service.word.SpokenWord;

public interface SpeachService {
	
	void setSpeechHandler(OnSpeechHandler handler);

	void speak(SpokenWord spokenWord);
	
	void stop();
	
}
