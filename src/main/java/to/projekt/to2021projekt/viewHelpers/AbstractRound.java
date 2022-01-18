package to.projekt.to2021projekt.viewHelpers;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public abstract class AbstractRound extends HBox {
    protected final int columnsNumber;
    protected final int roundNumber;
    protected final String defaultColorUrl;
    protected final String defaultDarkColorUrl;
    protected final String absoluteIconPath;
    protected final ArrayList<ColorIconView> roundColors = new ArrayList<>();

    public AbstractRound(int columnsNumber, int roundNumber, String iconPath, String iconFilename, String darkIconFilename) {
        this.setAlignment(Pos.CENTER);
        this.absoluteIconPath = iconPath;
        this.columnsNumber = columnsNumber;
        this.roundNumber = roundNumber;
        this.defaultColorUrl = absoluteIconPath + iconFilename;
        this.defaultDarkColorUrl = absoluteIconPath + darkIconFilename;
    }

    protected void createRound() {
        HBox box = new HBox();
        box.setSpacing(10);

        for (int i = 0; i < this.columnsNumber; ++i) {
            ColorIconView view = new ColorIconView("gray", defaultColorUrl, defaultDarkColorUrl);
            view.setFitWidth(45);
            view.setFitHeight(45);
            view.setOnMouseClicked(event -> {
                if(!RoundState.getColor().isEmpty() && RoundState.getRoundCounter() == this.roundNumber){
                    Image im1 = new Image(absoluteIconPath + RoundState.getColor() + ".png");
                    view.setImage(im1);
                    view.setColor(RoundState.getColor());
                }
            });
            view.setOnMouseEntered(event -> {
                if(RoundState.getRoundCounter() == this.roundNumber
                        && view.getColor().equals("gray")) view.setDarkIcon();
            });
            view.setOnMouseExited(event -> {
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
