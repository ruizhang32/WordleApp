package uc.seng301.wordleapp.assignment5.guesser;

import uc.seng301.wordleapp.assignment5.game.Wordle;

/**
 * Defines the common operations of a guesser, for both simulated and real
 * player
 */
public abstract class Guesser {
    protected Wordle wordle;
    protected int numGuesses = 0;
    Guesser(Wordle wordle) {
        this.wordle = wordle;
    }

    /**
     * Gets the next guess from the guesser
     * 
     * @return a new Guess object from the guesser, as its next guess
     */
    public abstract Guess getGuess();

    /**
     * Gets the total number of guesses made over the life of the guesser
     *
     * @return total number of guesses made
     */
    public int getNumGuesses() {
        return numGuesses;
    }
}
