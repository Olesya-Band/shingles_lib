package com.nlp;

import ru.textanalysis.tawt.sp.api.SyntaxParser;

import java.util.ArrayList;
import java.util.List;

public enum Lemmatizer {
    INSTANCE;

    private SyntaxParser parser;

    Lemmatizer() {
        parser = new SyntaxParser();
        parser.init();
    }

    public List<String> getLemms(String[] words) {
        return getLemms(String.join(" ", words));
    }

    public List<String> getLemms(Iterable<String> words) {
        return getLemms(String.join(" ", words));
    }

    public List<String> getLemms(String text) {
        var treeSentence = parser.getTreeSentence(text);
        var lemms = new ArrayList<String>();

        treeSentence.forEach(
                node -> node.applyConsumer(
                        words -> words.forEach(
                                word -> {
                                    var forms = new ArrayList<String>(1);

                                    word.applyConsumer(
                                            omoForm -> {
                                                if (forms.size() == 0) {
                                                    forms.add(omoForm.getCurrencyOmoForm().getInitialFormString());
                                                }
                                            }
                                    );

                                    lemms.add(forms.get(0));
                                }
                        )
                )
        );

        return lemms;
    }
}
