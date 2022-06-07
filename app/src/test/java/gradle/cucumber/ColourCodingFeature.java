package gradle.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import uc.seng301.wordleapp.assignment5.game.ColourCoder;
import uc.seng301.wordleapp.assignment5.game.Wordle;

public class ColourCodingFeature {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GRAY = "\u001B[90m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private ColourCoder colourCoder;
    private Wordle wordle;
    private String word;
    private String colourCodedString;

    private String getColouredStringOfOne(String string, String colour){
        return colour+string+ANSI_RESET;
    }

    @Given("I am guessing the word {string}")
    public void i_am_guessing_the_word(String word) {
        wordle = new Wordle(word);
        this.word = word;
        colourCoder = new ColourCoder(wordle);
    }

    @Then("All letters are green")
    public void all_letters_are_green() {
        String expected = getColouredStringOfOne("c", ANSI_GREEN) +
                getColouredStringOfOne("r", ANSI_GREEN) +
                getColouredStringOfOne("a", ANSI_GREEN) +
                getColouredStringOfOne("n", ANSI_GREEN) +
                getColouredStringOfOne("e", ANSI_GREEN);
        Assertions.assertEquals(expected, colourCodedString);
    }

    @When("I make the guess {string}")
    public void i_make_the_guess(String word) {
        colourCodedString = colourCoder.getColourCodedString(word);
    }

    @Then("The appropriate letters are colour coded green {string}, yellow {string} and {string}, and gray {string} and {string}")
    public void the_appropriate_letters_are_colour_coded_green_yellow_and_and_gray_and(String s1, String s2, String s3, String s4, String s5) {
        String expected = getColouredStringOfOne(s1, ANSI_GREEN) +
                getColouredStringOfOne(s2, ANSI_YELLOW) +
                getColouredStringOfOne(s4, ANSI_GRAY) +
                getColouredStringOfOne(s3, ANSI_YELLOW) +
                getColouredStringOfOne(s5, ANSI_GRAY);
        Assertions.assertEquals(expected, colourCodedString);
    }
    @Then("The appropriate letters are colour coded green {string}, yellow {string}, gray {string}, yellow {string}, and gray {string}")
    public void the_appropriate_letters_are_colour_coded_green_yellow_gray_yellow_and_gray(String s1, String s2, String s3, String s4, String s5) {
        String expected = getColouredStringOfOne(s1, ANSI_GREEN) +
                getColouredStringOfOne(s2, ANSI_YELLOW) +
                getColouredStringOfOne(s3, ANSI_GRAY) +
                getColouredStringOfOne(s4, ANSI_YELLOW) +
                getColouredStringOfOne(s5, ANSI_GRAY);
        Assertions.assertEquals(expected, colourCodedString);
    }

}
