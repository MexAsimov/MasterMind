package to.projekt.to2021projekt.models;

import javax.persistence.*;

@Entity
@Table(name="GAME")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id", nullable = false)
    private long gameID;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private User playerID;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="difficulty_id")
    private Difficulty difficultyID;

    private int result;

    private int gameTime;

    public Game() {
    }

    public Game(User playerID, Difficulty difficultyID, int result, int gameTime) {
        this.playerID = playerID;
        this.difficultyID = difficultyID;
        this.result = result;
        this.gameTime = gameTime;
    }

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public User getPlayerID() {
        return playerID;
    }

    public void setPlayerID(User playerID) {
        this.playerID = playerID;
    }

    public Difficulty getDifficultyID() {
        return difficultyID;
    }

    public void setDifficultyID(Difficulty difficultyID) {
        this.difficultyID = difficultyID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
}
