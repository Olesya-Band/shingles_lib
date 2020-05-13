package com.nlp.filter;

import java.util.List;

/**
 * Базовый интерфейс для классов, предназначенных для удаления последовательностей символов из текста.
 */
public interface Filter {
    List<String> clean(String[] words);

    List<String> clean(Iterable<String> words);

    String clean(String text);
}
