package gradle.cucumber;

import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import uc.seng301.wordleapp.assignment5.game.Wordle;
import uc.seng301.wordleapp.assignment5.guesser.Guess;
import uc.seng301.wordleapp.assignment5.guesser.ManualGuesser;

public class ManualGuessingFeature {
    private ManualGuesser manualGuesser;
    private Wordle wordle;
    private Scanner scanner;
    private Guess guess;
    private PrintStream printStream;
    private ByteArrayOutputStream outputStream;
    private Queue<String> mockInputs;

    @Before
    public void setup() {
        wordle = new Wordle("crane");
        scanner = Mockito.mock(Scanner.class);
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        mockInputs = new LinkedList<>();
        // The following custom mock answer can be used to add mocked strings to the scanner as it is running
        // Note that it acts as a queue and follows FIFO rules
        // You may wish to use this (and the helper function below) for your implementation of U5
        Answer<String> customAnswer = new Answer() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return mockInputs.remove();
            }
        };
        when(scanner.nextLine()).thenAnswer(customAnswer);

    }

    /**
     * A simple helper function for adding any number of string inputs to the mocked scanner
     * The underlying structure is a Queue so follows FIFO rules
     * @param mockedInputs any number of string parameters to add in order
     */
    private void addScannerMocking(String... mockedInputs) {
        mockInputs.addAll(Arrays.asList(mockedInputs));
    }

    @Given("I am playing with the manual guesser")
    public void i_am_playing_with_the_manual_guesser() {
        manualGuesser = new ManualGuesser(wordle, scanner, printStream);
    }

    @When("I make a guess")
    public void i_make_a_guess() throws IOException {
        addScannerMocking("crane");
        guess = manualGuesser.getGuess();
    }

    @Then("The guess count is updated correctly")
    public void the_guess_count_is_updated_correctly() {
        Assertions.assertEquals(1, manualGuesser.getNumGuesses());
    }

    @Then("The Guess is added to the stack")
    public void the_guess_is_added_to_the_stack() {
        Assertions.assertEquals(guess, manualGuesser.getGuesses().peek());
    }

    @When("I make a guess {string}")
    public void i_make_a_guess(String word) {
        addScannerMocking(word, "!q");
        guess = manualGuesser.getGuess();
    }

    @Then("I am not allowed to make the guess")
    public void i_am_not_allowed_to_make_the_guess() {
        printStream.flush();
        Assertions.assertTrue(outputStream.toString().contains("Guesses must be 5 letter words"));

    }

    @Then("The guess count is not changed")
    public void the_guess_count_is_not_changed() {
        Assertions.assertEquals(0, manualGuesser.getNumGuesses());
    }

    @Then("The Guess is not added to the stack")
    public void the_guess_is_not_added_to_the_stack() {
        Assertions.assertEquals(0, manualGuesser.getGuesses().size());
    }

}
