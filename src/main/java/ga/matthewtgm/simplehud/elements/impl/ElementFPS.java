package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import ga.matthewtgm.simplehud.gui.GuiConfigurationCategories;
import ga.matthewtgm.simplehud.gui.elements.GuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ElementFPS extends Element {

    public static boolean precise;

    public ElementFPS() {
        super("FPS", "General");
        if (this.prefix == null) this.prefix = "FPS";
    }

    public static boolean isPrecise() {
        return precise;
    }

    public static void setPrecise(boolean precise) {
        ElementFPS.precise = precise;
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(String.valueOf(Minecraft.getDebugFPS()));
        this.height = Math.round(10 * this.getPosition().getScale());
        super.onRendered(position);
    }

}