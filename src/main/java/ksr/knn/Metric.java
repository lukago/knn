package ksr.knn;

import java.util.List;

public interface Metric {
    double dist(List<Double> a, List<Double> b);
}
