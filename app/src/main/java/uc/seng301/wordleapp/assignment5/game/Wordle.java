package uc.seng301.wordleapp.assignment5.game;

/**
 * Represents domain of a Wordle game, allows setting one 5-letter word and checking this
 * to see if it is correct
 */
public class Wordle {
    private final String word;

    /**
     * Default constructor
     *
     * @param word set the word, cannot be null
     * @throws IllegalArgumentException if given word is null or empty
     */
    public Wordle(String word) {
        if (null == word || word.isBlank()) {
            throw new IllegalArgumentException("A word for a wordle cannot be null or empty");
        }
        this.word = word;
    }

    /**
     * Checks the given proposition to see if it is the correct word
     *
     * @param proposition proposition/guess made by user
     * @return true if guess is correct, false if incorrect
     */
    public boolean check(String proposition) {
        return word.equals(proposition);
    }

    /**
     * Gets the given word used in the wordle game
     *
     * @return wordle word
     */
    public String getWord() {
        return word;
    }
}
