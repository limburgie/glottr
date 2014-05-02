package be.webfactor.polyglot.domain;

import java.util.Locale;

public enum Language {

	EN("en", "English", "ukenglishfemale"),
	ES("es", "Español", "eurspanishfemale"),
	FR("fr", "Français", "eurfrenchfemale"),
	DE("de", "Deutsch", "eurgermanfemale"),
	NL("nl", "Nederlands", "eurdutchfemale");
	
	public static final Language DEFAULT = Language.EN;

	private String languageId;
	private String label;
	private String voice;
	
	private Language(String languageId, String label, String voice) {
		this.languageId = languageId;
		this.label = label;
		this.voice = voice;
	}
	
	public Locale getLocale() {
		return new Locale(languageId);
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getVoice() {
		return voice;
	}
	
	public String toString() {
		return label;
	}
	
}
