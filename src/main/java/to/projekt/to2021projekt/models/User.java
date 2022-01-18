package to.projekt.to2021projekt.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long userID;

    private String email;
    private String hashedPassword;

    @OneToMany(mappedBy = "playerID")
    private Set<Game> games;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public User(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        games = new HashSet<>();
    }

    public Set<Game> getMyGames() {
        return games;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public User(){}

    public void addNewGame(Game game) {
        this.games.add(game);
    }
}
