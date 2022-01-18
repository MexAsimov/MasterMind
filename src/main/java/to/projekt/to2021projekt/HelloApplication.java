package to.projekt.to2021projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.Difficulty;
import to.projekt.to2021projekt.models.Game;
import to.projekt.to2021projekt.models.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneProvider.getInstance().loadScene("view/login-view.fxml", "stylesheets/login-panel.css");
        Scene scene = SceneProvider.getInstance().getScene();
        stage.setTitle("Master Mind");
        StageProvider.getInstance().registerStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
