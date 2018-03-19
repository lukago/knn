package ksr.knn;

import ksr.metric.Metric;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Knn<K, V> {

    private List<Entry<K, V>> trainEntries;
    private List<Entry<K, V>> testEntries;
    private Metric<K, V> metric;

    public Knn(List<Entry<K, V>> trainEntries, List<Entry<K, V>> testEntries, Metric<K, V> metric) {
        this.trainEntries = trainEntries;
        this.testEntries = testEntries;
        this.metric = metric;
    }

    private List<Pair> getNeighbours(int k, Entry<K, V> testEntry) {
        List<Pair> distances = trainEntries.stream().map(entry -> {
            double dist = metric.dist(testEntry, entry);
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
                .get().getKey().entry.getLabel();
    }

    public Response classify(int k) {
        Response response = new Response();
        for (Entry<K, V> testEntry : testEntries) {
            String res = getResponse(getNeighbours(k, testEntry));
            if (res.equals(testEntry.getLabel())) {
                response.good++;
                System.out.println("1");
            } else {
                response.wrong++;
                System.out.println("0");
            }
        }
        response.perc = response.good * 1.0 / (response.good + response.wrong);
        return response;
    }

    public static class Response {
        public int good;
        public int wrong;
        public double perc;
    }

    private class Pair {
        Entry<K, V> entry;
        double dist;

        Pair(Entry<K, V> entry, double dist) {
            this.entry = entry;
            this.dist = dist;
        }
    }
}
