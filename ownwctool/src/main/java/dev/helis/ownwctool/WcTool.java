package dev.helis.ownwctool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import dev.helis.ownwctool.process.FileProcessor;
import dev.helis.ownwctool.process.OptionBuilder;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ownwctool", 
         description = "Print newline, word, and byte counts", 
         mixinStandardHelpOptions = true, // Adds --help and --version
         version = "OwnWcTool 1.0")
public class WcTool implements Callable<Integer> {
    @Option(names = {"-m", "--chars"}, 
            description = "Print the character counts, as per the current locale. Encoding errors are not counted.")
    private boolean showChars;

    @Option(names = {"-l", "--letters"}, 
    description = "Print the lines counts, as per the current locale. Encoding errors are not counted.")
    private boolean showLines;

    @Option(names = {"-w", "--words"}, 
    description = "Print the words counts, as per the current locale. Encoding errors are not counted.")
    private boolean showWords;

    @Option(names = {"-c", "--bytes"}, 
    description = "Print the byte counts, as per the current locale. Encoding errors are not counted.")
    private boolean showBytes;

    @Option(names = {"-x", "--extracted-multiple-files"}, 
    description = "Able to extract the file names from the first file and process them.")
    private boolean multipleFiles;

    @Option(names = {"-s", "--stdin"}, 
    description = "Read the files from the standard input.")
    private boolean stdin;

    @Parameters(index = "0", description = "The file to process.")
    private String fileName;

    @Override
    public Integer call() {
        if (stdin && fileName != "-") {
            System.err.println("ownwctool: missing file operand -");
            return 3;
        }

        if (stdin && multipleFiles) {
            System.err.println("ownwctool: -s and -x are mutually exclusive");
            return 4;
        }

         try {
            if (stdin) {
                Set<String> files = getFilesFromStdin();
                FileProcessor fileProcessor = new FileProcessor(files, new OptionBuilder()
                        .showChars(showChars)
                        .showLines(showLines)
                        .showWords(showWords)
                        .showBytes(showBytes)
                        .multipleFiles(multipleFiles)
                        .build());
                fileProcessor.process().forEach(System.out::println);
                return 0;
            }
            FileProcessor fileProcessor = new FileProcessor(fileName, new OptionBuilder()
                    .showChars(showChars)
                    .showLines(showLines)
                    .showWords(showWords)
                    .showBytes(showBytes)
                    .multipleFiles(multipleFiles)
                    .build());
            fileProcessor.process().forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.err.println("ownwctool: " + e.getMessage());
            return 1 ;
        } catch (Exception e) {
            System.err.println("ownwctool: Error when read file" );
            return 2;
        }

        return 0;
    }

    private Set<String> getFilesFromStdin() {
        Set<String> files = new  HashSet<>();
        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Stream.of(line.split("\0")).forEach(files::add);       
            }
        } catch (Exception e) {
            System.err.println("Error reading from stdin: " + e.getMessage());
        }
        return files;
    }

}
