package ksr.parser;

public class ParsedData {

    String[] words;
    String label;

    public ParsedData(String[] words, String label) {
        this.words = words;
        this.label = label;
    }

    public String[] getWords() {
        return words;
    }

    public String getLabel() {
        return label;
    }
}
