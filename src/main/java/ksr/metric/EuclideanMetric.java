package ksr.metric;

import ksr.knn.Entry;

import java.util.HashSet;
import java.util.Set;

public class EuclideanMetric implements Metric {

    @Override
    public double dist(Entry a, Entry b) {
        Set<Integer> indexes = new HashSet<>(a.getWordsMap().keySet());
        indexes.addAll(b.getWordsMap().keySet());
        double distance = 0;

        for (int index : indexes) {
            Integer val1 = a.getWordsMap().get(index);
            Integer val2 = b.getWordsMap().get(index);
            if (val1 == null) val1 = 0;
            if (val2 == null) val2 = 0;
            distance += Math.pow(val1 - val2, 2);
        }

        return Math.sqrt(distance);
    }
}
