package to.projekt.to2021projekt.viewHelpers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ColorIconView extends ImageView {

    private String color;
    private final Image icon;
    private final Image darkIcon;
    private boolean isClicked;

    public ColorIconView(String color, String iconUrl, String darkIconUrl) {
        this.icon = new Image(iconUrl);
        this.darkIcon = new Image(darkIconUrl);
        this.setImage(icon);
        this.color = color;
    }

    public void setDarkIcon() { this.setImage(darkIcon);}
    public void setLightIcon() { this.setImage(icon); }
    public String getColor() {
        return color;
    }
    public void setColor(String color) { this.color = color; }
    public String getLightIconUrl() { return this.icon.getUrl(); }
    public boolean isClicked() { return isClicked; }
    public void click() { isClicked = !isClicked; }

}
