package com.nlp.shingle;

import java.util.*;
import java.util.function.Function;

/**
 * Класс, предназначенный для подсчета шинглов от слов текста.
 * @param <T> - тип шингла.
 */
public class ShingleCalculator<T> {
    private int length;

    private Function<String, T> hashFunc;

    /**
     * Конструктор, принимает на вход функцию для вычисления шингла.
     * Количество слов в последовательности от которой считается шингл = 1.
     */
    public ShingleCalculator(Function<String, T> hashFunc) {
        this(1, hashFunc);
    }

    /**
     * Конструктор, принимает на вход количество слов в последовательности
     * от которой считается шингл и функцию для вычисления шингла.
     */
    public ShingleCalculator(int length, Function<String, T> hashFunc) {
        checkLength(length);
        Objects.requireNonNull(hashFunc, "hashFunc");

        this.length = length;
        this.hashFunc = hashFunc;
    }

    private void checkLength(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
    }

    /**
     * Метод, который возвращает количество слов в шингле.
     */
    public int getLength() {
        return length;
    }

    /**
     * Метод, выполняет вычисление шинглов от переданного текста.
     * @param text - текст.
     * @return - список шинглов.
     */
    public List<T> calc(String text) {
        return calc(text.split(" "));
    }

    /**
     * Метод, выполняет вычисление шинглов от переданного массива слов текста.
     * @param words - список слов текста.
     * @return - список шинглов.
     */
    public List<T> calc(String[] words) {
        return calc(Arrays.asList(words));
    }

    /**
     * Метод, выполняет вычисление шинглов от переданного массива слов текста.
     * @param words - список слов текста.
     * @return - список шинглов.
     */
    public List<T> calc(List<String> words) {
        if (words.size() <= length) {
            return Collections.singletonList(hashFunc.apply(String.join(" ", words)));
        }

        var count = words.size() - length + 1;
        var shingles = new ArrayList<T>(count);
        var buf = new StringBuilder();

        for (int i = 0; i < count; ++i) {
            for (int j = i; j < i + length; ++j) {
                buf.append(words.get(j)).append(' ');
            }

            shingles.add(hashFunc.apply(buf.toString()));
            buf.delete(0, buf.length());
        }

        return shingles;
    }
}
