package ksr.knn;

public interface Metric {
    double dist(Entry a, Entry b);
}
