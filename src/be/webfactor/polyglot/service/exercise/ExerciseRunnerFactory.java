package be.webfactor.polyglot.service.exercise;

import be.webfactor.polyglot.service.exercise.impl.ExerciseRunnerImpl;

public class ExerciseRunnerFactory {

	public static ExerciseRunner forActivity(ExerciseActivity activity) {
		return new ExerciseRunnerImpl(activity);
	}
	
}
