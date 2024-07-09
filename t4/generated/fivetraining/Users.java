public class Users {
    private int id;
    private String cpf;
    private String name;
    private java.sql.Date birth_date;

    public Users() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(java.sql.Date birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString() {
        return "Users {" + "id" + "=" + getId() + ", " + "cpf" + "=" + getCpf() + ", " + "name" + "=" + getName() + ", " + "birth_date" + "=" + getBirth_date() + "}";
    }
}
