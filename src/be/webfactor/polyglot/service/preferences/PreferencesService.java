package be.webfactor.polyglot.service.preferences;

import be.webfactor.polyglot.domain.Preferences;

public interface PreferencesService {

	Preferences getPreferences();
	
	void reset();
	
}
