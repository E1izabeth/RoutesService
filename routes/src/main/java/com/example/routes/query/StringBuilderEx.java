package com.example.routes.query;

public class StringBuilderEx {
    private final StringBuilder _sb;

    public StringBuilderEx() {
        _sb = new StringBuilder();
    }

    public StringBuilderEx appendLine() {
        _sb.append("\n");
        return this;
    }

    public StringBuilderEx appendLine(String line) {
        _sb.append(line);
        _sb.append("\n");
        return this;
    }

    public StringBuilderEx formatLine(String format, Object ... args) {
        _sb.append(String.format(format, args));
        _sb.append("\n");
        return this;
    }

    public StringBuilderEx append(String str) {
        _sb.append(str);
        return this;
    }

    public StringBuilderEx format(String format, Object ... args) {
        _sb.append(String.format(format, args));
        return this;
    }


    public String toString() {
        return _sb.toString();
    }

    public int length() {
        return _sb.length();
    }

    public void clear() {
        _sb.setLength(0);
    }
}
