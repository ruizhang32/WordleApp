package uc.seng301.wordleapp.assignment5.guesser;

import uc.seng301.wordleapp.assignment5.game.Wordle;

/**
 * Abstract class dealing with guesses for a wordle.
 */
public abstract class Guess {
    protected String proposition;
    protected Wordle wordle;

    /**
     * Creates a new guess object
     *
     * @param proposition Proposition is the string representation of the users
     *                    guess, this string must not be null and must be length 5
     * @param wordle      Worlde object is properly created and must have word
     *                    parameter set
     */
    protected Guess(String proposition, Wordle wordle) {
        this.proposition = proposition;
        this.wordle = wordle;
    }

    /**
     * Gets the string representation of the guess
     * 
     * @Preconditions this.proposition is a 5 char long word
     * @Postconditions None
     *
     * @return guess string with length 5, cannot be null,
     */
    public abstract String getProposition();

    /**
     * Guess object checks itself for correctness
     * 
     * @Preconditions both {this.proposition} and {this.wordle} are non-null
     * @Postconditions None
     *
     * @return true if the proposition and wordle word match exactly, else
     *         false
     */
    public abstract boolean isCorrect();

    @Override
    public String toString() {
        return "{" +
                " proposition='" + getProposition() + "'" +
                "}";
    }
}
