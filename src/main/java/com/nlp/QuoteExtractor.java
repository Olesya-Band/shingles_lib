package com.nlp;

import java.util.*;

/**
 * Класс, предназначенный для извлечения цитат из текста.
 */
public class QuoteExtractor {
    /**
     * Множество символов, с помощью которых разделены цитаты, которые нужно извлечь.
     */
    private Set<Character> quoteSymbols;

    /**
     * Минимальное кол-во слов, которое должно быть в цитате, чтобы ее извлечь.
     */
    private int wordsCount;

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

    /**
     * Метод, выполняет проверку @quoteSymbols на отсутствие null.
     * @throws NullPointerException если @quoteSymbols = null или хотябы один из элементов
     * в @quoteSymbols равен нулю.
     */
    private void checkQuoteSymbols(Iterable<Character> quoteSymbols) {
        Objects.requireNonNull(quoteSymbols, "quoteSymbols");
        quoteSymbols.forEach(symbol -> Objects.requireNonNull(symbol, "symbol"));
    }

    /**
     * Метод, выполняет проверку count.
     * @throws IllegalArgumentException если count < 1.
     */
    private void checkCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count < 1");
        }
    }

    public Set<Character> getQuoteSymbols() {
        return new HashSet<>(quoteSymbols);
    }

    public int getWordsCount() {
        return wordsCount;
    }

    /**
     * Метод, извлекает все цитаты из текста, которые разделены символами из @quoteSymbols
     */
    public Set<String> extract(String text) {
        return extract(getQuotesIndexes(text), text);
    }

    /**
     * Метод, возвращает позиции всех символов из @quoteSymbols в тексте.
     */
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

    /**
     * Метод, извлекает все цитаты из текста, которые разделены символами из @quoteSymbols
     */
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

    /**
     * Метод, выполняет подсчет слов в цитате и определяет, достаточно ли их для добавления в результат.
     * @param quote - цитата
     * @return - true, если слов достаточно, false - если не достаточно.
     */
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
