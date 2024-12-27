package dev.helis.ownwctool.process;

import java.util.HashSet;
import java.util.List;

public class OptionBuilder {

    HashSet<CountType> countType = new HashSet<>();

    private boolean multipleFiles;

    private boolean stdin;
   

    public OptionBuilder() {
    }

    public OptionBuilder showChars(boolean showChars) {
        if(showChars) {
            this.countType.add(CountType.CHARS);
        }        
        return this;
    }

    public OptionBuilder showLines(boolean showLines) {
        if(showLines) {
            this.countType.add(CountType.LINES);
        }
        return this;
    }

    public OptionBuilder showWords(boolean showWords) {
        if(showWords) {
            this.countType.add(CountType.WORDS);
        }
        return this;
    }

    public OptionBuilder showBytes(boolean showBytes) {
        if (showBytes) {
            this.countType.add(CountType.BYTES);            
        }
        return this;
    }

    public List<CountType> getCountType() {
        return List.copyOf(countType);
    }

    public boolean isMultipleFiles() {
        return multipleFiles;
    }

    public OptionBuilder multipleFiles(boolean multipleFiles) {
        this.multipleFiles = multipleFiles;
        return this;
    }

    public OptionBuilder stdin(boolean stdin) {
        this.stdin = stdin;
        return this;    
    }

     public OptionBuilder withDefaultCounts() {
        if (countType.isEmpty()) {
            showLines(true).showWords(true).showBytes(true);
        }
        return this;
    }

    public Option build() {
        withDefaultCounts();
        return new Option(countType, multipleFiles, stdin);
    }
}
