package ksr.extraction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class StopWords {

    private static final String STOP_PATH = "data/stopwords.txt";

    public static Set<String> readStopWords() {
        Set<String> lines = new HashSet<>();
        try {
            lines = new HashSet<>(Files.readAllLines(Paths.get(STOP_PATH), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
