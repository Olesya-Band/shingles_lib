package com.nlp;

import java.io.*;
import java.util.*;

/**
 * Класс, предназначенный для замены слов в тексте на их синонимы.
 */
public class SynonymReplacer {
    private Map<String, String> synonyms;

    /**
     * Конструктор, принмает на вход путь к файлу, в котором лежат последовательности
     * синонимов, и символ разделитель для синонимов. Каждая последовательность синонимов
     * должна быть расположена на отдельной строке.
     */
    public SynonymReplacer(String filePath, String separator) {
        synonyms = new HashMap<>();

        try (var in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)))) {
            String line;

            while ((line = in.readLine()) != null) {
                var words = line.split(separator);
                if (words.length > 1) {
                    for (var word : words) {
                        synonyms.put(word, words[0]);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Конструктор, принмает на вход отображение для синонимов.
     */
    public SynonymReplacer(Map<String, String> synonyms) {
        checkSynonyms(synonyms);

        this.synonyms = new HashMap<>(synonyms);
    }

    private void checkSynonyms(Map<String, String> synonyms) {
        Objects.requireNonNull(synonyms, "synonyms");

        synonyms.forEach((k, v) -> {
            Objects.requireNonNull(k, "key");
            Objects.requireNonNull(v, "value");
        });
    }

    /**
     * Метод, который возвращает словарь синонимов.
     */
    public Map<String, String> getSynonyms() {
        return new HashMap<>(synonyms);
    }

    /**
     * Метод возвращает синоним для определенного слова или если синоним не найден - само это слово.
     */
    public String getSynonym(String word) {
        return synonyms.getOrDefault(word, word);
    }

    /**
     * Метод, возвращает список синонимов для слов текста в порядке их следования в тексте.
     */
    public List<String> replace(String text) {
        return replace(text.split(" "));
    }

    /**
     * Метод, возвращает список синонимов для слов массива в порядке их следования в массиве.
     */
    public List<String> replace(String[] words) {
        return replace(Arrays.asList(words));
    }

    /**
     * Метод, возвращает список синонимов для слов списка в порядке их следования в списке.
     */
    public List<String> replace(List<String> words) {
        for (int i = 0; i < words.size(); ++i) {
            words.set(i, getSynonym(words.get(i)));
        }

        return words;
    }
}
