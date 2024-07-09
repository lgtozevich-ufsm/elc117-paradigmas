public class Program_exercises {
    private int program_id;
    private int exercise_code;
    private int load;
    private int sets;
    private int minimum_repetitions;
    private int maximum_repetitions;
    private double resting_time;

    public Program_exercises() { }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
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

    @Override
    public String toString() {
        return "Program_exercises {" + "program_id" + "=" + getProgram_id() + ", " + "exercise_code" + "=" + getExercise_code() + ", " + "load" + "=" + getLoad() + ", " + "sets" + "=" + getSets() + ", " + "minimum_repetitions" + "=" + getMinimum_repetitions() + ", " + "maximum_repetitions" + "=" + getMaximum_repetitions() + ", " + "resting_time" + "=" + getResting_time() + "}";
    }
}
