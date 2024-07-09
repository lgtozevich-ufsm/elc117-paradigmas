public class Workouts {
    private int id;
    private int user_id;
    private String program_name;
    private java.sql.Timestamp start_time;
    private java.sql.Timestamp end_time;

    public Workouts() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public java.sql.Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(java.sql.Timestamp start_time) {
        this.start_time = start_time;
    }

    public java.sql.Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(java.sql.Timestamp end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "Workouts {" + "id" + "=" + getId() + ", " + "user_id" + "=" + getUser_id() + ", " + "program_name" + "=" + getProgram_name() + ", " + "start_time" + "=" + getStart_time() + ", " + "end_time" + "=" + getEnd_time() + "}";
    }
}
