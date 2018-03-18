package ksr.knn;

public class EuclideanMetric implements Metric {

    @Override
    public double dist(Entry a, Entry b) {
        double distance = 0;
        for (int index : a.wordsMap.keySet()) {
            Integer val1 = a.getWordsMap().get(index);
            Integer val2 = b.getWordsMap().get(index);
            if (val1 != null && val2 != null) {
                distance += Math.pow(val1 - val2, 2);
            }
        }
        return Math.sqrt(distance);
    }
}
