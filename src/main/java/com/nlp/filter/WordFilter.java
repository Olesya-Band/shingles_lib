package com.nlp.filter;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum WordFilter implements Filter {
    INSTANCE;

    private JMorfSdk sdk;

    WordFilter() {
        sdk = JMorfSdkFactory.loadFullLibrary();
    }

    public List<String> clean(String[] words) {
        return clean(Arrays.asList(words));
    }

    public List<String> clean(Iterable<String> words) {
        var res = new ArrayList<String>();

        for (var word : words) {
            if (!needToFilter(sdk.getTypeOfSpeeches(word))) {
                res.add(word);
            }
        }

        return res;
    }

    public String clean(String text) {
        return String.join(" ", clean(text.split(" ")));
    }

    private boolean needToFilter(List<Byte> types) {
        for (var type : types) {
            switch (type) {
                case MorfologyParameters.TypeOfSpeech.ANAPHORICPRONOUN:
                case MorfologyParameters.TypeOfSpeech.COLLECTIVENUMERAL:
                case MorfologyParameters.TypeOfSpeech.INTERJECTION:
                case MorfologyParameters.TypeOfSpeech.NOUNPRONOUN:
                case MorfologyParameters.TypeOfSpeech.PRETEXT:
                case MorfologyParameters.TypeOfSpeech.UNION:
                    return true;
            }
        }

        return false;
    }
}
