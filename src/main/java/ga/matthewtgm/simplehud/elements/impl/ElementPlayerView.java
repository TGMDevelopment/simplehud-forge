package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransSlider;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import ga.matthewtgm.simplehud.gui.GuiConfigurationCategories;
import ga.matthewtgm.simplehud.gui.elements.GuiElement;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

public class ElementPlayerView extends Element {

    public ElementPlayerView() {
        super("Player View", "General");

        this.elementScreen = new GuiElement(new GuiConfigurationCategories.GuiConfigurationGeneral(new GuiConfiguration(SimpleHUD.getInstance().configGui)), this) {

            @Override
            public void initGui() {
                super.initGui();
                this.buttonList.clear();
                this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Save and go back"));
                this.buttonList.add(new GuiTransButton(1, this.width / 2 - 105, this.height / 2 - 100, 100, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(scaleSlider = new GuiTransSlider(4, this.width / 2 + 5, this.height / 2 - 100, 100, 20, "Scale: ", "", 1, 5, this.element.getPosition().getScale(), true, true));
                this.buttonList.add(backgroundButton = new GuiTransButton(6, this.width / 2 - 50, this.height / 2 - 70, 100, 20, "Background"));
            }

        };
    }

    @Override
    public void onRendered(ElementPosition position) {
        if (this.background && this.backgroundColor != null)
            Gui.drawRect(position.getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.getRGBA());
        if (this.mc.thePlayer != null) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            GuiInventory.drawEntityOnScreen(
                    Math.round(position.x + this.width / 2),
                    Math.round(position.y + this.height / 2 * 2),
                    Math.round(this.getPosition().getScale() * 25),
                    this.mc.thePlayer.cameraYaw,
                    this.mc.thePlayer.rotationPitch,
                    this.mc.thePlayer
            );
            GlStateManager.popMatrix();
        }
        this.width = Math.round(32 * this.getPosition().getScale());
        this.height = Math.round(48 * this.getPosition().getScale());
    }

}