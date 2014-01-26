package be.webfactor.polyglot.service.translation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import be.webfactor.polyglot.domain.Exercise;
import be.webfactor.polyglot.domain.Language;
import be.webfactor.polyglot.service.translation.TranslationService;
import be.webfactor.polyglot.service.word.SpokenWord;

public abstract class TranslationServiceResourceBundleImpl implements TranslationService {
	
	private static final String PACKAGE_FORMAT = "be.webfactor.polyglot.exercises.%s.Language";
	private static final String FILE_FORMAT = "/be/webfactor/polyglot/exercises/%s/Language_%s.properties";
	
	protected Exercise exercise;
	protected boolean repeated;
	private String currentKey;
	
	public TranslationServiceResourceBundleImpl(Exercise exercise, boolean repeated) {
		this.exercise = exercise;
		this.repeated = repeated;
	}
	
	public SpokenWord getNextWord() {
		return new SpokenWord(getVoiceId(), getWord());
	}
	
	public boolean isFinished() {
		return !repeated && currentKey == null && isLoopFinished();
	}
	
	private String getVoiceId() {
		return (currentKey == null ? exercise.getFrom() : exercise.getTo()).getVoice();
	}
	
	private String getWord() {
		if(currentKey == null) {
			currentKey = getNextKey();
			return getBundle(exercise.getFrom()).getString(currentKey);
		}
		String translation = getBundle(exercise.getTo()).getString(currentKey);
		currentKey = null;
		return translation;
	}
	
	protected abstract String getNextKey();
	
	protected abstract boolean isLoopFinished();
	
	protected List<String> getKeys(Language language) {
		String fileLocation = String.format(FILE_FORMAT, exercise.getTopic().getBundlePackage(), language.getLocale().getLanguage());
		Scanner fileScanner = new Scanner(getClass().getResourceAsStream(fileLocation), "UTF-8");
		fileScanner.useDelimiter("\\A");
		List<String> result = new ArrayList<String>();
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			if(!line.trim().isEmpty()) {
				result.add(line.split("=")[0]);
			}
		}
		fileScanner.close();
		return result;
	}
	
	protected ResourceBundle getBundle(Language language) {
		return ResourceBundle.getBundle(String.format(PACKAGE_FORMAT, exercise.getTopic().getBundlePackage()), language.getLocale());
	}
	
}
