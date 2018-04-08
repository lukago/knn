package ksr.metric;

import ksr.knn.Entry;

import java.util.HashSet;
import java.util.Set;

public class TermFrequency implements Metric<String, Integer> {

    @Override
    public double dist(Entry<String, Integer> a, Entry<String, Integer> b) {
        Set<String> words = new HashSet<>(a.getWordsMap().keySet());
        words.addAll(b.getWordsMap().keySet());
        double distance = 0;

        for (String word : words) {
            Integer val1 = a.getWordsMap().get(word);
            Integer val2 = b.getWordsMap().get(word);
            if (val1 == null) val1 = 0;
            if (val2 == null) val2 = 0;
            distance += Math.min(val1, val2);
        }

        return -distance;
    }
}
