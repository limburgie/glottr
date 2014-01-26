package be.webfactor.polyglot.domain;

import java.io.Serializable;

public class Exercise implements Serializable {

	private Language from;
	private Language to;
	private Topic topic;
	
	public Exercise(Language from, Language to, Topic topic) {
		this.from = from;
		this.to = to;
		this.topic = topic;
	}
	
	public Language getFrom() {
		return from;
	}
	
	public Language getTo() {
		return to;
	}
	
	public Topic getTopic() {
		return topic;
	}
	
}
