package uc.seng301.wordleapp.assignment5.guesser;

import uc.seng301.wordleapp.assignment5.game.Wordle;

/**
 * Basic Guess implementation
 */
public class GuessImpl extends Guess {

    /**
     * Default constructor
     * 
     * @param proposition a proposition for the wordle (assumed not null and 5 chars
     *                    long)
     * @param wordle      the wordle to guess (assumed not null)
     */
    public GuessImpl(String proposition, Wordle wordle) {
        super(proposition, wordle);
    }

    @Override
    public boolean isCorrect() {
        return wordle.check(proposition);
    }

    @Override
    public String getProposition() {
        return proposition;
    }
}
