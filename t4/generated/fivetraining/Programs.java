public class Programs {
    private int id;
    private int user_id;
    private String name;

    public Programs() { }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Programs {" + "id" + "=" + getId() + ", " + "user_id" + "=" + getUser_id() + ", " + "name" + "=" + getName() + "}";
    }
}
