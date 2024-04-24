package com.fivetraining.console;

import com.fivetraining.console.exceptions.ConsoleSyntaxException;

public class ConsoleTokenizer {
    private final String source;
    private int cursor;

    public ConsoleTokenizer(String source) {
        this.source = source;
        this.cursor = 0;
    }

    public String nextToken() throws ConsoleSyntaxException {
        if (!this.hasNextToken()) {
            return null;
        }

        this.skipWhitespace();

        State state = new NormalState("", false);

        while (!(state instanceof EndedState)) {
            state = state.advance(hasSourceEnded() ? null : source.charAt(cursor++));
        }

        return state.getToken();
    }

    public boolean hasNextToken() {
        return !this.hasSourceEnded();
    }

    private void skipWhitespace() {
        while (!hasSourceEnded() && Character.isWhitespace(source.charAt(cursor))) {
            cursor++;
        }
    }

    private boolean hasSourceEnded() {
        return cursor >= source.length();
    }

    private abstract static class State {
        private final String token;
        private final boolean escaping;

        protected State(String token, boolean escaping) {
            this.token = token;
            this.escaping = escaping;
        }

        public abstract State advance(Character character) throws ConsoleSyntaxException;

        public String getToken() {
            return token;
        }

        public boolean isEscaping() {
            return escaping;
        }
    }

    private static class NormalState extends State {
        public NormalState(String token, boolean escaping) {
            super(token, escaping);
        }

        @Override
        public State advance(Character character) {
            if (character == null) {
                return new EndedState(getToken(), isEscaping());
            }

            if (isEscaping()) {
                return new NormalState(getToken() + character, false);
            }

            if (character == '\\') {
                return new NormalState(getToken(), true);
            }

            if (character == '"') {
                return new QuotedState(getToken(), false);
            }

            if (Character.isWhitespace(character)) {
                return new EndedState(getToken(), isEscaping());
            }

            return new NormalState(getToken() + character, false);
        }
    }

    private static class QuotedState extends State {
        public QuotedState(String token, boolean escaping) {
            super(token, escaping);
        }

        @Override
        public State advance(Character character) throws ConsoleSyntaxException {
            if (character == null) {
                throw new ConsoleSyntaxException("Unexpected end of command while looking for \"");
            }

            if (isEscaping()) {
                return new QuotedState(getToken() + character, false);
            }

            if (character == '\\') {
                return new QuotedState(getToken(), true);
            }

            if (character == '"') {
                return new NormalState(getToken(), false);
            }

            return new QuotedState(getToken() + character, false);
        }
    }

    private static class EndedState extends State {
        public EndedState(String token, boolean escaping) {
            super(token, escaping);
        }

        @Override
        public State advance(Character character) {
            return this;
        }
    }
}
