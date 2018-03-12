package ksr.knn;

import java.util.List;

public class EuclideanMetric implements Metric {

    @Override
    public double dist(List<Double> a, List<Double> b) {
        double distance = 0;
        for (int i = 0; i < a.size(); i++) {
            distance += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(distance);
    }
}
