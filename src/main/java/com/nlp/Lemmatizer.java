package com.nlp;

import java.util.ArrayList;
import java.util.List;
import ru.textanalysis.tawt.sp.api.SyntaxParser;

/**
 * Множество, предназначенное для получения лемм последовательности слов.
 */
public enum Lemmatizer {
    INSTANCE;

    private SyntaxParser parser;

    Lemmatizer() {
        parser = new SyntaxParser();
        parser.init();
    }

    /**
     * Метод, предназначенный для получения лемм слов.
     * @param words- массив, содержащий слова, леммы которых нужно получить.
     * @return - список лемм переданных слов, в порядке их следования в массиве.
     */
    public List<String> getLemms(String[] words) {
        return getLemms(String.join(" ", words));
    }

    /**
     * Метод, предназначенный для получения лемм слов.
     * @param words - последовательность, содержащая слова, леммы которых нужно получить.
     * @return - список лемм переданных слов, в порядке их следования в последовательности.
     */
    public List<String> getLemms(Iterable<String> words) {
        return getLemms(String.join(" ", words));
    }

    /**
     * Метод, предназначенный для получения лемм слов.
     * @param text - строка, содержащая слова, леммы которых нужно получить.
     * @return - список лемм переданных слов, в порядке их следования в строке.
     */
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
