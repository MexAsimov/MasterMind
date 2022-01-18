package to.projekt.to2021projekt.viewHelpers;

public class RoundState {
    private static int roundCounter = 0;
    private static String color = "";
    private static ColorIconView view;

    public static int getRoundCounter() {
        return roundCounter;
    }

    public static String getColor() {
        return color;
    }

    public static void setColor(String newColor) {
        color = newColor;
    }

    public static void nextRound() {
        RoundState.roundCounter++;
    }

    public static void setColorView(ColorIconView colorView) {
        view = colorView;
    }

    public static ColorIconView getColorView() { return view; }

    public static void resetState() {
        view = null;
        color = "";
        roundCounter = 0;
    }
}
