package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountVectorizer implements FeatureExtractor {

    private HashMap<String, Integer> dict;

    @Override
    public List<Entry> extract(List<ParsedData> data) {
        initDict(data);
        List<Entry> entries = new ArrayList<>();
        for (ParsedData parsedData : data) {
            Map<Integer, Integer> wordsMap = new HashMap<>();
            for (String word : parsedData.getWords()) {
                if (!wordsMap.containsKey(dict.get(word))) {
                    wordsMap.put(dict.get(word), countOccurences(word, parsedData.getWords()));
                }
            }
            entries.add(new Entry(wordsMap, parsedData.getLabel()));
        }
        return entries;
    }

    private void initDict(List<ParsedData> data) {
        dict = new HashMap<>();
        int indexer = 0;
        for (ParsedData parsedData : data) {
            for (String word : parsedData.getWords()) {
                if (!dict.containsKey(word)) {
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
