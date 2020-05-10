package com.nlp;

import java.util.*;

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

    public Set<String> getfQuotes() {
        return new HashSet<>(fQuotes);
    }

    public int getfQuoteCount() {
        return fQuoteCount;
    }

    public int getfQuoteMidLen() {
        return fQuoteMidLen;
    }

    public double getfQuotePercent() {
        return fQuotePercent;
    }

    public Set<String> getsQuotes() {
        return new HashSet<>(sQuotes);
    }

    public int getsQuoteCount() {
        return sQuoteCount;
    }

    public int getsQuoteMidLen() {
        return sQuoteMidLen;
    }

    public double getsQuotePercent() {
        return sQuotePercent;
    }

    public Set<String> getEqualsQuotes() {
        return new HashSet<>(equalsQuotes);
    }

    public List<byte[]> getfShingles() {
        return new ArrayList<>(fShingles);
    }

    public List<byte[]> getsShingles() {
        return new ArrayList<>(sShingles);
    }

    public double getMeasure() {
        return measure;
    }

    public long getTime() {
        return time;
    }
}
