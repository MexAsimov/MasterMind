package to.projekt.to2021projekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.Game;

import java.io.IOException;
import java.util.Set;

public class StatisticsController {
    @FXML
    public ListView gamesList;
    @FXML
    public Button cancelButton;
    @FXML
    public Label viewTitleLabel;

    public StatisticsController() {

    }
    public void initLayout(){
        Set<Game> games = SessionProvider.getLoggedUser().getMyGames();

        gamesList.getItems().addAll(games.stream().map(game -> "Game " + game.getGameID() +
                ", Result: " + game.getResult() + ", Time: " + game.getGameTime()).toList());

        gamesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        gamesList.setOnMouseClicked(event ->
                System.out.println(gamesList.getSelectionModel().getSelectedItems().get(0)));

    }


    public void handleCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = StageProvider.getInstance().getStage();
        SceneProvider.getInstance().loadScene("view/start-view.fxml", "stylesheets/menu-panel.css");
        Scene scene = SceneProvider.getInstance().getScene();
        stage.setScene(scene);
    }
}
