package ga.matthewtgm.simplehud.utils;

import java.awt.*;

public class ColourUtils {

    private static ColourUtils INSTANCE;
    public static ColourUtils getInstance() {
        if(INSTANCE == null)
            INSTANCE = new ColourUtils();
        return INSTANCE;
    }

    public int getChroma() {
        final long l = System.currentTimeMillis();
        return Color.HSBtoRGB(l % 2000L / 2000.0F, 0.8F, 0.8F);
    }

}