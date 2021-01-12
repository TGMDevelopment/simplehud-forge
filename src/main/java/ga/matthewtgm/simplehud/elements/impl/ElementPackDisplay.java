package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleButton;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.List;

public class ElementPackDisplay extends Element {

    public ElementPackDisplay() {
        super("Pack Display");
        this.elementScreen = new ElementGUI(this) {

            @Override
            public void initGui() {
                this.buttonList.add(new GuiSimpleButton(0, 0, 0, "Back"));
                this.buttonList.add(new GuiSimpleButton(1, 0, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(new GuiSimpleButton(2, 0, 40, "Show Brackets: " + (this.element.shouldRenderBrackets() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(new GuiSimpleButton(3, 0, 60, "Colour: " + this.element.colour.getAsMCColour(this.element.colour) + this.element.colour.getName().toLowerCase()));
                this.buttonList.add(new GuiSimpleButton(4, 0, 80, "Text Shadow: " + (this.element.getTextShadow() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(new GuiSimpleButton(6, 0, 120, "Background: " + (this.element.getBackground() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(new GuiSimpleButton(7, 0, 140, "Background Colour: " + this.element.backgroundColor.getAsMCColour(this.element.backgroundColor) + this.element.backgroundColor.getName().toLowerCase()));
                this.buttonList.add(new GuiSimpleButton(8, 0, 160, "Background Transparent: " + (this.element.backgroundTransparent ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            }

        };
    }

    @Override
    public void onRendered(ElementPosition position) {
        try {
            if (this.background && this.backgroundColor != null)
                Gui.drawRect(this.getPosition().getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.setTransparency(backgroundTransparent ? 35 : 255));
            GlStateManager.pushMatrix();
            GlStateManager.scale(this.getPosition().getScale(), this.getPosition().getScale(), 1);
            this.width = 32 + this.mc.fontRendererObj.getStringWidth(this.getCurrentPack().getPackName()) * position.getScale();
            this.height = 32 * position.getScale();
            this.mc.fontRendererObj.drawString(this.getCurrentPack().getPackName(), (position.getX() / position.getScale()) + 36, (position.getY() / position.getScale()) + 16, this.colour.getHex());
            this.mc.getTextureManager().bindTexture(this.biToRl(this.getCurrentPack().getPackImage()));
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            Gui.drawModalRectWithCustomSizedTexture(position.getX() / position.getScale(), position.getY() / position.getScale(), 0, 0, 32 * position.getScale(), this.height, 32 * position.getScale(), this.height);
            GlStateManager.popMatrix();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private ResourceLocation biToRl(BufferedImage image) {
        DynamicTexture dt;
        try {
            dt = new DynamicTexture(image);
        } catch(Exception e) {
            dt = TextureUtil.missingTexture;
        }
        return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("thumb.png", dt);
    }

    private IResourcePack getCurrentPack() {
        final List list = this.mc.getResourcePackRepository().getRepositoryEntries();
        final IResourcePack pack = null;
        if (list.size() > 0) {
            return ((ResourcePackRepository.Entry) list.get(0)).getResourcePack();
        }
        return this.mc.mcDefaultResourcePack;
    }

    }