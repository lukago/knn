package ksr.metric;

import ksr.knn.Entry;

public interface Metric {
    double dist(Entry a, Entry b);
}
