package ksr.metric;

import ksr.knn.Entry;

import java.util.HashSet;
import java.util.Set;

public class ChebyshevMetricDouble implements Metric<String, Double> {

    @Override
    public double dist(Entry<String, Double> a, Entry<String, Double> b) {
        Set<String> indexes = new HashSet<>(a.getWordsMap().keySet());
        indexes.addAll(b.getWordsMap().keySet());
        double distance = 0;

        for (String index : indexes) {
            Double val1 = a.getWordsMap().get(index);
            Double val2 = b.getWordsMap().get(index);
            if (val1 == null) val1 = 0.0;
            if (val2 == null) val2 = 0.0;
            distance = Math.max(distance, Math.abs(val1 - val2));
        }

        return Math.sqrt(distance);
    }
}