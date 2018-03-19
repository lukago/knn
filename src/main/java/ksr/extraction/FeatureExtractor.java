package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;

import java.util.List;

public interface FeatureExtractor<K, V> {

    List<Entry<K, V>> extract(List<ParsedData> data);
}
