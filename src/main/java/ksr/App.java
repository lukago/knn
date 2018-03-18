package ksr;

import ksr.knn.Entry;
import ksr.knn.EuclideanMetric;
import ksr.knn.Knn;
import ksr.parser.ParsedData;
import ksr.parser.SgmParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws IOException {
        List<ParsedData> d = new SgmParser().parseAll("data/reuters21578", ".sgm");

        Map<Integer, Integer> l1 = new HashMap<>();
        l1.put(1, 1);
        l1.put(2, 2);
        l1.put(3, 2);


        Map<Integer, Integer> l2 = new HashMap<>();
        l2.put(1, 10);
        l2.put(2, 20);
        l2.put(3, 30);

        Map<Integer, Integer> l3 = new HashMap<>();
        l3.put(1, 100);
        l3.put(2, 200);
        l3.put(3, 300);

        Map<Integer, Integer> l4 = new HashMap<>();
        l4.put(1, 10);
        l4.put(2, 20);
        l4.put(3, 40);

        List<Entry> trainSets = new ArrayList<>();
        trainSets.add(new Entry(l1, "small"));
        trainSets.add(new Entry(l2, "medium"));
        trainSets.add(new Entry(l3, "big"));

        Entry test = new Entry(l4, "");
        Knn knn = new Knn(trainSets, test, new EuclideanMetric());
        String res = knn.classify(2);
        System.out.println(res);
    }
}
















