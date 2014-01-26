package be.webfactor.polyglot.service.speak.impl;

import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;

import android.app.Activity;
import android.media.AudioManager;
import android.widget.Toast;
import be.webfactor.polyglot.service.speak.OnSpeechHandler;
import be.webfactor.polyglot.service.speak.SpeachService;
import be.webfactor.polyglot.service.word.SpokenWord;

public class SpeachServiceISpeechImpl implements SpeachService {

	private SpokenWord lastSpokenWord;
	private OnSpeechHandler handler;
	private Activity activity;
	private SpeechSynthesis tts;
	
	public SpeachServiceISpeechImpl(Activity activity) {
		this.activity = activity;
		try {
			tts = SpeechSynthesis.getInstance(activity);
			tts.setStreamType(AudioManager.STREAM_MUSIC);
			tts.setSpeechSynthesisEvent(new SpeechSynthesisEvent() {
				public void onPlaySuccessful() {
					if(handler != null) {
						handler.onSpeechFinished(lastSpokenWord);
					}
				}
				public void onPlayStart() {
					if(handler != null) {
						handler.onSpeechStarted(lastSpokenWord);
					}
				}
			});
		} catch (InvalidApiKeyException e) {
			Toast.makeText(activity, "No API key!", Toast.LENGTH_LONG).show();
		}
	}
	
	public void setSpeechHandler(OnSpeechHandler handler) {
		this.handler = handler;
	}
	
	public void speak(SpokenWord spokenWord) {
		try {
			tts.setVoiceType(spokenWord.getVoiceId());
			tts.speak(spokenWord.getWordOutsideBrackets());
			lastSpokenWord = spokenWord;
		} catch (BusyException e) {
			Toast.makeText(activity, "Busy!", Toast.LENGTH_LONG).show();
		} catch (NoNetworkException e) {
			Toast.makeText(activity, "No network!", Toast.LENGTH_LONG).show();
		}
	}

	public void stop() {
		tts.stop();
	}

}