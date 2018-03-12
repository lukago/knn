package ksr;

import ksr.knn.Entry;
import ksr.knn.EuclideanMetric;
import ksr.knn.Knn;

import java.util.ArrayList;
import java.util.List;

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
        Knn knn = new Knn(trainSets, test, new EuclideanMetric());
        String res = knn.classify(2);
        System.out.println(res);
    }
}
















