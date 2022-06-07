package uc.seng301.wordleapp.assignment5;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import uc.seng301.wordleapp.assignment5.accessor.GameRecordAccessor;
import uc.seng301.wordleapp.assignment5.accessor.UserAccessor;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryQuery;
import uc.seng301.wordleapp.assignment5.dictionary.DictionaryService;
import uc.seng301.wordleapp.assignment5.game.Game;
import uc.seng301.wordleapp.assignment5.game.Wordle;
import uc.seng301.wordleapp.assignment5.guesser.Guesser;
import uc.seng301.wordleapp.assignment5.guesser.ManualGuesser;
import uc.seng301.wordleapp.assignment5.guesser.RandomGuesser;
import uc.seng301.wordleapp.assignment5.guesser.SmartGuesser;
import uc.seng301.wordleapp.assignment5.model.GameRecord;
import uc.seng301.wordleapp.assignment5.model.User;

public class App {
    private static final Logger LOGGER = LogManager.getLogger(App.class);
    private final GameRecordAccessor gameRecordAccessor;
    private final UserAccessor userAccessor;
    private final Scanner cli;
    private final PrintStream output;
    private final Random random;

    public App(InputStream inputStream, PrintStream outputStream) {
        LOGGER.info("Starting application...");
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        userAccessor = new UserAccessor(sessionFactory);
        gameRecordAccessor = new GameRecordAccessor(sessionFactory);
        cli = new Scanner(inputStream);
        this.output = outputStream;
        random = new Random(301L);
        runCli();
    }

    public static void main(String[] args) {
        new App(System.in, System.out);
    }

    /**
     * Simple welcome message for command line operation
     */
    private void welcome() {
        output.println("""
                ######################################################
                             Welcome to Wordle Clone App
                ######################################################""");
    }

    /**
     * prints help page for commands to console
     */
    private void printHelp() {
        output.println("Available Commands:");
        output.println("\"create <name>\" to create a new player");
        output.println("\"play <name> <guesser>\" to play with the provided player and guesser, where guesser is " +
                "one of 'manual', 'random', 'smart'");
        output.println("\"highscores\" to view up to the top 10 high scores");
        output.println("\"exit\" or \"!q\" to quit");
    }

    /**
     * Runs the command line interface and reads inputs until the user quits or
     * terminates the program
     */
    public void runCli() {
        welcome();
        printHelp();
        while (true) {
            String input = cli.nextLine();
            LOGGER.info("User input: {}", input);
            switch (input.split(" ")[0]) {
                case "create":
                    String[] uInputs = input.split(" ");
                    if (uInputs.length == 1) {
                        output.println("Command incorrect use \"help\" for more information");
                        continue;
                    }
                    User u = new User();
                    u.setUserName(uInputs[1]);
                    Long userId = userAccessor.persistUser(u);
                    LOGGER.info("Input valid, created user {}} id {}}", u.getUserName(), u.getUserId());
                    output.println(String.format("Created user %s id %d", u.getUserName(), userId));
                    break;
                case "play":
                    DictionaryQuery dictionaryQuery = new DictionaryService();
                    List<String> words = dictionaryQuery.guessWord("").getWords();
                    Wordle wordle = new Wordle(words.get(random.nextInt(words.size())));
                    String[] inputs = input.split(" ");
                    if (inputs.length == 1) {
                        output.println("Command incorrect use \"help\" for more information");
                        continue;
                    }
                    User user = userAccessor.getUserByName(inputs[1]);
                    if (user == null) {
                        output.println(String.format("User %s does not exist", inputs[1]));
                        continue;
                    }
                    String guesserName = inputs[2];
                    Guesser guesser = null;
                    switch (guesserName) {
                        case "manual" -> guesser = new ManualGuesser(wordle, cli, output);
                        case "random" -> guesser = new RandomGuesser(wordle);
                        case "smart" -> guesser = new SmartGuesser(wordle);
                    }
                    if (guesser == null) {
                        output.println(String.format("Guesser %s does not exist", inputs[2]));
                        continue;
                    }

                    Game game = new Game(user, guesser, wordle);
                    GameRecord gameRecord = game.playGame();
                    if (gameRecord == null) {
                        continue;
                    }
                    Long gameRecordId = gameRecordAccessor.persistGameRecord(gameRecord);
                    LOGGER.info("Input valid, played/simulated game with user {} and guesser {}", user.getUserName(),
                            guesserName);
                    output.println(gameRecord.toString() + ". Saved record with id: " + gameRecordId);
                    break;
                case "highscores":
                    List<GameRecord> highScores = gameRecordAccessor.getHighscores();
                    if (highScores.isEmpty()) {
                        output.println("No games have been recorded yet");
                    }
                    for (int i = 0; i < highScores.size(); i++) {
                        output.println(String.format("%d: %s", i + 1, highScores.get(i)));
                    }
                    LOGGER.info("Input valid, high scores printed");
                    break;
                case "help":
                    printHelp();
                    LOGGER.info("Input valid, help printed");
                    break;
                case "exit":
                case "!q":
                    LOGGER.info("Input valid, quitting application");
                    System.exit(0);
                    break;

                default:
                    output.println("Invalid command, use \"help\" for more info");
                    LOGGER.info("Input invalid, {}", input);
                    break;
            }
        }
    }
}
