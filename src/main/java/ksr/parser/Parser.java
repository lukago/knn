package ksr.parser;

import java.io.IOException;
import java.util.List;

public interface Parser {

    List<ParsedData> parse(String filepath) throws IOException;

    List<ParsedData> parseAll(String dirpath, String pattern) throws IOException;
}
