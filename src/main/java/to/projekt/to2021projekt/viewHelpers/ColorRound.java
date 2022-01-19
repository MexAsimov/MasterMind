package to.projekt.to2021projekt.viewHelpers;

import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ColorRound extends AbstractRound {

    private final String defaultResultColor;
    private final ArrayList<ColorIconView> resultColors = new ArrayList<>();
    private final String resultHalfColor;
    private final String resultFullColor;

    public ColorRound(int roundNumber, int columnsNumber) {
        super(columnsNumber, roundNumber, "gray");
        this.setSpacing(40);
        this.setAlignment(Pos.CENTER);
        this.defaultResultColor = "gray";
        this.resultHalfColor = "yellow";
        this.resultFullColor = "green";

        this.getStyleClass().add("round");

        createRoundResultsBox();
        createRound();
    }

    public void createRoundResultsBox() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        for (int i = 0; i < this.columnsNumber; ++i) {
            ColorIconView view = new ColorIconView(12.5, defaultResultColor);
            resultColors.add(view);
            box.getChildren().add(view);
        }
        this.getChildren().add(box);
    }

    public void setResult(int halfNumber, int fullNumber) {
        for(int i = 0; i < fullNumber; ++i) {
            resultColors.get(i).setColor(this.resultFullColor);
            resultColors.get(i).setLightIcon();
        }
        for(int i = fullNumber; i < fullNumber + halfNumber; ++i) {
            resultColors.get(i).setColor(this.resultHalfColor);
            resultColors.get(i).setLightIcon();
        }
    }

    public void setRound(){
        for(ColorIconView view: roundColors) view.getStyleClass().add("colorSetRound");
        for(ColorIconView view: resultColors) view.getStyleClass().add("colorSetRound");
        this.getStyleClass().add("setRound");
    }

    public void unsetRound(){
        for(ColorIconView view: roundColors) view.getStyleClass().remove("colorSetRound");
        for(ColorIconView view: resultColors) view.getStyleClass().remove("colorSetRound");
        this.getStyleClass().remove("setRound");
    }
}
