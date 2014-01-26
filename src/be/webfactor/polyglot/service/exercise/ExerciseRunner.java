package be.webfactor.polyglot.service.exercise;

import be.webfactor.polyglot.domain.Exercise;

public interface ExerciseRunner {

	void start(Exercise exercise);
	
	void stop();
	
}
