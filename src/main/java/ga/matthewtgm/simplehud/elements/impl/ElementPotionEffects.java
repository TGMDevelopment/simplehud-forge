package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class ElementPotionEffects extends Element {

    public ElementPotionEffects() {
        super("Potion Effects");
    }

    @Override
    public void onRendered() {
        if(this.background && this.backgroundColor != null)
            Gui.drawRect(this.getPosition().getX() - 2, this.getPosition().getY() - 2, this.getPosition().getX() + this.width, this.getPosition().getY() + this.height, this.backgroundColor.setTransparency(backgroundTransparent ? 10 : 255));
        GlStateManager.pushMatrix();
        GlStateManager.scale(this.getPosition().getScale(), this.getPosition().getScale(), 1);
        this.drawPotions();
        this.width = 101 * this.getPosition().getScale();
        this.height = 154 * this.getPosition().getScale();
        GlStateManager.popMatrix();
    }

    public void drawPotions() {
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
                    drawTexturedModalRect(((this.getPosition().getX() / this.getPosition().getScale()) + offsetX) - 20, ((this.getPosition().getY() / this.getPosition().getScale()) + i2) - offsetY, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
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

                this.mc.fontRendererObj.drawString(s1, (this.getPosition().getX() / this.getPosition().getScale()) + offsetX, ((this.getPosition().getY() / this.getPosition().getScale()) + i2) - offsetY, this.colour.getHex(), this.getTextShadow());
                String s = Potion.getDurationString(potioneffect);
                this.mc.fontRendererObj.drawString(s, (this.getPosition().getX() / this.getPosition().getScale()) + offsetX, ((this.getPosition().getY() / this.getPosition().getScale()) + i2 + 10) - offsetY, this.colour.getHex(), this.getTextShadow());
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