package com.fivetraining.app.models;

import java.time.LocalDateTime;

public class WorkoutActivity {
    private int workoutId;
    private int exerciseCode;

    private int load;
    private int sets;

    private int minimumRepetitions;
    private int maximumRepetitions;

    private double restingTime;

    private boolean completed;

    public WorkoutActivity() {}

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getExerciseCode() {
        return exerciseCode;
    }

    public void setExerciseCode(int exerciseCode) {
        this.exerciseCode = exerciseCode;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getMinimumRepetitions() {
        return minimumRepetitions;
    }

    public void setMinimumRepetitions(int minimumRepetitions) {
        this.minimumRepetitions = minimumRepetitions;
    }

    public int getMaximumRepetitions() {
        return maximumRepetitions;
    }

    public void setMaximumRepetitions(int maximumRepetitions) {
        this.maximumRepetitions = maximumRepetitions;
    }

    public double getRestingTime() {
        return restingTime;
    }

    public void setRestingTime(double restingTime) {
        this.restingTime = restingTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
