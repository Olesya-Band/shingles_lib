package com.nlp.shingle;

import java.util.*;

public class ShingleComparator<T> {
    private Comparator<T> comparator;

    private int count;

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

    public int getCount() {
        return count;
    }

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
