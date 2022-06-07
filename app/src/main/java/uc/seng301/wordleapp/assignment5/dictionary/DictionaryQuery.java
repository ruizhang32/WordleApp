package uc.seng301.wordleapp.assignment5.dictionary;

/**
 * This class exposes an interface for getting possible words from a '.'
 * replaced string. e.g., 'cr.n.' or 'spl.ts'
 * 
 * @see {@link DictionaryResponse}
 */
public interface DictionaryQuery {
  /**
   * Get all possible words that match the query
   * All words can be fetched by supplying '.....'
   * 
   * @Preconditions The guess string is 5 character long with no spaces
   *                and properly formatted with '.'s replacing missing characters
   * @Postconditions None
   *
   * @param query '.' replaced string of length 5 e.g., 'cr.n.'
   * @return A {@link DictionaryResponse} object containing the matching words
   */
  DictionaryResponse guessWord(String query);
}
