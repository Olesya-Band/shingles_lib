package com.nlp.shingle;

import java.util.*;

/**
 * Класс, предназначенный для подсчета количества одинаковых шинглов в двух последовательностях.
 * @param <T> - тип шингла.
 */
public class ShingleComparator<T> {
    /**
     * Функция, с помощью которой выполняется сравнение двух шинглов.
     */
    private Comparator<T> comparator;

    /**
     * Минимальное кол-во шинглов, которое нужно сравнить.
     */
    private int count;

    /**
     * Конструктор, принимает на вход @comparator и @count
     * @param comparator
     * @param count
     */
    public ShingleComparator(Comparator<T> comparator, int count) {
        checkCount(count);

        this.comparator = Objects.requireNonNull(comparator, "comparator");
        this.count = count;
    }

    /**
     * Метод, выполняет проверку минимального вол-ва шинглов для проверки.
     * @param count - минимальное кол-во шинглов для проверки.
     * @throws IllegalArgumentException если count < 1
     */
    private void checkCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 1");
        }
    }

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
        // Вычисляем кол-во шинглов, которое нужно сравнить.
        int count = Math.min(this.count, Math.min(first.size(), second.size()));

        // Определяем меньшую и большую последовательности шинглов.
        var min = (first.size() > second.size()) ? second : first;
        var max = (first.size() > second.size()) ? first : second;

        // Создаем копию большей последовательности шинглов.
        max = new ArrayList<>(max);
        // Сортируем ее по неубыванию, для бинарного поиска.
        max.sort(comparator);

        // Кол-во одинаковых шинглов.
        int equals = 0;

        // Проходимся по шинглам меньшей последовательности.
        for (T shingle : min) {
            // Ищем каждый пройденный шингл в большей последовательности.
            if (Collections.binarySearch(max, shingle, comparator) > -1) {
                // Если нашли, то увеличиваем счетчик одинаковых шинглов на 1.
                ++equals;
            }

            // Уменьшаем кол-во шинглов, которые нужно сравнить на 1.
            if (--count == 0) {
                // Если больше шинглы сравивать не надо, то прерываем цикл поиска.
                break;
            }
        }

        // Возвращаем кол-во одинаковых шинглов.
        return equals;
    }
}
