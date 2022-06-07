package uc.seng301.wordleapp.assignment5.guesser;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uc.seng301.wordleapp.assignment5.dictionary.Dictionary;
import uc.seng301.wordleapp.assignment5.game.Wordle;

/**
 * Class simulates guessing functionality in a 'smart' way, using the rules of
 * Wordle to understand each guess and make only guesses at each round that
 * could be correct based on the current knowledge.
 */
public class SmartGuesser extends Guesser {
    private final Random random;
    private final Knowledge knowledge;
    private final List<String> words;

    public SmartGuesser(Wordle wordle) {
        super(wordle);
        random = new Random();
        knowledge = new Knowledge();
        words = new Dictionary().guessWord("").getWords();
    }

    @Override
    public Guess getGuess() {
        numGuesses++;
        Iterator<String> itr = words.iterator();
        Guess guess = null;
        while (itr.hasNext()) {
            String word = itr.next();
            int i = 0;
            boolean removed = false;
            // looks through characters in word and removes word if it does not have a
            // character exactly where we know it should be
            for (char c : knowledge.getKnownInPlace()) {
                if (c != '\u0000') {// the '\u0000' character is the default character for char[]
                    if (word.indexOf(c) == -1) {
                        itr.remove();
                        removed = true;
                        break;
                    }
                }
            }
            if(!removed) {
                // looks through characters in word and removes word if it doesn't have a character
                // that we know is somewhere in the word. This is not perfect as it does not check that the
                // character is not in a position already tried
                for(int j = 0; j<5;j++){
                    if (knowledge.getKnownOutOfPlace()[j] != '\u0000') {
                        if (word.indexOf(knowledge.getKnownOutOfPlace()[j]) == -1) {
                            itr.remove();
                            removed = true;
                            break;
                        }
                    }
                }
            }
            if (!removed) {
                // looks through characters in word and removes word if it has a character we know it can't have
                for (char c : knowledge.getKnownNotInWord()) {
                    if (c != '\u0000') {
                        if (word.indexOf(c) != -1) {
                            itr.remove();
                            removed = true;
                            break;
                        }
                    }
                }
            }
            if (!removed) {
                // looks through characters in word and removes word if it doesn't have the
                // expected character (works with duplicate letters)
                for (char c : knowledge.getKnownInPlace()) {
                    if (c != '\u0000') {
                        boolean found = false;
                        for (int index = word.indexOf(c); index >= 0; index = word.indexOf(c, index + 1)) {
                            if (index == i) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            itr.remove();
                            break;
                        }
                    }
                    i++;
                }
            }
        }

        for (char c : knowledge.getKnownInPlace()) {
            if (c == '\u0000') {
                guess = new GuessImpl(words.get(random.nextInt(words.size())), wordle);
                break;
            }
        }

        if(guess == null) { // If there are no blank chars found our known in place array must be the correct word
             guess = new GuessImpl(String.valueOf(knowledge.getKnownInPlace()), wordle);
        }

        knowledge.update(guess, wordle.getWord());
        return guess;
    }
}
