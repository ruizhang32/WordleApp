package uc.seng301.wordleapp.assignment5.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Simple javax data class for game records
 */
@Entity
@Table(name = "gamerecord")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_gamerecord")
    private Long gameRecordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    private Date timestamp;
    private String word;
    private int numGuesses;

    public GameRecord() {
        timestamp = new Date();
    }

    public Long getGameRecordId() {
        return gameRecordId;
    }

    public void setGameRecordId(Long gameRecordId) {
        this.gameRecordId = gameRecordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public void setNumGuesses(int numGuesses) {
        if (numGuesses <= 0) {
            throw new IllegalArgumentException("Number of guesses cannot be 0 or fewer");
        }
        this.numGuesses = numGuesses;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "gameRecordId=" + gameRecordId +
                ", user=" + user.getUserName() +
                ", word='" + word + '\'' +
                ", numGuesses=" + numGuesses +
                ", timestamp=" + timestamp +
                '}';
    }

}
