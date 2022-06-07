package uc.seng301.wordleapp.assignment5.guesser;

import uc.seng301.wordleapp.assignment5.dictionary.Dictionary;
import uc.seng301.wordleapp.assignment5.game.Wordle;

import java.util.List;
import java.util.Random;

/**
 * Class simulates guessing functionality by selecting a word
 * at random from the full list of words without removal
 */
public class RandomGuesser extends Guesser {
    private final Random random;
    private final List<String> words;

    public RandomGuesser(Wordle wordle) {
        super(wordle);
        random = new Random();
        words = new Dictionary().guessWord("").getWords();
    }

    @Override
    public Guess getGuess() {
        numGuesses++;
        return new GuessImpl(words.get(random.nextInt(words.size())), wordle);
    }
}
