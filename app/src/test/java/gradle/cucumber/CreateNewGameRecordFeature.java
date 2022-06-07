package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import uc.seng301.wordleapp.assignment5.accessor.GameRecordAccessor;
import uc.seng301.wordleapp.assignment5.accessor.UserAccessor;
import uc.seng301.wordleapp.assignment5.model.GameRecord;
import uc.seng301.wordleapp.assignment5.model.User;

import java.util.Date;

public class CreateNewGameRecordFeature {

    private GameRecordAccessor gameRecordAccessor;
    private UserAccessor userAccessor;
    private User player;
    private GameRecord firstGameRecord;
    private GameRecord secondGameRecord;
    private Date timestampBefore;
    private Exception storedException;
    private String gameRecordWord;
    private int gameRecordNumGuesses;

    @Before
    public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        userAccessor = new UserAccessor(sessionFactory);
        gameRecordAccessor = new GameRecordAccessor(sessionFactory);
    }

    @Given("I have a player {string}")
    public void i_have_a_player(String name) {
        player = new User();
        player.setUserName(name);
        Long playerId = userAccessor.persistUser(player);
        Assertions.assertNotNull(player);
        Assertions.assertNotNull(playerId);
        Assertions.assertSame(player.getUserName(), name);
    }

    @When("I create a game record with the player, word {string}, and {int} guesses")
    public void i_create_a_game_record_with_the_player_word_and_guesses(String word, Integer numGuesses) {
        try {
            gameRecordWord = word;
            gameRecordNumGuesses = numGuesses;
            timestampBefore = new Date();
            firstGameRecord = new GameRecord();
            firstGameRecord.setUser(player);
            firstGameRecord.setWord(word);
            firstGameRecord.setNumGuesses(numGuesses);
            Long firstGameRecordId = gameRecordAccessor.persistGameRecord(firstGameRecord);
            Assertions.assertNotNull(firstGameRecord);
            Assertions.assertNotNull(firstGameRecordId);
        } catch (Exception e) {
            storedException = e;
        }
    }

    @Then("The game record is created with the correct user, word, number of guesses, and the current timestamp")
    public void the_game_record_is_created_with_the_correct_user_word_number_of_guesses_and_the_current_timestamp()
    {
        Assertions.assertTrue(!firstGameRecord.getTimestamp().before(timestampBefore) &&
                !firstGameRecord.getTimestamp().after(new Date()));
        Assertions.assertEquals(firstGameRecord.getUser(), player);
        Assertions.assertEquals(firstGameRecord.getWord(), gameRecordWord);
        Assertions.assertEquals(firstGameRecord.getNumGuesses(), gameRecordNumGuesses);
    }
    @Given("I have already created a game record with the player, word {string}, and {int} guesses")
    public void i_have_already_created_a_game_record_with_the_player_word_and_guesses(String word, Integer numGuesses) {
        gameRecordWord = word;
        gameRecordNumGuesses = numGuesses;
        timestampBefore = new Date();
        secondGameRecord = new GameRecord();
        secondGameRecord.setUser(player);
        secondGameRecord.setWord(word);
        secondGameRecord.setNumGuesses(numGuesses);
        Long secondGameRecordId = gameRecordAccessor.persistGameRecord(secondGameRecord);
        Assertions.assertNotNull(secondGameRecord);
        Assertions.assertNotNull(secondGameRecordId);
    }

    @Then("there are now two game records with identical values except timestamp")
    public void there_are_now_two_game_records_with_identical_values_except_timestamp() {
        Assertions.assertNotEquals(firstGameRecord, secondGameRecord);
        Assertions.assertNotEquals(firstGameRecord.getTimestamp().getTime(), secondGameRecord.getTimestamp().getTime());
        Assertions.assertEquals(firstGameRecord.getUser(), secondGameRecord.getUser());
        Assertions.assertEquals(firstGameRecord.getWord(), secondGameRecord.getWord());
        Assertions.assertEquals(firstGameRecord.getNumGuesses(), secondGameRecord.getNumGuesses());
    }

    @Then("I expect an exception that disallows me to create it")
    public void i_expect_an_exception_that_disallows_me_to_create_it() {
        Assertions.assertNotNull(storedException);
        Assertions.assertEquals(IllegalArgumentException.class, storedException.getClass());
    }

}