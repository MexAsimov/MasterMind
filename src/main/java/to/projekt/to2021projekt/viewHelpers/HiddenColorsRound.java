package to.projekt.to2021projekt.viewHelpers;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class HiddenColorsRound extends AbstractRound{

    public HiddenColorsRound(int columnsNumber, String iconPath) {
        super(columnsNumber, -1, iconPath, "dark.png", "dark.png");
        createRound();
    }

    public void setHiddenColors(ArrayList<String> colors) {
        for(int i = 0; i < colors.size(); ++i) roundColors.get(i).setColor(colors.get(i));
    }

    public void showHiddenColors(ArrayList<String> colorsUrls) {
        int index = 0;
        for(ColorIconView view: roundColors) {
            view.setImage(new Image(absoluteIconPath + colorsUrls.get(index) + ".png"));
            index++;
        }
    }
}
