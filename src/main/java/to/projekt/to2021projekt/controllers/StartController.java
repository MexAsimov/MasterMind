package to.projekt.to2021projekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;

import javax.mail.MessagingException;
import java.io.IOException;

public class StartController {
    @FXML
    public VBox startPanel;
    @FXML
    public Button newGameBtn;
    @FXML
    public Button checkStatsBtn;
    @FXML
    public Button settingsBtn;

    public StartController() {
    }

    public void showStats(ActionEvent actionEvent) throws IOException {
        Stage stage = StageProvider.getInstance().getStage();
        SceneProvider.getInstance().loadScene("view/stats-view.fxml", "stylesheets/statistics-panel.css");
        StatisticsController statisticsController = SceneProvider.getInstance().getLoader().getController();
        stage.setScene(SceneProvider.getInstance().getScene());
        statisticsController.initLayout();
    }

    public void showSettings(ActionEvent actionEvent) throws IOException {
        Stage stage = StageProvider.getInstance().getStage();
        SceneProvider.getInstance().loadScene("view/settings-view.fxml", "stylesheets/settings-panel.css");
        SettingsController settingsController = SceneProvider.getInstance().getLoader().getController();
        stage.setScene(SceneProvider.getInstance().getScene());
        settingsController.initView();
    }

    public void startNewGame(ActionEvent actionEvent) throws MessagingException {
        SceneProvider.getInstance().loadScene("view/board-view.fxml", "stylesheets/board-panel.css");
        StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
        StageProvider.getInstance().getStage().setResizable(false);
        StageProvider.getInstance().getStage().setX(100);
        StageProvider.getInstance().getStage().setY(10);
    }
}
