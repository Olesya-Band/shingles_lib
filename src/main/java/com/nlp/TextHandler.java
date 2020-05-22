package com.nlp;

import com.nlp.filter.WordFilter;
import ru.textanalysis.tawt.graphematic.parser.text.GraphematicParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Фасад, предназначенный для предварительной обработки текста.
 * Выполняет: разделение текста на фразы, отчистку от стоп-слов
 * и символов пунктуации, лемматизацию, замену синонимов.
 */
public class TextHandler {
    private final GraphematicParser phraseParser;
    private final SynonymReplacer synReplacer;
    private final WordFilter wordFilter;

    public TextHandler(GraphematicParser phraseParser, SynonymReplacer synReplacer) {
        Objects.requireNonNull(phraseParser, "phraseParser");
        Objects.requireNonNull(synReplacer, "synReplacer");

        this.phraseParser = phraseParser;
        this.synReplacer = synReplacer;
        this.wordFilter = WordFilter.INSTANCE;
    }

    /**
     * Метод, разбивает текст на отдельные фразы.
     *
     * @param text текст
     * @return phrases отдельные фразы
     */
    public List<List<String>> apply(String text) {
        text = text.trim()
                .toLowerCase()
                .replaceAll(" +", " ");

        var phrases = getPhrases(phraseParser.parserText(text));

        for (int i = 0; i < phrases.size(); ++i) {
            var phrase = phrases.get(i);

            phrase = WordFilter.INSTANCE.clean(phrase);
            phrase = Lemmatizer.INSTANCE.getLemms(phrase);
            phrase = synReplacer.replace(phrase);

            phrases.set(i, phrase);
        }

        return phrases;
    }

    private List<List<String>> getPhrases(List<List<List<List<String>>>> text) {
        var phrases = new ArrayList<List<String>>();

        for (var paragraph : text) {
            for (var sentence : paragraph) {
                phrases.addAll(sentence);
            }
        }

        return phrases;
    }
}
