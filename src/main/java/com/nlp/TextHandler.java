package com.nlp;

import com.nlp.filter.WordFilter;
import ru.textanalysis.tawt.graphematic.parser.text.GraphematicParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextHandler {
    private GraphematicParser phraseParser;
    private SynonymReplacer synReplacer;

    public TextHandler(GraphematicParser phraseParser, SynonymReplacer synReplacer) {
        Objects.requireNonNull(phraseParser, "phraseParser");
        Objects.requireNonNull(synReplacer, "synReplacer");

        this.phraseParser = phraseParser;
        this.synReplacer = synReplacer;
    }

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
