package com.nlp;

import com.nlp.shingle.ShingleCalculator;
import com.nlp.shingle.ShingleComparator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import ru.textanalysis.tawt.graphematic.parser.text.GParserImpl;

/**
 * Класс, содержит всю основную логику, которая используется  для обработки и сравнения текстов.
 */
public class TextComparator {
    private static final Set<Character> QUOTE_CHARS = Set.of('\"', '\'');
    private static final int QUOTE_LENGTH = 2;

    private static final String SYNONYMS_PATH = "/ru_synonyms.txt";
    private static final String SYNONYMS_SEPARATOR = ";";

    private static final int SHINGLE_LENGTH = 3;

    private static final int MAX_COMPARE_COUNT = 100;

    private QuoteExtractor quotesExtractor;
    private TextHandler textHandler;

    private ShingleCalculator<byte[]> shingleCalculator;
    private ShingleComparator<byte[]> shingleComparator;

    /**
     * Конструктор, который инициирует зависимости TextComparator.
     */
    public TextComparator() {
        this.quotesExtractor = new QuoteExtractor(QUOTE_CHARS, QUOTE_LENGTH);
        this.textHandler = new TextHandler(
                new GParserImpl(),
                new SynonymReplacer(SYNONYMS_PATH, SYNONYMS_SEPARATOR)
        );

        MessageDigest hashFunc;
        try {
            hashFunc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        shingleCalculator = new ShingleCalculator<>(
                SHINGLE_LENGTH,
                str -> hashFunc.digest(str.getBytes())
        );
        this.shingleComparator = new ShingleComparator<>(
                (a, b) -> {
                    for (int i = 0; i < a.length; ++i) {
                        if (a[i] != b[i]) {
                            return a[i] - b[i];
                        }
                    }

                    return 0;
                },
            MAX_COMPARE_COUNT
        );
    }


    /**
     * Конструктор, принимает на вход первый и второй текст.
     * @param fTextStr первый текст
     * @param sTextStr второй текст
     * @return result
     */
    public Result compare(String fTextStr, String sTextStr, boolean needShingles)
    {
        var result = new Result();

        long startAll = System.currentTimeMillis();

        var fQuotes = new HashSet<>(quotesExtractor.extract(fTextStr));

        if (fQuotes.isEmpty())
        {
            result.fQuotes = new HashSet<>();
            result.fQuoteMidLen = 0;
            result.fQuotePercent = 0;
        }
        else
        {
            result.fQuotes = new HashSet<>(fQuotes);
            result.fQuoteMidLen = fQuotes.stream().mapToInt(s -> s.split(" ").length).sum() / fQuotes.size();
            result.fQuotePercent = (double) fQuotes.stream().mapToInt(String::length).sum() / fTextStr.length() * 100;
        }

        var sQuotes = new HashSet<>(quotesExtractor.extract(sTextStr));

        if (sQuotes.isEmpty()) {
            result.sQuotes = new HashSet<>();
            result.sQuoteMidLen = 0;
            result.sQuotePercent = 0;
        } else {
            result.sQuotes = new HashSet<>(sQuotes);
            result.sQuoteMidLen = sQuotes.stream().mapToInt(s -> s.split(" ").length).sum() / sQuotes.size();
            result.sQuotePercent = (double) sQuotes.stream().mapToInt(String::length).sum() / sTextStr.length() * 100;
        }

        var equalsQuotes = new HashSet<>(fQuotes);
        equalsQuotes.retainAll(sQuotes);

        result.equalsQuotes = new HashSet<>(equalsQuotes);

        var fPhrases = textHandler.apply(fTextStr);
        var sPhrases = textHandler.apply(sTextStr);

        var fShingles = new ArrayList<byte[]>();
        var sShingles = new ArrayList<byte[]>();

        for (var phrase : fPhrases)
        {
            fShingles.addAll(shingleCalculator.calc(phrase));
        }
        for (var phrase : sPhrases)
        {
            sShingles.addAll(shingleCalculator.calc(phrase));
        }

        result.fShingles = new ArrayList<>(fShingles);
        result.sShingles = new ArrayList<>(sShingles);

        int count = shingleComparator.compare(fShingles, sShingles);
        result.measure = (double) count / Math.min(MAX_COMPARE_COUNT, Math.min(fShingles.size(), sShingles.size())) * 100;

        long endAll = System.currentTimeMillis();

        result.time = endAll - startAll;

        return result;
    }
}
