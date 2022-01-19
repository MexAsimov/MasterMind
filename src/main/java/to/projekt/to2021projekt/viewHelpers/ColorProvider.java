package to.projekt.to2021projekt.viewHelpers;

import java.util.HashMap;

public class ColorProvider {
    private static final String[] colorsNameArray = {"red", "orange", "yellow", "pink", "purple", "green", "blue", "turquoise"};
    private static final HashMap<String, String> colors = new HashMap<>(){{

        put("yellow", "#ebf306 ");
        put("green", "#62d22b ");
        put("gray", "#75767a");
        put("red", "#f01911 ");
        put("orange", "#ff5602 ");
        put("pink", "#ff2e54  ");
        put("purple", "#8127ff  ");
        put("blue", "#233cff      ");
        put("turquoise", "#10fffb ");

        put("darkred", "#860d09 ");
        put("darkorange", "#8f3000 ");
        put("darkyellow", "#939804  ");
        put("darkpink", "#96001c ");
        put("darkpurple", "#362e4a");
        put("darkblue", "#002647");
        put("darkturquoise", "#003837 ");
        put("darkgreen", "#377418  ");
        put("darkgray", "#414244 ");

    }};

    public static String getColorCode(String color){
        return colors.get(color);
    }

    public static String[] getColorsNameArray() {
        return colorsNameArray;
    }
}
