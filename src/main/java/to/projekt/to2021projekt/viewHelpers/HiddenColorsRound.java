package to.projekt.to2021projekt.viewHelpers;

import java.util.ArrayList;

public class HiddenColorsRound extends AbstractRound {

    public HiddenColorsRound(int columnsNumber) {
        super(columnsNumber, -1, "gray");
        createRound();
    }

    public void setHiddenColors(ArrayList<String> colors) {
        for(int i = 0; i < colors.size(); ++i) roundColors.get(i).setColor(colors.get(i));
    }

    public void showHiddenColors(ArrayList<String> colors) {
        int index = 0;
        for(ColorIconView view: roundColors) {
            view.setColor(colors.get(index));
            view.setLightIcon();
            index++;
        }
    }
}
