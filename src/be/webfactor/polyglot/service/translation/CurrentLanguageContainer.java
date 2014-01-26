package be.webfactor.polyglot.service.translation;

import be.webfactor.polyglot.domain.Language;

public class CurrentLanguageContainer {

	private static Language language;
	
	public static void set(Language language) {
		CurrentLanguageContainer.language = language;
	}
	
	public static Language get() {
		return language == null ? Language.DEFAULT : language;
	}
	
}
