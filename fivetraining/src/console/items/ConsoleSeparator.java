package console.items;

public class ConsoleSeparator implements ConsoleDisplayable {
    private final String content;

    public ConsoleSeparator() {
        this.content = "";
    }

    public ConsoleSeparator(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String display() {
        return '\n' + this.getContent();
    }
}
