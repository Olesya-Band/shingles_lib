package com.nlp.shingle;

import java.util.*;
import java.util.function.Function;

public class ShingleCalculator<T> {
    private int length;

    private Function<String, T> hashFunc;

    public ShingleCalculator(Function<String, T> hashFunc) {
        this(1, hashFunc);
    }

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

    public int getLength() {
        return length;
    }

    public List<T> calc(String text) {
        return calc(text.split(" "));
    }

    public List<T> calc(String[] words) {
        return calc(Arrays.asList(words));
    }

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
