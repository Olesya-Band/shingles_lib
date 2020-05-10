import com.nlp.SynonymReplacer;
import com.nlp.TextHandler;
import com.nlp.shingle.ShingleCalculator;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;
import ru.textanalysis.tawt.graphematic.parser.text.GParserImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class HashTimeCompare {
    private static final String TEXT_PATH = "src/test/resources/text17.txt";

    private static final String SYNONYMS_PATH = "ru_synonyms.txt";
    private static final String SYNONYMS_SEPARATOR = ";";

    private static final int SHINGLE_LENGTH = 3;

    private List<List<String>> phrases;

    private ShingleCalculator<byte[]> javaCalc;
    private ShingleCalculator<byte[]> md5Calc;
    private ShingleCalculator<byte[]> sha1Calc;
    private ShingleCalculator<byte[]> sha256Calc;

    @Before
    public void initTextPhrases() throws IOException {
        var textHandler = new TextHandler(
                new GParserImpl(),
                new SynonymReplacer(SYNONYMS_PATH, SYNONYMS_SEPARATOR)
        );

        var text = Files.readString(Paths.get(TEXT_PATH));

        phrases = textHandler.apply(text);
    }

    @Before
    public void initCalc() {
        javaCalc = new ShingleCalculator<>(
                SHINGLE_LENGTH,
                str -> {
                    int h = str.hashCode();
                    return new byte[] {
                            (byte) ((h>> 24) & 0xff),
                            (byte) ((h >> 16) & 0xff),
                            (byte) ((h >> 8) & 0xff),
                            (byte) (h & 0xff),
                    };
                }
        );

        MessageDigest hashFuncMd5;
        try {
            hashFuncMd5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md5Calc = new ShingleCalculator<>(
                SHINGLE_LENGTH,
                str -> hashFuncMd5.digest(str.getBytes())
        );

        MessageDigest hashFuncSha1;
        try {
            hashFuncSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        sha1Calc = new ShingleCalculator<>(
                SHINGLE_LENGTH,
                str -> hashFuncSha1.digest(str.getBytes())
        );

        MessageDigest hashFuncSha256;
        try {
            hashFuncSha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        sha256Calc = new ShingleCalculator<>(
                SHINGLE_LENGTH,
                str -> hashFuncSha256.digest(str.getBytes())
        );
    }

    @Test
    public void timeCompare() {
        long start;
        long end;

        start = System.currentTimeMillis();
        for (var p : phrases) {
            javaCalc.calc(p);
        }
        end = System.currentTimeMillis();

        System.out.println(String.format("Java time: %d ms", end - start));

        start = System.currentTimeMillis();
        for (var p : phrases) {
            md5Calc.calc(p);
        }
        end = System.currentTimeMillis();

        System.out.println(String.format("MD5 time: %d ms", end - start));

        start = System.currentTimeMillis();
        for (var p : phrases) {
            sha1Calc.calc(p);
        }
        end = System.currentTimeMillis();

        System.out.println(String.format("SHA1 time: %d ms", end - start));

        start = System.currentTimeMillis();
        for (var p : phrases) {
            sha256Calc.calc(p);
        }
        end = System.currentTimeMillis();

        System.out.println(String.format("SHA256 time: %d ms", end - start));
    }
}
