package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransSlider;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import ga.matthewtgm.simplehud.gui.GuiElement;
import ga.matthewtgm.simplehud.utils.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class ElementPotionEffects extends Element {

    public ElementPotionEffects() {
        super("Potion Effects");

        this.elementScreen = new GuiElement(new GuiConfiguration(SimpleHUD.getInstance().configGui), this) {

            @Override
            public void initGui() {
                super.initGui();
                this.buttonList.clear();
                this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Save and go back"));
                this.buttonList.add(new GuiTransButton(1, this.width / 2 - 105, this.height / 2 - 100, 100, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(new GuiTransButton(3, this.width / 2 + 5, this.height / 2 - 100, 100, 20, "Text Shadow: " + (this.element.getTextShadow() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(scaleSlider = new GuiTransSlider(4, this.width / 2 - 105, this.height / 2 - 70, 100, 20, "Scale: ", "", 1, 5, this.element.getPosition().getScale(), true, true));
                this.buttonList.add(rSlider = new GuiTransSlider(5, this.width / 2 + 5, this.height / 2 - 70, 100, 20, "Red: ", "", 1, 255, this.element.colour.getR(), false, true));
                this.buttonList.add(gSlider = new GuiTransSlider(6, this.width / 2 - 105, this.height / 2 - 40, 100, 20, "Green: ", "", 1, 255, this.element.colour.getG(), false, true));
                this.buttonList.add(bSlider = new GuiTransSlider(7, this.width / 2 + 5, this.height / 2 - 40, 100, 20, "Blue: ", "", 1, 255, this.element.colour.getB(), false, true));
                this.buttonList.add(new GuiTransButton(8, this.width / 2 - 105, this.height / 2 - 10, 100, 20, "Background: " + (this.element.getBackground() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(bgRSlider = new GuiTransSlider(9, this.width / 2 + 5, this.height / 2 - 10, 100, 20, "BG Red: ", "", 1, 255, this.element.backgroundColor.getR(), false, true));
                this.buttonList.add(bgGSlider = new GuiTransSlider(10, this.width / 2 - 105, this.height / 2 + 20, 100, 20, "BG Green: ", "", 1, 255, this.element.backgroundColor.getG(), false, true));
                this.buttonList.add(bgBSlider = new GuiTransSlider(11, this.width / 2 + 5, this.height / 2 + 20, 100, 20, "BG Blue: ", "", 1, 255, this.element.backgroundColor.getB(), false, true));
                this.buttonList.add(bgASlider = new GuiTransSlider(12, this.width / 2 - 105, this.height / 2 + 50, 100, 20, "BG Alpha: ", "", 1, 255, this.element.backgroundColor.getA(), false, true));
                this.buttonList.add(chromaToggle = new GuiTransButton(14, this.width / 2 + 5, this.height / 2 + 50, 100, 20, "Chroma: " + (this.element.isChroma() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            }

        };
    }

    @Override
    public void onRendered(ElementPosition position) {
        if(this.background && this.backgroundColor != null)
            Gui.drawRect(position.getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.getRGBA());
        GlStateManager.pushMatrix();
        GlStateManager.scale(position.getScale(), position.getScale(), 1);
        this.drawPotions(position);
        this.width = Math.round(101 * position.getScale());
        this.height = Math.round(154 * position.getScale());
        GlStateManager.popMatrix();
    }

    public void drawPotions(ElementPosition position) {
        int offsetX = 21;
        int offsetY = 14;
        int i2 = 16;
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();

        if (!collection.isEmpty())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;


            if (collection.size() > 5)
            {
                l = 132 / (collection.size() - 1);
            }

            for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects())
            {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                if (potion.hasStatusIcon())
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                    int i1 = potion.getStatusIconIndex();
                    drawTexturedModalRect(Math.round(((position.getX() / position.getScale()) + offsetX) - 20), Math.round(((position.getY() / position.getScale()) + i2) - offsetY), 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }

                String s1 = I18n.format(potion.getName(), new Object[0]);
                if (potioneffect.getAmplifier() == 1)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                }
                else if (potioneffect.getAmplifier() == 2)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                }
                else if (potioneffect.getAmplifier() == 3)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                }

                this.mc.fontRendererObj.drawString(s1, (position.getX() / position.getScale()) + offsetX, ((position.getY() / position.getScale()) + i2) - offsetY, this.isChroma() ? ColourUtils.getInstance().getChroma() : this.colour.getRGB(), this.getTextShadow());
                String s = Potion.getDurationString(potioneffect);
                this.mc.fontRendererObj.drawString(s, (position.getX() / position.getScale()) + offsetX, ((position.getY() / position.getScale()) + i2 + 10) - offsetY, this.isChroma() ? ColourUtils.getInstance().getChroma() : this.colour.getRGB(), this.getTextShadow());
                i2 += l;
            }
        }
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)0).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)0).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }

}