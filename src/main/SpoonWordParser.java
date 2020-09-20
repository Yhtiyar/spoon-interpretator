package main;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yhtyyar created on 20.09.2020
 */
public class SpoonWordParser {
    private static Map<String, Token> vocabulary = Map.of(
            "1", Token.INC,
            "000", Token.DEC,
            "010", Token.NEXT_CELL,
            "011", Token.PREV_CELL,
            "0011", Token.CLOSE_BRACKET,
            "00100", Token.OPEN_BRACKET,
            "001010", Token.PRINT,
            "0010110", Token.READ,
            "00101110", Token.PRINT_ALL,
            "00101111", Token.TERMINATE
    );
    private final HuffmanTree spoonWordsTree;
    private HuffmanTree.Node currNode;
    private void initializeTree() {
        for (var entry : vocabulary.entrySet()) {
            spoonWordsTree.addWord(entry.getKey(), entry.getValue());
        }
    }
    public SpoonWordParser() {
        spoonWordsTree = new HuffmanTree();
        initializeTree();
        currNode = spoonWordsTree.getRoot();
    }
    public boolean eatChar(char ch) {
        switch (ch) {
            case '0' : currNode = currNode.getLeft(); break;
            case '1' : currNode = currNode.getRight(); break;
            default: throw new IllegalArgumentException("Unknown char:" + ch);
        }
        if (currNode == null)
            throw new IllegalArgumentException("Not recognized word provided");
        return hasTokenParsed();
    }
    public boolean hasTokenParsed() {
        return currNode.getToken() != null;
    }
    public Token getToken() {
        Token token = currNode.getToken();
        currNode = spoonWordsTree.getRoot();
        return token;
    }

}
