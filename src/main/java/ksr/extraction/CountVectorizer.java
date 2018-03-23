package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountVectorizer implements FeatureExtractor<Integer, Integer> {

    private HashMap<String, Integer> dict;
    private static final String STOP_PATH = "data/stopwords.txt";

    @Override
    public List<Entry<Integer, Integer>> extract(List<ParsedData> data) {
        initDict(data);
        List<Entry<Integer, Integer>> entries = new ArrayList<>();
        for (ParsedData parsedData : data) {
            Map<Integer, Integer> wordsMap = new HashMap<>();
            for (String word : parsedData.getWords()) {
                word = word.toLowerCase();
                Integer dictIndex = dict.get(word);
                if (dictIndex != null && !wordsMap.containsKey(dictIndex)) {
                    wordsMap.put(dictIndex, countOccurences(word, parsedData.getWords()));
                }
            }
            entries.add(new Entry<>(wordsMap, parsedData.getLabel()));
        }
        return entries;
    }

    private void initDict(List<ParsedData> data) {
        dict = new HashMap<>();
        Set<String> stopWords = StopWords.readStopWords(STOP_PATH);
        int indexer = 0;
        for (ParsedData parsedData : data) {
            for (String word : parsedData.getWords()) {
                word = word.toLowerCase();
                if (!dict.containsKey(word) && !stopWords.contains(word)) {
                    dict.put(word, indexer++);
                }
            }
        }
    }

    private int countOccurences(String word, String[] words) {
        int cnt = 0;
        for (String w : words)
            if (word.equals(w))
                cnt++;
        return cnt;
    }
}
