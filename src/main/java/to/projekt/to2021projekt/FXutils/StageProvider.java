package to.projekt.to2021projekt.FXutils;

import javafx.stage.Stage;

public class StageProvider {
    private static StageProvider instance;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void registerStage(Stage stage) {
        this.stage = stage;
    }

    private StageProvider() {}

    public static StageProvider getInstance() {
        if (instance == null) {
            instance = new StageProvider();
        }
        return instance;
    }
}
