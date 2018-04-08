package ksr.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SgmParser implements Parser {

    private final String[] places = {"west-germany", "usa", "france", "uk", "canada", "japan"};
    private final Set<String> placesSet = new HashSet<>(Arrays.asList(places));

    @Override
    public List<ParsedData> parse(String filepath) throws IOException {
        List<ParsedData> parsedData = new ArrayList<>();
        File input = new File(filepath);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Elements reutres = doc.getElementsByTag("REUTERS");

        for (Element reuter : reutres) {
            Element places = reuter.getElementsByTag("PLACES").first();
            Elements placesNames = places.getElementsByTag("D");
            if (placesNames.size() == 1) {
                String placeName = placesNames.first().text();
                if (placesSet.contains(placeName)) {
                    List<TextNode> texts = reuter.getElementsByTag("TEXT").first().textNodes();
                    String[] words = texts.get(texts.size() - 1).text()
                            .replace(",", "")
                            .replace(".", "")
                            .replace("\"", "")
                            .replace("(", "")
                            .replace(")", "")
                            .replace("/", " ")
                            .replace("<", "")
                            .replace(">", "")
                            .replace("\u0003", "")
                            .replaceAll("\\d+", "")
                            .toLowerCase()
                            .split(" ");
                    parsedData.add(new ParsedData(words, placeName));
                }
            }
        }

        return parsedData;
    }

    @Override
    public List<ParsedData> parseAll(String dirpath, String pattern) throws IOException {
        File dir = new File(dirpath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(pattern));
        List<ParsedData> parsedData = new ArrayList<>();
        if (files == null) return parsedData;
        for (File f : files)
            parsedData.addAll(parse(f.getAbsolutePath()));
        return parsedData;
    }
}
