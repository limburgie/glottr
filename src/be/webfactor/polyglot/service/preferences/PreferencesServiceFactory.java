package be.webfactor.polyglot.service.preferences;

import android.content.Context;
import be.webfactor.polyglot.service.preferences.impl.PreferencesServiceImpl;

public class PreferencesServiceFactory {

	private static PreferencesService preferencesService;
	
	public static PreferencesService forContext(Context context) {
		if(preferencesService == null) {
			preferencesService = new PreferencesServiceImpl(context);
		}
		return preferencesService;
	}
	
}
