package be.webfactor.polyglot.service.speak;

import be.webfactor.polyglot.service.speak.impl.SpeachServiceISpeechImpl;
import android.app.Activity;

public class SpeachServiceFactory {

	private static SpeachService INSTANCE;
	
	public static SpeachService forActivity(Activity activity) {
		if(INSTANCE == null) {
			INSTANCE = new SpeachServiceISpeechImpl(activity);
		}
		return INSTANCE;
	}
	
}
