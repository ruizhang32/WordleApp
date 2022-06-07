package uc.seng301.wordleapp.assignment5.guesser;

/**
 * Class which holds knowledge of one or many guesses made against a wordle word
 */
public class Knowledge {
    private char[] knownInPlace = new char[5];
    private char[] knownOutOfPlace = new char[5];
    private char[] knownNotInWord = new char[26];

    /**
     * Updates the current knowledge with the new guess proposition.
     *
     * @param guess A guess with a 5-letter proposition set that will be used to
     *              update the knowledge
     * @param word  the 5-letter word the guess is to be compared against
     */
    public void update(Guess guess, String word) {
        for (int i = 0; i < 5; i++) {
            if (guess.getProposition().charAt(i) == word.charAt(i)) {
                addToKnownInPlace(guess.getProposition().charAt(i), i);
            } else if (word.contains(String.valueOf(guess.getProposition().charAt(i)))) {
                addToKnownOutOfPlace(guess.getProposition().charAt(i));
            } else {
                addToKnownNotInWord(guess.getProposition().charAt(i));
            }
        }
    }

    /**
     * Adds a character to known out of place array
     *
     * @param c character that has been determined to be in the wordle word, but not
     *          in the correct spot
     */
    private void addToKnownOutOfPlace(char c) {
        for (char x : knownOutOfPlace) {
            if (x == c) {
                return;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (knownOutOfPlace[i] == '\u0000') {
                knownOutOfPlace[i] = c;
                return;
            }
        }
    }

    /**
     * Adds a character that is known at an exact index of the wordle
     *
     * @param c     character that has been found at index
     * @param index index character is correct guessed at
     */
    private void addToKnownInPlace(char c, int index) {
        knownInPlace[index] = c;
    }

    /**
     * Adds a character that is known to not be in the word
     *
     * @param c character that is not in the word
     */
    private void addToKnownNotInWord(char c) {
        for (int i = 0; i < knownNotInWord.length; i++) {
            if (knownNotInWord[i] == '\u0000') {
                knownNotInWord[i] = c;
                return;
            }
        }
    }

    /**
     * Get the chars correctly placed in word to guess
     * 
     * @return a char array with the correctly guessed letters at the right place
     */
    public char[] getKnownInPlace() {
        return knownInPlace;
    }

    /**
     * Get the chars not correctly placed in word to guess
     * 
     * @return a char array with letters in the word found in the wrong position
     */
    public char[] getKnownOutOfPlace() {
        return knownOutOfPlace;
    }

    /**
     * Get the chars not in word to guess
     * 
     * @return a char array containing guessed letters that do not exist in the word
     */
    public char[] getKnownNotInWord() {
        return knownNotInWord;
    }

}
