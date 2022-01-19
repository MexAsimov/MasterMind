package to.projekt.to2021projekt.viewHelpers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ColorIconView extends Circle {

    private String color;
    private String darkColor;
    private boolean isClicked;

    public ColorIconView(double radius, String color) {
        this.setRadius(radius);
        this.darkColor = "dark"+color;
        this.color = color;
        setLightIcon();
        this.getStyleClass().add("color");
    }

    public void setDarkIcon() { setStyle("-fx-fill: "+ColorProvider.getColorCode(darkColor)+";");}
    public void setLightIcon() { setStyle("-fx-fill: "+ColorProvider.getColorCode(color)+";"); }

    public String getColor() {
        return color;
    }
    public void setColor(String color) { this.color = color;}
    public boolean isClicked() { return isClicked; }
    public void click() { isClicked = !isClicked; }

}
