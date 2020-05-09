package com.nlp.filter;

import java.util.List;

/**
 * Базовый интерфейс для классов, предназначенных для удаления последовательностей символов из текста.
 */
public interface Filter {
    /**
     * Метод, очищает входной массив символьных последовательностей от тех,
     * которые находятся в values.
     * @param words - входной массив.
     * @return - копия входного массива, очищенная от значений из values.
     */
    List<String> clean(String[] words);

    /**
     * Метод, очищает входную последовательность символьных последовательностей от тех,
     * которые находятся в values.
     * @param words - входная последовательность.
     * @return - копия входной последовательности, очищенная от значений из values.
     */
    List<String> clean(Iterable<String> words);

    /**
     * Метод, очищает входную строку от последовательностей символов из values.
     * @param text - входной текст.
     * @return - входной текст, очищенный от значений из values.
     */
    String clean(String text);
}
