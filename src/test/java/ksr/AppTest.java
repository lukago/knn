package ksr;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ksr.extraction.CountMapper;
import ksr.knn.Entry;
import ksr.metric.Levenshtein;
import ksr.parser.ParsedData;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigorous Test :-)
     */
    public void testApp() {
        ParsedData p1 = new ParsedData(new String[]{"awesome", "strange", "text"}, "label");
        ParsedData p2 = new ParsedData(new String[]{"awesomeone", "strange", "text"}, "label");

        List<ParsedData> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);

        CountMapper countMapper = new CountMapper();
        List<Entry<String, Integer>> entries = countMapper.extract(list);
        Levenshtein levenshtein = new Levenshtein();
        System.out.println(levenshtein.dist(entries.get(0), entries.get(1)));
    }
}
