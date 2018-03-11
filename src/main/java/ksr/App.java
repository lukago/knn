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
        l4.add(1000.0);
        l4.add(2000.0);
        l4.add(3000.0);

        List<Entry> trainSets = new ArrayList<>();
        trainSets.add(new Entry(l1, "small"));
        trainSets.add(new Entry(l2, "medium"));
        trainSets.add(new Entry(l3, "big"));

        Entry test = new Entry(l4, "");
        KNN knn = new KNN(trainSets, test);
        String res = knn.classify(1);
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

    private List<Entry> getNeighbours(List<Entry> trainEntries, Entry testEntry, int k) {
        List<Pair> distances = trainEntries.stream().map(entry -> {
            double dist = euclideanDistance(testEntry.values, entry.values);
            return new Pair(entry, dist);
        }).collect(Collectors.toList());

        distances.sort(Comparator.comparingDouble(a -> a.dist));
        List<Entry> neighbours = distances.subList(0, k)
                .stream().map(d -> d.entry)
                .collect(Collectors.toList());

        return neighbours;
    }

    private String getResponse(List<Entry> neighbours) {
        Map<String, Integer> classVotes = new HashMap<>();
        for (Entry entry : neighbours) {
            String response = entry.label;
            if (classVotes.containsKey(response)) {
                classVotes.put(response, classVotes.get(response) + 1);
            } else {
                classVotes.put(response, 1);
            }
        }
        return classVotes.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .get().getKey();
    }

    public String classify(int k) {
        List<Entry> neighbours = getNeighbours(this.trainSets, this.testInstance, k);
        return getResponse(neighbours);
    }

}
















