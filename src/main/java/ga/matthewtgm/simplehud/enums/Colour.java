package ga.matthewtgm.simplehud.enums;

import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

public enum Colour {

    WHITE("White", new Color(255, 255, 255).getRGB()),
    GRAY("Gray", new Color(125, 125, 125).getRGB()),
    DARK_GRAY("Dark gray", new Color(75, 75, 75).getRGB()),
    BLACK("Black", new Color(26, 26, 26).getRGB()),
    DARK_RED("Dark red", new Color(130, 0, 0).getRGB()),
    RED("Red", new Color(213, 0, 0).getRGB()),
    GOLD("Gold", new Color(252, 158, 0).getRGB()),
    YELLOW("Yellow", new Color(217, 206, 0).getRGB()),
    DARK_GREEN("Dark green", new Color(5, 118, 0).getRGB()),
    GREEN("Green", new Color(10, 212, 0).getRGB()),
    AQUA("Aqua", new Color(0, 203, 187).getRGB()),
    DARK_AQUA("Dark aqua", new Color(0, 102, 92).getRGB()),
    DARK_BLUE("Dark blue", new Color(6, 0, 226).getRGB()),
    BLUE("Blue", new Color(0, 90, 226).getRGB()),
    LIGHT_PURPLE("Light purple", new Color(228, 0, 255).getRGB()),
    DARK_PURPLE("Dark purple", new Color(123, 0, 122).getRGB());

    private final String name;
    int hex;

    Colour(String name, int hex) {
        this.name = name;
        this.hex = hex;
    }

    public int setTransparency(int alpha) {
        return (hex & 0xFFFFFF) | (alpha << 24);
    }

    public Colour getNextColor(Colour color) {
        return values()[(color.ordinal() + 1) % values().length];
    }

    public String getName() {
        return this.name;
    }

    public EnumChatFormatting getAsMCColour(Colour colour) {
        return EnumChatFormatting.valueOf(colour.getName().toUpperCase().replaceAll(" ", "_"));
    }

    public int getHex() {
        return this.hex;
    }

}
