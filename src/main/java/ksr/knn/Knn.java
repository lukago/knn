package ksr.knn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Knn {

    private List<Entry> trainEntries;
    private Entry testEntry;
    private Metric metric;

    public Knn(List<Entry> trainEntries, Entry testEntry, Metric metric) {
        this.trainEntries = trainEntries;
        this.testEntry = testEntry;
        this.metric = metric;
    }

    private List<Pair> getNeighbours(int k) {
        List<Pair> distances = trainEntries.stream().map(entry -> {
            double dist = metric.dist(testEntry.values, entry.values);
            return new Pair(entry, dist);
        }).collect(Collectors.toList());

        distances.sort(Comparator.comparingDouble(a -> a.dist));
        return distances.subList(0, k);
    }

    private String getResponse(List<Pair> neighbours) {
        Map<Pair, Integer> classVotes = new HashMap<>();
        neighbours.forEach(pair -> {
            if (classVotes.containsKey(pair))
                classVotes.put(pair, classVotes.get(pair) + 1);
            else
                classVotes.put(pair, 1);
        });
        return classVotes.entrySet().stream()
                .max((p1, p2) -> {
                    if (p1.getValue() > p2.getValue()) return 1;
                    if (p1.getValue().equals(p2.getValue()))
                        return Double.compare(p2.getKey().dist, p1.getKey().dist);
                    return -1;
                })
                .get().getKey().entry.label;
    }

    public String classify(int k) {
        return getResponse(getNeighbours(k));
    }

    public static class Pair {
        Entry entry;
        double dist;

        Pair(Entry entry, double dist) {
            this.entry = entry;
            this.dist = dist;
        }
    }
}
