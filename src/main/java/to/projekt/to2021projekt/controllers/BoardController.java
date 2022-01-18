package to.projekt.to2021projekt.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.Difficulty;
import to.projekt.to2021projekt.models.Game;
import to.projekt.to2021projekt.models.User;
import to.projekt.to2021projekt.util.ConfigSerializer;
import to.projekt.to2021projekt.util.MailController;
import to.projekt.to2021projekt.util.MailType;
import to.projekt.to2021projekt.viewHelpers.ColorIconView;
import to.projekt.to2021projekt.viewHelpers.ColorRound;
import to.projekt.to2021projekt.viewHelpers.HiddenColorsRound;
import to.projekt.to2021projekt.viewHelpers.RoundState;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

public class BoardController {

    @FXML
    public VBox colorsContainer;
    @FXML
    public HBox hiddenColors;
    @FXML
    public VBox roundColors;
    @FXML
    public Button checkButton;
    @FXML
    public Button restartButton;
    @FXML
    public Button exitButton;
    @FXML
    public BorderPane boardWindow;
    @FXML
    public Label scoreField;
    @FXML
    public Label minutesField;
    @FXML
    public Label secondsField;
    private int seconds = 0;
    private int minutes = 0;
    private Timer timer;
    private int numOfColors;
    private int numOfRounds;
    private boolean colorRepetition;

    private String iconsAbsolutePath;
    private final ArrayList<ColorRound> roundList = new ArrayList<>();
    private HiddenColorsRound hiddenColorsRound;
    private int fullPoints, halfPoints;
    private ArrayList<String> colors;

    private final String[] colorsNameArray = {"red", "orange", "yellow", "pink", "purple", "green", "blue", "turquoise"};
    private final String[] darkColorsNameArray = {"red_dark", "orange_dark", "yellow_dark", "pink_dark", "purple_dark", "green_dark", "blue_dark", "turquoise_dark"};
    private final Random random = new Random();
    @FXML
    public void initialize() throws IOException {
        RoundState.resetState();
        loadConfig();
        createColorsPalette();
        createHiddenColors();
        createRoundBoard();
        initializeTimer();
    }

    private void loadConfig() throws IOException {
        ConfigSerializer configSerializer = new ConfigSerializer();
        this.numOfColors = configSerializer.getNumOfColors();
        this.numOfRounds = configSerializer.getNumOfRounds();
        this.colorRepetition = configSerializer.getColorRepetition();
        iconsAbsolutePath = new java.io.File(".").getCanonicalPath() + "\\src\\main\\resources\\to\\projekt\\to2021projekt\\icons\\";
    }

    private void initializeTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTimer());
            }
        }, 0, 1000);
    }

    public void createColorsPalette()  {
        colorsContainer.setSpacing(20);
        HBox box;
        for(int j = 0; j < colorsNameArray.length/2; ++j) {
            box = new HBox();
            for (int i = 0; i < 2; ++i) {
                box.setSpacing(20);
                box.setAlignment(Pos.CENTER);
                String path = iconsAbsolutePath + colorsNameArray[2*j + i] + ".png";
                String darkPath = iconsAbsolutePath + darkColorsNameArray[2*j + i] + ".png";
                ColorIconView view = new ColorIconView(colorsNameArray[2*j + i], path, darkPath);
                view.setFitWidth(70);
                view.setFitHeight(70);
                view.setOnMouseClicked(event -> {
                    RoundState.setColor(view.getColor());
                    if(RoundState.getColorView() != null) {
                        RoundState.getColorView().setLightIcon();
                        RoundState.getColorView().click();
                    }
                    view.setDarkIcon();
                    view.click();
                    RoundState.setColorView(view);
                });
                view.setOnMouseEntered(event -> view.setDarkIcon());
                view.setOnMouseExited(event -> {
                    if(!view.isClicked()) view.setLightIcon();
                });
                box.getChildren().add(view);
            }
            colorsContainer.getChildren().add(box);
        }
    }

    public void createHiddenColors() {
        hiddenColors.setAlignment(Pos.CENTER);
        hiddenColorsRound = new HiddenColorsRound(this.numOfColors, iconsAbsolutePath);
        colors = new ArrayList<>();
        for(int i = 0; i < this.numOfColors; i++){
            int index = random.nextInt(colorsNameArray.length);
            while(colorRepetition == false && colors.contains(colorsNameArray[index])){
                index = random.nextInt(colorsNameArray.length);
            }
            colors.add(colorsNameArray[index]);
        }
        hiddenColorsRound.setHiddenColors(colors);
        hiddenColors.getChildren().add(hiddenColorsRound);
    }

    public void createRoundBoard() {
        roundColors.setSpacing(10);
        for(int j = this.numOfRounds - 1; j >=0; --j) {
            ColorRound box = new ColorRound(j , this.numOfColors, iconsAbsolutePath);
            roundList.add(box);
            roundColors.getChildren().add(box);
        }
        Collections.reverse(roundList);
        roundList.get(0).setRound();
    }

    public void checkColorsRound() {
        ArrayList<String> setColors = roundList.get(RoundState.getRoundCounter()).getColorsArray();
        fullPoints = 0;
        halfPoints = 0;

        setPointsAfterRound(setColors);

        roundList.get(RoundState.getRoundCounter()).setResult(halfPoints, fullPoints);
        roundList.get(RoundState.getRoundCounter()).unsetRound();
        updateScore();
        if(isGameDone()){
            timer.cancel();
            endGame();
            return;
        }
        RoundState.nextRound();
        roundList.get(RoundState.getRoundCounter()).setRound();
    }

    public void restartGame() throws IOException {
        RoundState.resetState();
        SceneProvider.getInstance().loadScene("view/masterMindView.fxml", "stylesheets/login-panel.css");
        StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
        this.initialize();
    }

    public void exit() {
        SceneProvider.getInstance().loadScene("view/start-view.fxml", "stylesheets/menu-panel.css");
        StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
    }

    public int getScore(){
        return Integer.parseInt(scoreField.getText());
    }

    private void endGame() {
        hiddenColorsRound.showHiddenColors(colors);
        checkButton.disableProperty().setValue(true);
        if(isGameWon()) {
            updateScoreAtEnd();
            showWinDialog();
            processWinGame();
        }
        else{
            showLoseDialog();
        }
    }

    private boolean isGameWon(){
        return fullPoints == this.numOfColors;
    }

    private boolean isGameDone(){
        return isGameWon() || RoundState.getRoundCounter() == this.numOfRounds - 1;
    }

    private void setPointsAfterRound(ArrayList<String> setColors){
        ArrayList<String> checkColors = new ArrayList<>(colors);
        for(int i=0; i<this.numOfColors; i++) {
            if(setColors.get(i).equals(checkColors.get(i))){
                fullPoints++;
                checkColors.set(i, "None");
                setColors.set(i, "None");
            }
        }

        for(int i=0; i<this.numOfColors; i++) {
            if(setColors.get(i) == "None"){
                continue;
            }
            if(checkColors.contains(setColors.get(i))){
                halfPoints++;
                checkColors.set(checkColors.indexOf(setColors.get(i)), "None");
            }
        }
    }

    private void updateScore(){
        int prevPoints = Integer.parseInt(scoreField.getText());
        int newPoints = 10 * (fullPoints*3 + halfPoints);
        scoreField.setText(String.valueOf(prevPoints+newPoints));
    }

    // set all points to the end as full points
    private void updateScoreAtEnd(){
        int prevPoints = Integer.parseInt(scoreField.getText());
        int newPoints = 35*(numOfRounds-RoundState.getRoundCounter()) * numOfColors;
        scoreField.setText(String.valueOf(prevPoints+newPoints));
    }

    private void updateTimer() {
        seconds += 1;
        if(seconds == 60) {
            seconds = 0;
            minutes += 1;
        }
        String secondsString = (seconds > 9) ? Integer.toString(seconds) : "0" + Integer.toString(seconds);
        String minutesString = (minutes > 9) ? Integer.toString(minutes) : "0" + Integer.toString(minutes);
        secondsField.setText(secondsString);
        minutesField.setText(minutesString);
    }

    private void processWinGame(){
        System.out.println("Game won..");
        int result = Integer.parseInt(scoreField.getText());
        Game bestGame = SessionProvider.getBestScore();
        if(bestGame != null && bestGame.getResult() < result
                && bestGame.getPlayerID().getUserID() != SessionProvider.getLoggedUser().getUserID()){
            try {
                MailController mailController = new MailController();
                mailController.setReceiverEmail(bestGame.getPlayerID().getEmail());
                mailController.sendMail(MailType.NewRecordNotification);
            } catch (MessagingException e) {
                System.out.println("Mail error");
            }
        }
        Difficulty difficulty = new Difficulty(numOfColors, numOfRounds, colorRepetition);
        difficulty = SessionProvider.findDifficulty(difficulty);
        User user = SessionProvider.getLoggedUser();

        Game newGame = new Game(user,difficulty,
                result, (minutes*60 + seconds) );
        user.addNewGame(newGame);
        SessionProvider.saveGame(newGame);


    }

    private void showWinDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        VBox res = new VBox();
        Label label = new Label("You won! \n");
        label.setStyle("-fx-text-fill: green; -fx-font-size: 25; -fx-font-weight: bold;");
        res.getChildren().add(label);
        Label label2 = new Label("Your score: " + getScore());
        label2.setStyle("-fx-text-fill: black; -fx-font-size: 15;");
        res.getChildren().add(label2);
        alert.getDialogPane().setContent(res);
        ButtonType buttonTypeOK = new ButtonType("Save My Score");
        alert.getButtonTypes().setAll(buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {}
    }

    private void showLoseDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        VBox res = new VBox();
        Label label = new Label("You lost!");
        label.setStyle("-fx-text-fill: red; -fx-font-size: 25; -fx-font-weight: bold;");
        res.getChildren().add(label);
        alert.getDialogPane().setContent(res);
        ButtonType buttonTypeOK = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {}
    }
}
