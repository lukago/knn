package ksr.knn;

import ksr.metric.Metric;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Knn {

    private List<Entry> trainEntries;
    private List<Entry> testEntries;
    private Metric metric;

    public Knn(List<Entry> trainEntries, List<Entry> testEntries, Metric metric) {
        this.trainEntries = trainEntries;
        this.testEntries = testEntries;
        this.metric = metric;
    }

    private List<Pair> getNeighbours(int k, Entry testEntry) {
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
                .get().getKey().entry.label;
    }

    public Response classify(int k) {
        Response response = new Response();
        for (Entry testEntry : testEntries) {
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

    public static class Pair {
        Entry entry;
        double dist;

        Pair(Entry entry, double dist) {
            this.entry = entry;
            this.dist = dist;
        }
    }

    public static class Response {
        public int good;
        public int wrong;
        public double perc;
    }
}
