package dev.helis.ownwctool.process;

public class CountResultBuilder {

    private static final int NOT_INCLUDE = -1;

    private Option option;

    private int lines;
    private int words;
    private int chars;
    private int bytes;
    private String fileName;

    public CountResultBuilder(String fileName, Option option) {
        this.fileName = fileName;
        this.option = option;
    }

    public CountResultBuilder withLines(int lines) {
        this.lines = lines;
        return this;
    }

    public CountResultBuilder withWords(int words) {
        this.words = words;
        return this;
    }

    public CountResultBuilder withChars(int chars) {
        this.chars = chars;
        return this;
    }

    public CountResultBuilder withBytes(int bytes) {
        this.bytes = bytes;
        return this;
    }

    public void incrementLines() {
        lines++;
    }

    public void incrementWords(int length) {
        words += length;
    }

    public void incrementChars(int length) {
        chars += length;
    }

    public CountResult build() {
        if (!option.showLine()) {
            lines = NOT_INCLUDE;
        }
        if (!option.showWord()) {
            words = NOT_INCLUDE;
        }
        if (!option.showChar()) {
            chars = NOT_INCLUDE;
        }
        if (!option.showByte()) {
            bytes = NOT_INCLUDE;
        }
        return new CountResult(lines, words, chars, bytes, fileName);
    }

}
