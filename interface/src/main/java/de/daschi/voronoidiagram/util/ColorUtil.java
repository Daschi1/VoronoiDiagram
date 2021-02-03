package de.daschi.voronoidiagram.util;

import javafx.scene.paint.Color;

public class ColorUtil {
    public static Color blend(final Color color, final Color color1) {
        return new Color(MathUtil.clamp((color.getRed() + color1.getRed()) / 2, 0, 1), MathUtil.clamp((color.getGreen() + color1.getGreen()) / 2, 0, 1), MathUtil.clamp((color.getBlue() + color1.getBlue()) / 2, 0, 1), MathUtil.clamp((color.getOpacity() + color1.getOpacity()) / 2, 0, 1));
    }

    public static Color invertColor(final Color color) {
        double red = MathUtil.scaleToRange(color.getRed(), 0, 1, 0, 255);
        double green = MathUtil.scaleToRange(color.getGreen(), 0, 1, 0, 255);
        double blue = MathUtil.scaleToRange(color.getBlue(), 0, 1, 0, 255);
        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;
        return Color.rgb((int) red, (int) green, (int) blue);
    }
}
