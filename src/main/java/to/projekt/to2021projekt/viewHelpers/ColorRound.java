package to.projekt.to2021projekt.viewHelpers;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ColorRound extends AbstractRound {

    private final String defaultResultColorUrl;
    private final ArrayList<ColorIconView> resultColors = new ArrayList<>();
    private final String resultHalfColorUrl;
    private final String resultFullColorUrl;

    public ColorRound(int roundNumber, int columnsNumber, String iconPath) {
        super(columnsNumber, roundNumber, iconPath, "gray.png", "gray_dark.png");
        this.setSpacing(40);
        this.setAlignment(Pos.CENTER);
        this.defaultResultColorUrl = absoluteIconPath + "lightgray.png";
        this.resultHalfColorUrl = absoluteIconPath + "result_half.png";
        this.resultFullColorUrl = absoluteIconPath + "result_full.png";


        createRoundResultsBox();
        createRound();
    }

    public void createRoundResultsBox() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        for (int i = 0; i < this.columnsNumber; ++i) {
            ColorIconView view = new ColorIconView("gray", defaultResultColorUrl,defaultDarkColorUrl);
            view.setFitWidth(25);
            view.setFitHeight(25);
            resultColors.add(view);
            box.getChildren().add(view);
        }
        this.getChildren().add(box);
    }

    public void setResult(int halfNumber, int fullNumber) {
        for(int i = 0; i < fullNumber; ++i) resultColors.get(i).setImage(new Image(this.resultFullColorUrl));
        for(int i = fullNumber; i < fullNumber + halfNumber; ++i) resultColors.get(i).setImage(new Image(this.resultHalfColorUrl));
    }

    public void setRound(){
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public void unsetRound(){
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY)));
    }
}
