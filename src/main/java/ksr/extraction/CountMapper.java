package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;
import opennlp.tools.stemmer.PorterStemmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountMapper implements FeatureExtractor<String, Integer> {

    private Set<String> dict;

    @Override
    public List<Entry<String, Integer>> extract(List<ParsedData> data) {
        initDict(data);
        List<Entry<String, Integer>> entries = new ArrayList<>();
        for (ParsedData parsedData : data) {
            Map<String, Integer> wordsMap = new HashMap<>();
            for (String word : parsedData.getWords()) {
                String newWord = new PorterStemmer().stem(word);
                if (dict.contains(newWord) && !wordsMap.containsKey(newWord)) {
                    wordsMap.put(newWord, countOccurences(word, parsedData.getWords()));
                }
            }
            entries.add(new Entry<>(wordsMap, parsedData.getLabel()));
        }
        return entries;
    }

    private void initDict(List<ParsedData> data) {
        dict = new HashSet<>();
        Set<String> stopWords = StopWords.readStopWords();
        for (ParsedData parsedData : data) {
            for (String word : parsedData.getWords()) {
                String newWord = new PorterStemmer().stem(word);
                if (!dict.contains(newWord) && !stopWords.contains(newWord) && newWord.length() > 2) {
                    dict.add(newWord);
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
