public class Workout_activities {
    private int workout_id;
    private int exercise_code;
    private int load;
    private int sets;
    private int minimum_repetitions;
    private int maximum_repetitions;
    private double resting_time;
    private boolean completed;

    public Workout_activities() { }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public int getExercise_code() {
        return exercise_code;
    }

    public void setExercise_code(int exercise_code) {
        this.exercise_code = exercise_code;
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

    public int getMinimum_repetitions() {
        return minimum_repetitions;
    }

    public void setMinimum_repetitions(int minimum_repetitions) {
        this.minimum_repetitions = minimum_repetitions;
    }

    public int getMaximum_repetitions() {
        return maximum_repetitions;
    }

    public void setMaximum_repetitions(int maximum_repetitions) {
        this.maximum_repetitions = maximum_repetitions;
    }

    public double getResting_time() {
        return resting_time;
    }

    public void setResting_time(double resting_time) {
        this.resting_time = resting_time;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Workout_activities {" + "workout_id" + "=" + getWorkout_id() + ", " + "exercise_code" + "=" + getExercise_code() + ", " + "load" + "=" + getLoad() + ", " + "sets" + "=" + getSets() + ", " + "minimum_repetitions" + "=" + getMinimum_repetitions() + ", " + "maximum_repetitions" + "=" + getMaximum_repetitions() + ", " + "resting_time" + "=" + getResting_time() + ", " + "completed" + "=" + getCompleted() + "}";
    }
}
