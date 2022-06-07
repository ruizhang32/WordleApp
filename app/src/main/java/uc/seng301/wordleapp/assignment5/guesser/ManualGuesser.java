package uc.seng301.wordleapp.assignment5.guesser;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryQuery;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryResponse;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryService;
import uc.seng301.wordleapp.assignment5.game.ColourCoder;
import uc.seng301.wordleapp.assignment5.game.Wordle;

/**
 * Provides guessing functionality for the command line
 */
public class ManualGuesser extends Guesser {
    private static final Logger LOGGER = LogManager.getLogger(ManualGuesser.class);

    private final Scanner cli;
    private final PrintStream output;
    private final DictionaryQuery dictionaryQuery;
    private final ColourCoder colourCoder;
    private String currentGuess;
    private Stack<Guess> guesses = new Stack<>();
    private CareTaker careTaker = new CareTaker();

    public ManualGuesser(Wordle wordle, Scanner cli, PrintStream output) {
        super(wordle);
        this.cli = cli;
        this.output = output;
        this.dictionaryQuery = new DictionaryService();
        this.colourCoder = new ColourCoder(wordle);
    }

    /**
     * Asks for and reads the next guess from the command line, also allowing for
     * hints
     * Returns null if the player exits the game
     *
     * @return Guess made with appropriate input
     */
    public Guess getGuess() {
        LOGGER.info("Getting guess from command line with manual guesser");
        String input = "";
        boolean isPlaying = true;
        while (isPlaying) {
            output.println("Please enter your guess:");
            input = cli.nextLine();
            if (input.length() > 4 && input.substring(0, 5).equalsIgnoreCase("help ")) {
                String query = input.split(" ")[1];
                DictionaryResponse dictionaryResponse = dictionaryQuery.guessWord(query);
                output.println(dictionaryResponse.getTopFiveResults());
            } else if (input.equals("!q")) {
                return null;
            } else if(input.equals("undo")){
                // undo the guess
                if (numGuesses > 0){
                    // decrease the size of guesses
                    numGuesses--;
                    if(numGuesses == 0){
                        guesses = new Stack<>();
                    }else{ // get the second last memento
                        getStateFromMemento(careTaker.getMementoByNum(numGuesses));
                    }
                }else{
                    output.println("Nothing to undo");
                }

            } else if(input.equals("redo")){
                // get the last memento from careTaker
                if(numGuesses < careTaker.getMementoListSize()){
                    numGuesses++;
                    getStateFromMemento(careTaker.getMementoByNum(numGuesses));
                }else{ // not allow redo if no undo
                    output.println("Nothing to redo");
                }

            } else if (input.length() != 5) {
                output.println("Guesses must be 5 letter words");
            } else {
                numGuesses++;
                isPlaying = false;
            }
        }
        currentGuess = input;
        Guess guess = new GuessImpl(currentGuess, wordle);
        guesses.push(guess);

        careTaker.add(saveStateToMemento(), numGuesses-1);

        for (Guess g : guesses) {
            output.println(colourCoder.getColourCodedString(g.getProposition()));
        }
        return guess;
    }

    /**
     * Gets the stack of guesses with more recent guesses on top
     *
     * @return stack of guesses
     */
    public Stack<Guess> getGuesses() {
        return guesses;
    }

    public Memento saveStateToMemento(){
        // create a snapshot for each update
        return new Memento(guesses,numGuesses);
    }

    public void getStateFromMemento(Memento memento){
        guesses = memento.getGuessStack();
    }
}
