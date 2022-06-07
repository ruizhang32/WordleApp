package uc.seng301.wordleapp.assignment5.game;

/**
 * Provides Colour coding functionality for strings, against the given Wordle.
 * Colour coding is done using ANSI colour codes
 */
public class ColourCoder {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GRAY = "\u001B[90m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private final Wordle wordle;

    /**
     * Default constructor
     *
     * @param wordle the wordle to colour against
     */
    public ColourCoder(Wordle wordle) {
        this.wordle = wordle;
    }

    /**
     * Creates and returns a colour coded string following:
     * <ul>
     * <li>green for correct character in correct place</li>
     * <li>yellow for correct character in wrong place</li>
     * <li>gray for incorrect character</li>
     * </ul>
     * 
     * @param query string representation of guess to colour code
     * @return colour coded string
     */
    public String getColourCodedString(String query) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (wordle.getWord().charAt(i) == query.charAt(i)) {
                stringBuilder.append(ANSI_GREEN).append(query.charAt(i)).append(ANSI_RESET);
            } else {
                boolean found = false;
                for (char c : wordle.getWord().toCharArray()) {
                    if (c == query.charAt(i)) {
                        stringBuilder.append(ANSI_YELLOW).append(query.charAt(i)).append(ANSI_RESET);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    stringBuilder.append(ANSI_GRAY).append(query.charAt(i)).append(ANSI_RESET);
                }
            }
        }
        return stringBuilder.toString();
    }
}
