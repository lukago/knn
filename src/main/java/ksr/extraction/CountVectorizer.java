package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;
import opennlp.tools.stemmer.PorterStemmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountVectorizer implements FeatureExtractor<Integer, Integer> {

    private HashMap<String, Integer> dict;

    @Override
    public List<Entry<Integer, Integer>> extract(List<ParsedData> data) {
        initDict(data);
        List<Entry<Integer, Integer>> entries = new ArrayList<>();
        for (ParsedData parsedData : data) {
            Map<Integer, Integer> wordsMap = new HashMap<>();
            for (String word : parsedData.getWords()) {
                String newWord = new PorterStemmer().stem(word);
                Integer dictIndex = dict.get(newWord);
                if (dictIndex != null && !wordsMap.containsKey(dictIndex)) {
                    wordsMap.put(dictIndex, countOccurences(word, parsedData.getWords()));
                }
            }

            wordsMap = trim(wordsMap, 10);
            entries.add(new Entry<>(wordsMap, parsedData.getLabel()));
        }
        return entries;
    }

    private void initDict(List<ParsedData> data) {
        dict = new HashMap<>();
        Set<String> stopWords = StopWords.readStopWords();
        int indexer = 0;
        for (ParsedData parsedData : data) {
            for (String word : parsedData.getWords()) {
                String newWord = new PorterStemmer().stem(word);
                if (!dict.containsKey(newWord) && !stopWords.contains(newWord) && newWord.length() > 2) {
                    dict.put(newWord, indexer++);
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

    private Map<Integer, Integer> trim(Map<Integer, Integer> map, int count) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .limit(count)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
}
