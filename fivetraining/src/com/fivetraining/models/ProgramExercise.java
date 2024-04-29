package com.fivetraining.models;

public class ProgramExercise {
    private int programId;
    private int exerciseId;

    private int load;
    private int sets;

    private int minimumRepetitions;
    private int maximumRepetitions;

    private double restingTime;

    public ProgramExercise() {}

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
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
}
