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
        double startTime = System.currentTimeMillis();
        Response res = new Response();
        for (int i = 0; i < testEntries.size(); i++) {
            Entry<K, V> testEntry = testEntries.get(i);
            String sres = getResponse(getNeighbours(k, testEntry));
            if (sres.equals(testEntry.getLabel())) {
                String key = testEntry.getLabel();
                AnsPair val = res.ans.get(key);
                if (val == null) val = new AnsPair();
                val.good++;
                res.ans.put(key, val);
                System.out.printf("%d/%d \t%s +\n", i + 1, testEntries.size(), key);
            } else {
                String key = testEntry.getLabel();
                AnsPair val = res.ans.get(key);
                if (val == null) val = new AnsPair();
                val.wrong++;
                res.ans.put(key, val);
                System.out.printf("%d/%d \t%s -\n", i + 1, testEntries.size(), key);
            }
        }


        res.good = res.ans.entrySet().stream().mapToInt(entry -> entry.getValue().good).sum();
        res.wrong = res.ans.entrySet().stream().mapToInt(entry -> entry.getValue().wrong).sum();
        res.perc = res.good * 1.0 / (res.wrong + res.good);
        res.learnTime = (System.currentTimeMillis() - startTime) / 1000;
        return res;
    }

    public static class Response {
        public Map<String, AnsPair> ans = new HashMap<>();
        public int good;
        public int wrong;
        public double perc;
        public double learnTime;

        @Override
        public String toString() {
            return "Response{" +
                    "\nans=" + ans +
                    ",\ngood=" + good +
                    ",\nwrong=" + wrong +
                    ",\nperc=" + perc +
                    ",\nlearnTime=" + learnTime +
                    '}';
        }
    }

    public static class AnsPair {
        int good;
        int wrong;

        @Override
        public String toString() {
            return '{' + "good=" + good + ", wrong=" + wrong + '}';
        }
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
