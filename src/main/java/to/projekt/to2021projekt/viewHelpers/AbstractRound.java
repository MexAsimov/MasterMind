package to.projekt.to2021projekt.viewHelpers;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public abstract class AbstractRound extends HBox {
    protected final int columnsNumber;
    protected final int roundNumber;
    protected final String color;

    protected final ArrayList<ColorIconView> roundColors = new ArrayList<>();

    public AbstractRound(int columnsNumber, int roundNumber, String color) {
        this.setAlignment(Pos.CENTER);
        this.columnsNumber = columnsNumber;
        this.roundNumber = roundNumber;
        this.color = color;
    }

    protected void createRound() {
        HBox box = new HBox();
        box.setSpacing(10);

        for (int i = 0; i < this.columnsNumber; ++i) {
            ColorIconView view = new ColorIconView(22.5,"gray");
            view.setOnMouseClicked(event -> {
                if(!RoundState.getColor().isEmpty() && RoundState.getRoundCounter() == this.roundNumber){
                    view.setColor(RoundState.getColor());
                    view.setLightIcon();
                }
            });
            view.setOnMouseEntered(event -> {
                setCursor(Cursor.HAND);
                if(RoundState.getRoundCounter() == this.roundNumber
                        && (view.getColor().equals("gray"))) view.setDarkIcon();
            });
            view.setOnMouseExited(event -> {
                setCursor(Cursor.DEFAULT);
                if(RoundState.getRoundCounter() == this.roundNumber
                        && view.getColor().equals("gray")) view.setLightIcon();
            });
            roundColors.add(view);
            box.getChildren().add(view);
        }
        this.getChildren().add(box);
    }

    public ArrayList<String> getColorsArray() {
        ArrayList<String> colors = new ArrayList<>();
        for(ColorIconView view: roundColors) colors.add(view.getColor());
        return colors;
    }
}
