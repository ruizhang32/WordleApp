package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryQuery;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GuessHintFeature {
    private DictionaryQuery mockDictionaryQuery;
    private String known;
    private List<String> words;
    private final List<String> allWords = new ArrayList<>(Arrays.asList("which", "there", "their", "about", "would", "these", "other", "words", "could", "write"));

    @Before
    public void setup() {
        mockDictionaryQuery = Mockito.mock(DictionaryQuery.class);
        words = null;
    }

    @Given("I know the letters {string} of the word")
    public void i_know_the_letters_of_the_word(String known) {
        Pattern pattern = Pattern.compile(known);
        DictionaryResponse dictionaryResponse = new DictionaryResponse();
        dictionaryResponse.setWords(allWords.stream().filter(pattern.asPredicate()).collect(Collectors.toList()));
        Mockito.when(mockDictionaryQuery.guessWord(known)).thenReturn(dictionaryResponse);
        this.known = known;
    }

    @When("I get a list of possible words")
    public void i_get_a_list_of_possible_words() {
        words = mockDictionaryQuery.guessWord(known).getWords();
        Assertions.assertNotNull(words);
    }

    @Then("The available options are returned with only those that match")
    public void the_available_options_are_returned_with_only_those_that_match() {
        words = mockDictionaryQuery.guessWord(known).getWords();
        Assertions.assertNotNull(words);
        Assertions.assertEquals(words, allWords.stream().filter(s -> s.matches(known)).collect(Collectors.toList()));
    }

    @Then("No words are returned")
    public void no_words_are_returned() {
        words = mockDictionaryQuery.guessWord(known).getWords();
        Assertions.assertNotNull(words);
        Assertions.assertEquals(words.size(), 0);
    }


    @Then("All available words are returned")
    public void all_available_words_are_returned() {
        Assertions.assertEquals(words, allWords);
    }

}
