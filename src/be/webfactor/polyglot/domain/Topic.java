package be.webfactor.polyglot.domain;

import java.util.ResourceBundle;

import be.webfactor.polyglot.service.translation.CurrentLanguageContainer;

public enum Topic {

	//TEST("test"),
	COUNTING("numbers"),
	COLORS("colors"),
	FAMILY("family"),
	CITY("city"),
	HOUSE("house"),
	DRINKING("drinking");
	
	private static final String BUNDLE_NAME = "be.webfactor.polyglot.exercises.Language";
	
	private String name;
	private String bundlePackage;
	
	private Topic(String name) {
		this(name, name);
	}
	
	private Topic(String name, String bundlePackage) {
		this.name = name;
		this.bundlePackage = bundlePackage;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBundlePackage() {
		return bundlePackage;
	}
	
	public String toString() {
		return getBundle().getString(name);
	}
	
	private ResourceBundle getBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME, CurrentLanguageContainer.get().getLocale());
	}
	
}
