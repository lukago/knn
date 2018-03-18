package ksr.knn;

import java.util.Map;

public class Entry {

    Map<Integer, Integer> wordsMap;
    String label;

    public Entry(Map<Integer, Integer> values, String label) {
        this.wordsMap = values;
        this.label = label;
    }

    public Map<Integer, Integer> getWordsMap() {
        return wordsMap;
    }

    public String getLabel() {
        return label;
    }
}
