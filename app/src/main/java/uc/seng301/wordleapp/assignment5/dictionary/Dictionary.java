package uc.seng301.wordleapp.assignment5.dictionary;

/**
 * This class implements a dictionary query that uses an external service to
 * query hints for guesses.
 */
public class Dictionary implements DictionaryQuery {
    private final DictionaryService dictionaryService;

    /**
     * Constructor, needs a service to retrieve guess hints from.
     */
    public Dictionary() {
        this.dictionaryService = new DictionaryService();
    }

    @Override
    public DictionaryResponse guessWord(String query) {
        return dictionaryService.guessWord(query);
    }
}
