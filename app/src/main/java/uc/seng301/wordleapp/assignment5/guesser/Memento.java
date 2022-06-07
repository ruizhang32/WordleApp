package uc.seng301.wordleapp.assignment5.guesser;

import java.util.Stack;

public class Memento {
    private Stack<Guess> guessStack;
    private int numGuesses;

    public Memento(Stack<Guess> guessStack, int numGuesses) {
        this.guessStack = (Stack<Guess>) guessStack.clone();
        this.numGuesses = numGuesses;
    }

    public Stack<Guess> getGuessStack() {
        return guessStack;
    }

    public int getNumGuesses() {
        return numGuesses;
    }
}
