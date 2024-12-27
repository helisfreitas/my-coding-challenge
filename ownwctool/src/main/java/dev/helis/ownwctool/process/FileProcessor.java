package dev.helis.ownwctool.process;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class FileProcessor {

    private Set<String> fileNames = new HashSet<>();
    private Option option;

    public FileProcessor(String fileName, Option option) {
        this.fileNames.add(fileName);
        this.option = option;
    }

    public FileProcessor(Set<String> fileNames, Option option) {
        this.fileNames.addAll(fileNames);
        this.option = option;
    }

    public Set<CountResult> process() throws IOException {
        if (option.multipleFiles()) {
            fileNames = getFilesFrom(fileNames.stream().findFirst().get());
        }
        return processFiles();
    }

    private Set<CountResult> processFiles() throws IOException {
        Set<CountResult> results = new HashSet<>();
        for (String file : fileNames) {
            results.add(processFile(file));
        }
        return results;
    }

    private Set<String> getFilesFrom(String fileName) {
        Path path = Path.of(fileName);
        if (verifyInvalidFile(path)) {
            throw new IllegalArgumentException("Invalid file: " + fileName);
        }
        Set<String> files = new HashSet<>();

        try (var linesStream = Files.lines(path)) {
            linesStream.forEach(line -> {
                files.add(line);
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file: " + fileName);
        }
        return files;
    }

    private boolean verifyInvalidFile(Path path) {
        return !Files.exists(path) || !Files.isReadable(path) || !Files.isRegularFile(path);
    }

    private CountResult processFile(String fileName) throws IOException {
        Path path = Path.of(fileName);
        if (verifyInvalidFile(path)) {
            throw new IllegalArgumentException("Invalid file: " + fileName);
        }

        CountResultBuilder countResult = new CountResultBuilder(fileName, option);

        try (var linesStream = Files.lines(path)) {
            linesStream.forEach(line -> {
                if (option.showLine()) {
                    countResult.incrementLines();
                }
                if (option.showWord()) {
                    countResult.incrementWords(line.split("\\s+").length);
                }
                if (option.showChar()) {
                    countResult.incrementChars(line.length());
                }
            });
            if (option.countType().contains(CountType.BYTES)) {
                countResult.withBytes((int) Files.size(path));
            }
            return countResult.build();

        }
    }
}
