package ksr.extraction;

import ksr.knn.Entry;
import ksr.parser.ParsedData;
import opennlp.tools.stemmer.PorterStemmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TfIdf implements FeatureExtractor<String, Double> {

    private Map<String, Integer> dict;

    @Override
    public List<Entry<String, Double>> extract(List<ParsedData> data) {
        initDict(data);
        List<Entry<String, Double>> entries = new ArrayList<>();
        for (ParsedData parsedData : data) {
            Map<String, Double> wordsMap = new HashMap<>();
            for (String word : parsedData.getWords()) {
                String newWord = new PorterStemmer().stem(word);
                if (dict.containsKey(newWord) && !wordsMap.containsKey(newWord)) {
                    double tf = countOccurences(word, parsedData.getWords()) / parsedData.getWords().length;
                    double idf = Math.log(data.size() / dict.get(newWord));
                    wordsMap.put(newWord, tf * idf);
                }
            }
            entries.add(new Entry<>(wordsMap, parsedData.getLabel()));
        }
        return entries;
    }

    private void initDict(List<ParsedData> data) {
        dict = new HashMap<>();
        Set<String> stopWords = StopWords.readStopWords();
        for (ParsedData parsedData : data) {
            boolean occured = false;
            for (String word : parsedData.getWords()) {
                String newWord = new PorterStemmer().stem(word);
                if (!occured && dict.containsKey(newWord)) {
                    dict.put(newWord, dict.get(newWord) + 1);
                    occured = true;
                } else if (!stopWords.contains(newWord) && newWord.length() > 2) {
                    dict.put(newWord, 1);
                }
            }
        }
    }

    private double countOccurences(String word, String[] words) {
        double cnt = 0;
        for (String w : words)
            if (word.equals(w))
                cnt++;
        return cnt;
    }
}
