package ksr;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        List<Double> l1 = new ArrayList<>();
        l1.add(1.0);
        l1.add(2.0);
        l1.add(3.0);


        List<Double> l2 = new ArrayList<>();
        l2.add(10.0);
        l2.add(20.0);
        l2.add(30.0);

        List<Double> l3 = new ArrayList<>();
        l3.add(100.0);
        l3.add(200.0);
        l3.add(300.0);

        List<Double> l4 = new ArrayList<>();
        l4.add(1.0);
        l4.add(2.0);
        l4.add(4.0);

        List<Entry> trainSets = new ArrayList<>();
        trainSets.add(new Entry(l1, "small"));
        trainSets.add(new Entry(l2, "medium"));
        trainSets.add(new Entry(l3, "big"));

        Entry test = new Entry(l4, "");
        KNN knn = new KNN(trainSets, test);
        String res = knn.classify(2);
        System.out.println(res);
    }
}

class Entry {
    List<Double> values;
    String label;

    public Entry(List<Double> values, String label) {
        this.values = values;
        this.label = label;
    }
}

class Pair {
    Entry entry;
    double dist;

    public Pair(Entry entry, double dist) {
        this.entry = entry;
        this.dist = dist;
    }
}

class KNN {
    private List<Entry> trainSets;
    private Entry testInstance;

    public KNN(List<Entry> trainEntries, Entry testEntry) {
        this.trainSets = trainEntries;
        this.testInstance = testEntry;
    }

    private double euclideanDistance(List<Double> a, List<Double> b) {
        double distance = 0;
        for (int i = 0; i < a.size(); i++) {
            distance += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(distance);
    }

    private List<Pair> getNeighbours(List<Entry> trainEntries, Entry testEntry, int k) {
        List<Pair> distances = trainEntries.stream().map(entry -> {
            double dist = euclideanDistance(testEntry.values, entry.values);
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
        List<Pair> neighbours = getNeighbours(this.trainSets, this.testInstance, k);
        return getResponse(neighbours);
    }

}
















