package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * @author Yhtyyar created on 20.09.2020
 */
public class SpoonInterpretator implements IInterpretator {
    private final InputStream input;
    private final PrintStream out;

    SpoonInterpretator(InputStream input, PrintStream out) {
        this.input = input;
        this.out = out;
    }

    @Override
    public void evaluate(String sourceCode) {
        try {

            new Interpretate(new Tokenizer(sourceCode), input, out).evaluate();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private static class Interpretate {
        private static final int MEMORY_SIZE = 30000;
        private final InputStream input;
        private final PrintStream out;

        private final Tokenizer tokenizer;

        private char memory[];
        private int currCell;
        private int bracketsCount;

        Interpretate(Tokenizer tokenizer, InputStream input, PrintStream out) {
            memory = new char[MEMORY_SIZE];
            this.input = input;
            this.out = out;
            this.tokenizer = tokenizer;
        }
        private void evaluate() throws IOException {
           while (tokenizer.hasNext()){
                switch (tokenizer.nextToken()) {
                    case NEXT_CELL: moveToNextCell(); break;
                    case PREV_CELL: moveToPreviosCell(); break;
                    case INC: incrementCurrCell(); break;
                    case DEC: decrementCurrCell(); break;
                    case PRINT: printCurrCell(); break;
                    case READ: readChar(); break;
                    case OPEN_BRACKET:
                        bracketsCount++;
                        if (memory[currCell] == (char)0){
                            moveUntilCloseBracket();
                        }
                        break;
                    case CLOSE_BRACKET:
                        bracketsCount--;
                        if (memory[currCell] != (char)0){
                            tokenizer.previousToken();
                            moveToOpenBracket();
                        }
                        break;
                    case PRINT_ALL :
                        printAll();
                        break;
                    case TERMINATE:
                        terminate();
                }
            }
        }

        private void moveToNextCell() {
            currCell++;
        }
        private void printAll() {
            for (int i = 0; i < MEMORY_SIZE; ++i) {
                out.print(memory[i]);
            }
        }
        private void terminate() {
            System.exit(0);
        }
        private void moveToPreviosCell() {
            currCell--;
        }

        private void incrementCurrCell() {
            memory[currCell]++;
        }

        private void decrementCurrCell() {
            memory[currCell]--;
        }

        private void printCurrCell() {
            out.print(memory[currCell]);
        }

        private void readChar() throws IOException {
            memory[currCell] = (char)input.read();
        }

        private void checkForBracket() {
            switch (tokenizer.currToken()) {
                case OPEN_BRACKET: bracketsCount++; break;
                case CLOSE_BRACKET: bracketsCount--; break;
                default: break;
            }
        }
        private void moveUntilCloseBracket() {
            int neededBrackets = bracketsCount - 1;
            while (bracketsCount != neededBrackets){
                tokenizer.nextToken();
                checkForBracket();
            }
        }

        private void moveToOpenBracket() {
            int neededBrackets = bracketsCount + 1;
            while (bracketsCount != neededBrackets) {
                tokenizer.previousToken();
                checkForBracket();
            }
        }

    }
}
