package uc.seng301.wordleapp.assignment5.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Simple javax data class for users. Note that when using h2 in-memory database
 * 'user' table is not allowed due to name bashing
 */
@Entity
@Table(name = "game_user")
public class User {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private List<GameRecord> gameRecords;

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                (gameRecords != null
                        ? ", gameRecords=" + gameRecords.stream().map(GameRecord::toString).toList()
                        : "")
                +
                '}';
    }
}
