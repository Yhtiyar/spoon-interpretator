package main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yhtyyar created on 20.09.2020
 */
public class Tokenizer {
    private String sourceCode;
    private int sourceIndex;

    private final  SpoonWordParser parser;

    private List<Token> tokens;
    private int currIndex = -1;

    public Tokenizer(String sourceCode) {
        parser = new SpoonWordParser();
        this.sourceCode = sourceCode;
        tokens = new ArrayList<>();
        tokenize();

    }
    private void tokenize() {
        while (sourceIndex < sourceCode.length()){
            while (!parser.eatChar(sourceCode.charAt(sourceIndex++)));
            tokens.add(parser.getToken());
        }
    }
    private void parseToken() {
        while (!parser.eatChar(sourceCode.charAt(sourceIndex++)));
        tokens.add(parser.getToken());
    }

    private Token tokenAt(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("went border");
        }
        return tokens.get(index);
    }
    public Token nextToken() {
        if (currIndex == tokens.size())
            parseToken();
        return tokenAt(++currIndex);
    }
    public Token currToken() {
        return tokenAt(currIndex);
    }
    public Token previousToken() {
        return tokenAt(--currIndex);
    }
    public boolean hasNext() {
        return currIndex < tokens.size() - 1;
    }
}
