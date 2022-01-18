package to.projekt.to2021projekt.models;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name = "DIFFICULTY")
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "difficulty_id", nullable = false)
    private long difficultyID;
    @OneToMany(mappedBy = "difficultyID")
    private Set<Game> gamesWithDifficulty;

    private int numOfColors;

    private int numOfRounds;

    private boolean colorRepetition;

    public Difficulty() {
    }

    public Difficulty(int numOfColors, int numOfRounds, boolean colorRepetition) {
        this.numOfColors = numOfColors;
        this.numOfRounds = numOfRounds;
        this.colorRepetition = colorRepetition;
    }

    public int getNumOfColors() {
        return numOfColors;
    }

    public void setNumOfColors(int numOfColors) {
        this.numOfColors = numOfColors;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }

    public boolean getColorRepetition() {
        return this.colorRepetition;
    }
}
