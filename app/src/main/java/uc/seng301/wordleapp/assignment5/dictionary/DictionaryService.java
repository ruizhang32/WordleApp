package uc.seng301.wordleapp.assignment5.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * HTTP client to invoke the remote wordle solver
 */
public class DictionaryService implements DictionaryQuery {
    private static final Logger LOGGER = LogManager.getLogger(DictionaryService.class);
    private static final String SOLVER_URL = "https://seng301.csse.canterbury.ac.nz/solver/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DictionaryResponse guessWord(String query) {
        if (query.equals("")) {
            query = ".....";
        }
        LOGGER.info("Requesting words from api with input: " + query);
        DictionaryResponse dictionaryResponse = null;
        try {
            URL url = new URL(SOLVER_URL + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            LOGGER.info("Api responded with status code '{}'", responseCode);

            if (responseCode == 200) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder stringResult = new StringBuilder();
                while (scanner.hasNext()) {
                    stringResult.append(scanner.nextLine());
                }
                scanner.close();
                dictionaryResponse = objectMapper.readValue(stringResult.toString(), DictionaryResponse.class);
            } else {
                LOGGER.error("unable to process request to solver, response code is '{}'", responseCode);
                dictionaryResponse = offlineWords();
            }
        } catch (IOException e) {
            LOGGER.error("Error parsing api response", e);
            dictionaryResponse = offlineWords();
        }
        return dictionaryResponse;
    }

    /**
     * Loads words from text file if there is an issue with the api (e.g. no
     * internet connection or api down)
     * 
     * @Preconditions if there is an error loading the words from file (fallback),
     *                the program will stop
     *
     * @return DictionaryResponse object with words manually set to those loaded
     *         from file
     */
    public DictionaryResponse offlineWords() {
        LOGGER.info("Loading offline words");
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(DictionaryService.class.getClassLoader().getResourceAsStream("words.txt"))))) {
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }
            DictionaryResponse offlineDictionaryResponse = new DictionaryResponse();
            offlineDictionaryResponse.setWords(words);
            return offlineDictionaryResponse;
        } catch (IOException e) {
            LOGGER.fatal("Could not load offline words. Application will exit", e);
            System.exit(1);
        }
        return null;
    }
}
