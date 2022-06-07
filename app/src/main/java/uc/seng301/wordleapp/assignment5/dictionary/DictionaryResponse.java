package uc.seng301.wordleapp.assignment5.dictionary;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Simple data class for handy JSON (de)serialization
 */
public class DictionaryResponse {

    @JsonDeserialize
    @JsonProperty("words")
    private List<String> words;

    /**
     * Gets the list of words stored
     * 
     * @Preconditions {this.words} must not be null
     * @Postconditions None
     * 
     * @return list of words
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Gets up to the first 5 words from the list as they are stored, if fewer words
     * exist in the list all will be returned
     * 
     * @Preconditions {this.words} must not be null
     * @Postconditions None
     *
     * @return first 5 words from this response
     */
    public List<String> getTopFiveResults() {
        return words.stream().limit(5).toList();
    }

    /**
     * Sets the list of words, used only for mocking and getting words offline
     * 
     * @Precondition none
     * @Postcondition {this.words} is set to a non-null value
     *
     * @param words the list of 5-letter words to set, must not be null
     */
    public void setWords(List<String> words) {
        this.words = words;
    }
}
