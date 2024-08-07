public class Plans {
    private int code;
    private String name;
    private double price;

    public Plans() { }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Plans {" + "code" + "=" + getCode() + ", " + "name" + "=" + getName() + ", " + "price" + "=" + getPrice() + "}";
    }
}
