package com.nlp.filter;

import java.util.List;

public interface Filter {
    List<String> clean(String[] words);

    List<String> clean(Iterable<String> words);

    String clean(String text);
}
