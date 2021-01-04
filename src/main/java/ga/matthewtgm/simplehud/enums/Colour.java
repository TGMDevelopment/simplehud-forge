package ga.matthewtgm.simplehud.enums;

import net.minecraft.util.EnumChatFormatting;

public enum Colour {

    WHITE("White", -1),
    GRAY("Gray", -5592406),
    DARK_GRAY("Dark gray", -11184811),
    BLACK("Black", -16777216),
    DARK_RED("Dark red", -5636096),
    RED("Red", -43691),
    GOLD("Gold", -22016),
    YELLOW("Yellow", -171),
    DARK_GREEN("Dark green", -16733696),
    GREEN("Green", -11141291),
    AQUA("Aqua", -11141121),
    DARK_AQUA("Dark aqua", -16733526),
    DARK_BLUE("Dark blue", -16777046),
    BLUE("Blue", -11184641),
    LIGHT_PURPLE("Light purple", -43521),
    DARK_PURPLE("Dark purple", -5635926);

    private final String name;
    int hex;

    Colour(String name, int hex) {
        this.name = name;
        this.hex = hex;
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
