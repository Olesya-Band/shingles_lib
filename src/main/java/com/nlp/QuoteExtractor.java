package com.nlp;

import java.util.*;

/**
 * Класс, предназначенный для извлечения цитат из текста.
 */
public class QuoteExtractor {

    private int wordsCount;
    private Set<Character> quoteSymbols;

    /**
     * Конструктор, принимает на вход @quoteSymbols и @wordsCount.
     */
    public QuoteExtractor(Iterable<Character> quoteSymbols, int wordsCount) {
        checkQuoteSymbols(quoteSymbols);
        checkCount(wordsCount);

        this.quoteSymbols = new HashSet<>();
        quoteSymbols.forEach(this.quoteSymbols::add);

        this.wordsCount = wordsCount;
    }

    private void checkQuoteSymbols(Iterable<Character> quoteSymbols) {
        Objects.requireNonNull(quoteSymbols, "quoteSymbols");
        quoteSymbols.forEach(symbol -> Objects.requireNonNull(symbol, "symbol"));
    }

    private void checkCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count < 1");
        }
    }

    /**
     * Метод, который возвращает признаки цитат (кавычные символы)
     */
    public Set<Character> getQuoteSymbols() {
        return new HashSet<>(quoteSymbols);
    }

    /**
     * Метод, который возвращает минимальное количество слов в цитате.
     */
    public int getWordsCount() {
        return wordsCount;
    }

    /**
     * Метод, извлекает все цитаты из текста, которые разделены символами из @quoteSymbols
     */
    public Set<String> extract(String text) {
        return extract(getQuotesIndexes(text), text);
    }

    private Map<Character, List<Integer>> getQuotesIndexes(String text) {
        var quotesIndexes = new HashMap<Character, List<Integer>>(quoteSymbols.size());

        quoteSymbols.forEach(symbol -> quotesIndexes.put(symbol, new ArrayList<>(0)));

        for (int i = 0; i < text.length(); ++i) {
            if (quoteSymbols.contains(text.charAt(i))) {
                quotesIndexes.get(text.charAt(i)).add(i);
            }
        }

        return quotesIndexes;
    }

    private Set<String> extract(Map<Character, List<Integer>> quotesIndexes, String text) {
        var quotes = new HashSet<String>();

        for (var indexes : quotesIndexes.values()) {
            if (indexes.size() % 2 == 0) {
                for (int i = 0; i < indexes.size(); i += 2) {
                    int start = indexes.get(i) + 1;
                    int end = indexes.get(i + 1);

                    var quote = text.substring(start, end);

                    if (isEnoughWords(quote)) {
                        quotes.add(quote);
                    }
                }
            }
        }

        return quotes;
    }

    private boolean isEnoughWords(String quote) {
        int spaceCount = 0;

        for (int i = 0; i < quote.length(); ++i) {
            if (quote.charAt(i) == ' ') {
                ++spaceCount;
            }
        }

        return spaceCount + 1 >= wordsCount;
    }
}
