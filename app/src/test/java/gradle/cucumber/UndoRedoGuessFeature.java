package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import uc.seng301.wordleapp.assignment5.game.Wordle;
import uc.seng301.wordleapp.assignment5.guesser.ManualGuesser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static org.mockito.Mockito.when;

public class UndoRedoGuessFeature {
    private ManualGuesser manualGuesser;
    private Wordle wordle;
    private Scanner scanner;
    private int sizeBeforeUndo;
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

    @Given("I am playing as a manual guesser")
    public void i_am_playing_as_a_manual_guesser() {
        manualGuesser = new ManualGuesser(wordle, scanner, printStream);
    }

    @When("I make a guess with word input {string}")
    public void i_make_a_guess_with_word_input(String word) {
        addScannerMocking(word);
        manualGuesser.getGuess();
        sizeBeforeUndo = manualGuesser.getNumGuesses();
        Assertions.assertEquals(manualGuesser.getGuesses().get(0).getProposition(), word);
    }

    @And("I make a guess with undo or redo input {string}")
    public void I_make_a_guess_with_undo_or_redo_input(String string) {
        addScannerMocking(string, "!q");
        manualGuesser.getGuess();
    }

    @Then("{string} is removed from the stack of guesses")
    public void is_removed_from_the_stack_of_guesses(String word) {
        Assertions.assertFalse(manualGuesser.getGuesses().stream().anyMatch(g -> g.getProposition().equals(word)));
    }

    @Then("The size of guess stack decreases by one")
    public void the_size_of_guess_stack_decreases_by_one() {
        Assertions.assertEquals(sizeBeforeUndo - 1, manualGuesser.getNumGuesses());
    }

    @Then("I am not allowed to redo the guess")
    public void i_am_not_allowed_to_redo_the_guess() {
        printStream.flush();
        Assertions.assertTrue(outputStream.toString().contains("Nothing to redo"));
    }

    @Then("{string} is re-added to the stack of guesses")
    public void is_re_added_to_the_stack_of_guesses(String word) {
        Assertions.assertTrue(manualGuesser.getGuesses().stream().anyMatch(g -> g.getProposition().equals(word)));
    }

    @When("I make guesses {string} {string} {string} in order")
    public void i_make_guesses_in_order(String string1, String string2, String string3) {
        addScannerMocking(string1, string2, string3);
        manualGuesser.getGuess();
        manualGuesser.getGuess();
        manualGuesser.getGuess();
        Assertions.assertEquals(string1, manualGuesser.getGuesses().get(0).getProposition());
        Assertions.assertEquals(string2, manualGuesser.getGuesses().get(1).getProposition());
        Assertions.assertEquals(string3, manualGuesser.getGuesses().get(2).getProposition());
        Assertions.assertEquals(3, manualGuesser.getNumGuesses());
    }

    @Then("{string} {string} exist orderly in the stack of guesses")
    public void exist_orderly_in_the_stack_of_guesses(String string, String string2) {
        String actualFirstInStack = manualGuesser.getGuesses().get(0).getProposition();
        String actualSecondWordInStack = manualGuesser.getGuesses().get(1).getProposition();
        Assertions.assertEquals(string, actualFirstInStack);
        Assertions.assertEquals(string2, actualSecondWordInStack);
    }

    @Then("{string} is the element in the stack of guesses")
    public void is_the_element_in_the_stack_of_guesses(String string) {
        String actualWordInStack = manualGuesser.getGuesses().get(0).getProposition();
        Assertions.assertEquals(string, actualWordInStack);
    }

    @Then("The stack of guesses is empty")
    public void the_stack_of_guesses_is_empty() {
        Assertions.assertEquals(0, manualGuesser.getGuesses().size());
    }

    @Then("{string} {string} {string} exist orderly in the stack of guesses")
    public void exist_orderly_in_the_stack_of_guesses(String string, String string2, String string3) {
        String actualFirstWordInStack = manualGuesser.getGuesses().get(0).getProposition();
        String actualSecondWordInStack = manualGuesser.getGuesses().get(1).getProposition();
        String actualThirdWordInStack = manualGuesser.getGuesses().get(2).getProposition();
        Assertions.assertEquals(string, actualFirstWordInStack);
        Assertions.assertEquals(string2, actualSecondWordInStack);
        Assertions.assertEquals(string3, actualThirdWordInStack);
    }

}

