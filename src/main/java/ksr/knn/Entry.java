package ksr.knn;

import java.util.Map;

public class Entry<K, V> {

    private Map<K, V> wordsMap;
    private String label;

    public Entry(Map<K, V> values, String label) {
        this.wordsMap = values;
        this.label = label;
    }

    public Map<K, V> getWordsMap() {
        return wordsMap;
    }

    public String getLabel() {
        return label;
    }
}
