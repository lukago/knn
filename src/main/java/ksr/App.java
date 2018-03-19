package ksr;

import ksr.extraction.CountVectorizer;
import ksr.knn.Entry;
import ksr.knn.Knn;
import ksr.metric.EuclideanMetric;
import ksr.parser.ParsedData;
import ksr.parser.SgmParser;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        List<ParsedData> data = new SgmParser().parseAll("data/reuters21578", ".sgm");
        List<Entry<Integer, Integer>> entries = new CountVectorizer().extract(data);
        int endIndex = (int) (entries.size()*0.7);
        List<Entry<Integer, Integer>> trainEntries = entries.subList(0, endIndex);
        List<Entry<Integer, Integer>> testEntries = entries.subList(endIndex, entries.size() - 1);
        Knn<Integer, Integer> knn = new Knn<>(trainEntries, testEntries, new EuclideanMetric());
        Knn.Response res = knn.classify(5);
        System.out.println(res.perc);
        System.out.println(res.good);
        System.out.println(res.wrong);
    }
}
















