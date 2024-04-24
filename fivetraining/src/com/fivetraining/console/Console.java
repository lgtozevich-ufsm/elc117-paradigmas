package com.fivetraining.console;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    private final InputStream in;
    private final PrintStream out;

    private final Scanner scanner;

    public Console(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        this.scanner = new Scanner(in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void writeLine() {
        writeLine("");
    }

    public void writeLine(String line) {
        out.println(line);
    }

    public void write(String text) {
        out.print(text);
    }
}
