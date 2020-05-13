package com.nlp;

import java.util.*;

/**
 * Класс, который хранит результаты работы алгоритма и используется для передачи их (данных) в интерфейс.
 */
public class Result
{
    double measure;

    Set<String> fQuotes;
    int fQuoteCount;
    int fQuoteMidLen;
    double fQuotePercent;

    Set<String> sQuotes;
    int sQuoteCount;
    int sQuoteMidLen;
    double sQuotePercent;

    Set<String> equalsQuotes;

    List<byte[]> fShingles;
    List<byte[]> sShingles;

    long time;

    /**
     * Метод для получения цитат из первого текста.
     */
    public Set<String> getfQuotes() {
        return new HashSet<>(fQuotes);
    }

    /**
     * Метод для получения количества цитат первого текста.
     */
    public int getfQuoteCount() {
        return fQuoteCount;
    }
    /**
     * Метод для получения средней длины цитаты из первого текста.
     */
    public int getfQuoteMidLen() {
        return fQuoteMidLen;
    }

    /**
     * Метод для получения процентного соотношения цитат в тексте.
     */
    public double getfQuotePercent() {
        return fQuotePercent;
    }

    /**
     * Метод для получения цитат из второго текста.
     */
    public Set<String> getsQuotes() {
        return new HashSet<>(sQuotes);
    }

    /**
     * Метод для получения количества цитат из второго текста.
     */
    public int getsQuoteCount() {
        return sQuoteCount;
    }

    /**
     * Метод для получения средней длины цитат второго текста.
     */
    public int getsQuoteMidLen() {
        return sQuoteMidLen;
    }

    /**
     * Метод для получения процентного соотношения цитат второго текста.
     */
    public double getsQuotePercent() {
        return sQuotePercent;
    }

    /**
     * Метод для получения общих цитат первого и второго текста.
     */
    public Set<String> getEqualsQuotes() {
        return new HashSet<>(equalsQuotes);
    }

    /**
     * Метод для получения шинглов первого текста.
     */
    public List<byte[]> getfShingles() {
        return new ArrayList<>(fShingles);
    }

    /**
     * Метод для получения шинглов второго текста.
     */
    public List<byte[]> getsShingles() {
        return new ArrayList<>(sShingles);
    }

    /**
     * Метод для получения меры схожести в процентах.
     */
    public double getMeasure() {
        return measure;
    }

    /**
     * Метод для получения времени работы алгоритма.
     */
    public long getTime() {
        return time;
    }
}
