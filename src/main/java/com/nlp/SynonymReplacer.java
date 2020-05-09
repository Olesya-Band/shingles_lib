package com.nlp;

import java.io.*;
import java.util.*;

public class SynonymReplacer {
    private Map<String, String> synonyms;

    public SynonymReplacer(String filePath, String separator) {
        synonyms = new HashMap<>();

        try (var in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath)))) {
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

    public Map<String, String> getSynonyms() {
        return new HashMap<>(synonyms);
    }

    public String getSynonym(String word) {
        return synonyms.getOrDefault(word, word);
    }

    public List<String> replace(String text) {
        return replace(text.split(" "));
    }

    public List<String> replace(String[] words) {
        return replace(Arrays.asList(words));
    }

    public List<String> replace(List<String> words) {
        for (int i = 0; i < words.size(); ++i) {
            words.set(i, getSynonym(words.get(i)));
        }

        return words;
    }
}
