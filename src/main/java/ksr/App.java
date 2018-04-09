package ksr;

import ksr.extraction.CountVectorizer;
import ksr.extraction.CountMapper;
import ksr.extraction.TfIdf;
import ksr.knn.Entry;
import ksr.knn.Knn;
import ksr.metric.EuclideanMetric;
import ksr.metric.EuclideanMetricDouble;
import ksr.metric.TermFrequency;
import ksr.parser.ParsedData;
import ksr.parser.SgmParser;

import java.io.IOException;
import java.util.List;

public class App {

    private final static String[] places = {"west-germany", "usa", "france", "uk", "canada", "japan"};
    private final static String[] topics = {"acq", "earn", "jobs", "income", "cocoa"};
    private final static String[] voiv = {"lodzkie", "mazowieckie"};

    public static void main(String[] args) throws IOException {
        voiw();
    }

    private static void cv() throws IOException {
        List<ParsedData> data = new SgmParser("PLACES", places).parseAll("data/reuters21578", ".sgm");
        List<Entry<Integer, Integer>> entries = new CountVectorizer().extract(data);

        int endIndex = (int) (entries.size() * 0.6);
        List<Entry<Integer, Integer>> trainEntries = entries.subList(0, endIndex);
        List<Entry<Integer, Integer>> testEntries = entries.subList(endIndex, endIndex + 100);

        Knn<Integer, Integer> knn = new Knn<>(trainEntries, testEntries, new EuclideanMetric());
        Knn.Response res = knn.classify(3);

        System.out.println(res);
    }

    private static void om() throws IOException {
        List<ParsedData> data = new SgmParser("TOPICS", topics).parseAll("data/reuters21578", ".sgm");
        List<Entry<String, Integer>> entries = new CountMapper().extract(data);

        int endIndex = (int) (entries.size() * 0.6);
        List<Entry<String, Integer>> trainEntries = entries.subList(0, endIndex);
        List<Entry<String, Integer>> testEntries = entries.subList(endIndex, entries.size());

        Knn<String, Integer> knn = new Knn<>(trainEntries, testEntries, new TermFrequency());
        Knn.Response res = knn.classify(3);

        System.out.println(res);
    }

    private static void tfidf() throws IOException {
        List<ParsedData> data = new SgmParser("PLACES", places).parseAll("data/reuters21578", ".sgm");
        List<Entry<String, Double>> entries = new TfIdf().extract(data);

        int endIndex = (int) (entries.size() * 0.6);
        List<Entry<String, Double>> trainEntries = entries.subList(0, endIndex);
        List<Entry<String, Double>> testEntries = entries.subList(endIndex, entries.size());

        Knn<String, Double> knn = new Knn<>(trainEntries, testEntries, new EuclideanMetricDouble());
        Knn.Response res = knn.classify(3);

        System.out.println(res);
    }


    private static void voiw() throws IOException {
        List<ParsedData> data = new SgmParser("VOIVODESHIPS", voiv).parse("data/kek.txt");
        List<Entry<String, Double>> entries = new TfIdf().extract(data);

        int endIndex = (int) (entries.size() * 0.6);
        List<Entry<String, Double>> trainEntries = entries.subList(0, endIndex);
        List<Entry<String, Double>> testEntries = entries.subList(endIndex, entries.size());

        Knn<String, Double> knn = new Knn<>(trainEntries, testEntries, new EuclideanMetricDouble());
        Knn.Response res = knn.classify(3);

        System.out.println(res);
    }
}
















