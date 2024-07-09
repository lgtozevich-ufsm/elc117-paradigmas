public class Subscriptions {
    private int id;
    private int user_id;
    private int plan_code;
    private java.sql.Date start_date;
    private java.sql.Date end_date;
    private String card_number;
    private java.sql.Date card_expiry_date;
    private String card_cvv;

    public Subscriptions() { }

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

    public int getPlan_code() {
        return plan_code;
    }

    public void setPlan_code(int plan_code) {
        this.plan_code = plan_code;
    }

    public java.sql.Date getStart_date() {
        return start_date;
    }

    public void setStart_date(java.sql.Date start_date) {
        this.start_date = start_date;
    }

    public java.sql.Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(java.sql.Date end_date) {
        this.end_date = end_date;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public java.sql.Date getCard_expiry_date() {
        return card_expiry_date;
    }

    public void setCard_expiry_date(java.sql.Date card_expiry_date) {
        this.card_expiry_date = card_expiry_date;
    }

    public String getCard_cvv() {
        return card_cvv;
    }

    public void setCard_cvv(String card_cvv) {
        this.card_cvv = card_cvv;
    }

    @Override
    public String toString() {
        return "Subscriptions {" + "id" + "=" + getId() + ", " + "user_id" + "=" + getUser_id() + ", " + "plan_code" + "=" + getPlan_code() + ", " + "start_date" + "=" + getStart_date() + ", " + "end_date" + "=" + getEnd_date() + ", " + "card_number" + "=" + getCard_number() + ", " + "card_expiry_date" + "=" + getCard_expiry_date() + ", " + "card_cvv" + "=" + getCard_cvv() + "}";
    }
}
