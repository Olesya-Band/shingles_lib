package com.nlp.shingle;

import java.util.*;

/**
 * Класс, предназначенный для подсчета количества одинаковых шинглов в двух последовательностях.
 * @param <T> - тип шингла.
 */
public class ShingleComparator<T> {
    private Comparator<T> comparator;

    private int count;

    /**
     * Конструктор, принимает на вход @comparator и @count
     * @param comparator алгоритм сравнения двух шинглов
     * @param count минимальное количество шинглов
     */
    public ShingleComparator(Comparator<T> comparator, int count) {
        checkCount(count);

        this.comparator = Objects.requireNonNull(comparator, "comparator");
        this.count = count;
    }

    private void checkCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 1");
        }
    }

    /**
     * Метод, который возвращает минимальное количество шинглов.
     */
    public int getCount() {
        return count;
    }

    /**
     * Метод выполнет подсчет кол-ва одинаковых шинглов в двух последовательностях.
     * @param first - первая последовательность шинглов.
     * @param second - вторая последовательность шинглов.
     * @return - кол-во одинаковых шинглов.
     */
    public int compare(List<T> first, List<T> second) {
        int count = Math.min(this.count, Math.min(first.size(), second.size()));

        var min = (first.size() > second.size()) ? second : first;
        var max = (first.size() > second.size()) ? first : second;

        max = new ArrayList<>(max);
        max.sort(comparator);

        int equals = 0;

        for (T shingle : min) {
            if (Collections.binarySearch(max, shingle, comparator) > -1) {
                ++equals;
            }

            if (--count == 0) {
                break;
            }
        }

        return equals;
    }
}
