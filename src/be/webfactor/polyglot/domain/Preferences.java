package be.webfactor.polyglot.domain;

public class Preferences {

	public static final int DEFAULT_WAIT_MILLIS = 1000;
	public static final Playback DEFAULT_PLAYBACK = Playback.SHUFFLED;
	public static final boolean DEFAULT_REPEATABLE = true;
	
	private int waitMillis = DEFAULT_WAIT_MILLIS;
	private Playback playback = DEFAULT_PLAYBACK;
	private boolean repeatable = DEFAULT_REPEATABLE;

	public int getWaitMillis() {
		return waitMillis;
	}

	public void setWaitMillis(int waitMillis) {
		this.waitMillis = waitMillis;
	}

	public Playback getPlayback() {
		return playback;
	}

	public void setPlayback(Playback playback) {
		this.playback = playback;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}
	
}
