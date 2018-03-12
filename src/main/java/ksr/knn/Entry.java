package ksr.knn;

import java.util.List;

public class Entry {
    List<Double> values;
    String label;

    public Entry(List<Double> values, String label) {
        this.values = values;
        this.label = label;
    }
}
