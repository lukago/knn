package ksr.metric;

import ksr.knn.Entry;

import java.util.HashSet;
import java.util.Set;

public class ChebyshevMetric implements Metric<Integer, Integer> {

    @Override
    public double dist(Entry<Integer, Integer> a, Entry<Integer, Integer> b) {
        Set<Integer> indexes = new HashSet<>(a.getWordsMap().keySet());
        indexes.addAll(b.getWordsMap().keySet());
        double distance = 0;

        for (int index : indexes) {
            Integer val1 = a.getWordsMap().get(index);
            Integer val2 = b.getWordsMap().get(index);
            if (val1 == null) val1 = 0;
            if (val2 == null) val2 = 0;
            distance = Math.max(distance, Math.abs(val1-val2));
        }

        return distance;
    }
}
