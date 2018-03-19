package ksr.metric;

import ksr.knn.Entry;

public interface Metric<K, V> {
    double dist(Entry<K, V> a, Entry<K, V> b);
}
