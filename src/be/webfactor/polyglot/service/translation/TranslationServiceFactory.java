package be.webfactor.polyglot.service.translation;

import be.webfactor.polyglot.domain.Exercise;
import be.webfactor.polyglot.domain.Playback;
import be.webfactor.polyglot.service.translation.impl.TranslationServiceRandomImpl;
import be.webfactor.polyglot.service.translation.impl.TranslationServiceSequentialImpl;
import be.webfactor.polyglot.service.translation.impl.TranslationServiceShuffledImpl;

public class TranslationServiceFactory {

	public static TranslationService forPlaybackMode(Playback playback, Exercise exercise, boolean repeated) {
		switch (playback) {
		case SEQUENTIALLY:
			return new TranslationServiceSequentialImpl(exercise, repeated);
		case SHUFFLED:
			return new TranslationServiceShuffledImpl(exercise, repeated);
		case RANDOM:
			return new TranslationServiceRandomImpl(exercise, repeated);
		default:
			return forPlaybackMode(Playback.SHUFFLED, exercise, repeated);
		}
	}

}
