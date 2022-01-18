package to.projekt.to2021projekt.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;
import to.projekt.to2021projekt.util.ConfigSerializer;

import java.io.IOException;
import java.util.Optional;

public class SettingsController {
    final private int MAX_NUM_OF_ROUNDS = 10;
    final private int MIN_NUM_OF_ROUNDS = 2;
    final private int MAX_NUM_OF_COLORS = 6;
    final private int MIN_NUM_OF_COLORS = 2;

    @FXML
    public VBox settingsPanel;
    @FXML
    public TextField numOfColorsInput;
    @FXML
    public Button applyButton;
    @FXML
    public Button cancelButton;
    @FXML
    public TextField numOfRoundsInput;
    @FXML
    public CheckBox colorRepetitionInput;
    @FXML
    public Label colorsError;
    @FXML
    public Label roundsError;

    private ConfigSerializer configSerializer;

    public SettingsController() {
        configSerializer = new ConfigSerializer();
    }
    public void initView(){
        colorsError.setVisible(false);
        roundsError.setVisible(false);
        try {
            numOfColorsInput.setText(String.valueOf(configSerializer.getNumOfColors()));
            numOfRoundsInput.setText(String.valueOf(configSerializer.getNumOfRounds()));
            colorRepetitionInput.setSelected(Boolean.valueOf(configSerializer.getColorRepetition()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        applyNumValidation(numOfColorsInput);
        applyNumValidation(numOfRoundsInput);

    }
    public void applyChanges(ActionEvent actionEvent) throws IOException {
        if(validateConfig() == true){
            hideErrors();
            try {
                configSerializer.updateConfig(Integer.parseInt(numOfColorsInput.getText()),
                        Integer.parseInt(numOfRoundsInput.getText()), colorRepetitionInput.isSelected());
                showConfigSavedDialog();
            } catch (IOException e) {
                e.printStackTrace();
            };
        }
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = StageProvider.getInstance().getStage();
        SceneProvider.getInstance().loadScene("view/start-view.fxml", "stylesheets/menu-panel.css");
        Scene scene = SceneProvider.getInstance().getScene();
        stage.setScene(scene);
    }

    private boolean validateConfig() throws IOException {
        boolean result = true;
        if(Integer.parseInt(numOfColorsInput.getText()) > MAX_NUM_OF_COLORS
                 || Integer.parseInt(numOfColorsInput.getText()) < MIN_NUM_OF_COLORS){
            colorsError.setVisible(true);
            numOfColorsInput.setText(String.valueOf(configSerializer.getNumOfColors()));
            result = false;
        }
        if(Integer.parseInt(numOfRoundsInput.getText()) > MAX_NUM_OF_ROUNDS
                || Integer.parseInt(numOfRoundsInput.getText()) < MIN_NUM_OF_ROUNDS){
            roundsError.setVisible(true);
            numOfRoundsInput.setText(String.valueOf(configSerializer.getNumOfRounds()));
            result = false;
        }
        return result;
    }
    private void hideErrors(){
        roundsError.setVisible(false);
        colorsError.setVisible(false);
    }
    private void showConfigSavedDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        VBox res = new VBox();
        Label label = new Label("Config Saved!");
        label.setStyle("-fx-text-fill: green; -fx-font-size: 25;");
        res.getChildren().add(label);
        alert.getDialogPane().setContent(res);
        ButtonType buttonTypeOK = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {}
    }
    private void applyNumValidation(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }


}
