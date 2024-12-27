package dev.helis.ownwctool.process;

import java.util.HashSet;

public record Option(
    HashSet<CountType> countType,
    boolean multipleFiles,
    boolean stdin
) {

    public boolean showLine() {
        return countType.contains(CountType.LINES);
    }

    public boolean showWord() {
        return countType.contains(CountType.WORDS);
    }

    public boolean showChar() {
        return countType.contains(CountType.CHARS);
    }

    public boolean showByte() {
        return countType.contains(CountType.BYTES);
    }
    
}
