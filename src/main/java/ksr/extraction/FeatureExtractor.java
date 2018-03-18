package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;

import java.util.List;

public interface FeatureExtractor {

    List<Entry> extract(List<ParsedData> data);
}
