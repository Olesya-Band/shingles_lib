package com.nlp.shingle;

import java.util.*;
import java.util.function.Function;

/**
 * Класс, предназначенный для подсчета шинглов от слов текста.
 * @param <T> - тип шингла.
 */
public class ShingleCalculator<T> {
    /**
     * Количество слов в последовательности, от которой считается шингл.
     */
    private int length;

    /**
     * Функция, с помощью которой вычисляется значение шингла.
     */
    private Function<String, T> hashFunc;

    /**
     * Конструктор, принимает на вход функцию для вычисления шингла.
     * Количество слов в последовательности от которой считается шингл = 1.
     */
    public ShingleCalculator(Function<String, T> hashFunc) {
        this(1, hashFunc);
    }

    /**
     * Конструктор, принмает на вход количество слов в последовательности
     * от которой считается шингл и функцию для вычисления шингла.
     */
    public ShingleCalculator(int length, Function<String, T> hashFunc) {
        checkLength(length);
        Objects.requireNonNull(hashFunc, "hashFunc");

        this.length = length;
        this.hashFunc = hashFunc;
    }

    /**
     * Метод, выполняет проверку количества слов в последовательности от которой считается шингл.
     * @param length - значение для проверки.
     * @throws IllegalArgumentException - если значение для проверки меньше 1.
     */
    private void checkLength(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
    }

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
     * Метод, выполняет вычисление шинглов от переданного списка слов текста.
     * @param words - список слов текста.
     * @return - список шинглов.
     */
    public List<T> calc(List<String> words) {
        // Если длина переданного списка слов меньше длины шингла,
        // то просто считаем один шингл от этого списка слов.
        if (words.size() <= length) {
            return Collections.singletonList(hashFunc.apply(String.join(" ", words)));
        }

        // Определяем кол-во шинглов, которое необходимо подсчитать.
        var count = words.size() - length + 1;
        // Создаем список, в который будем добавлять вычесленные шинглы.
        var shingles = new ArrayList<T>(count);
        // Буфер для составления одной строки из нескольких слов.
        var buf = new StringBuilder();

        // Идем по всем словам, которые являются начальными в шинглах.
        for (int i = 0; i < count; ++i) {
            // Добавляем начальное слово и length - 1 слов, следующих за ним в буфер, разделяя их пробелом.
            for (int j = i; j < i + length; ++j) {
                buf.append(words.get(j)).append(' ');
            }

            // Считаем значение шингла от слов из буфера и добавляем его в результирующий список.
            shingles.add(hashFunc.apply(buf.toString()));
            // Очищаем буфер для вычисления следующего шингла.
            buf.delete(0, buf.length());
        }

        // Возвращаем список вычисленных шинглов.
        return shingles;
    }
}
