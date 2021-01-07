package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ElementArmourHUD extends Element {

    public ElementArmourHUD() {
        super("ArmourHUD");
        this.width = 64;
        this.height = 64;
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public void onRendered() {
        if(this.background && this.backgroundColor != null)
            Gui.drawRect(this.getPosition().getX() - 2, this.getPosition().getY() - 2, this.getPosition().getX() + this.width, this.getPosition().getY() + this.height, this.backgroundColor.setTransparency(backgroundTransparent ? 10 : 255));
        GlStateManager.pushMatrix();
        GlStateManager.scale(this.getPosition().getScale(), this.getPosition().getScale(), 1);
        for(int item = 0; item < mc.thePlayer.inventory.armorInventory.length; item++) {
            renderItemStack(this.getPosition(), item, mc.thePlayer.inventory.armorInventory[item]);
        }
        this.width = 64 * this.getPosition().getScale();
        this.height = 64 * this.getPosition().getScale();
        GlStateManager.popMatrix();
    }

    public void renderItemStack(ElementPosition position, int index, ItemStack item) {
        if(item == null) return;
        GL11.glPushMatrix();
        int offset = (-16 * index) + 48;
        if(item.getItem().isDamageable()) {
            double currentDmg = (item.getMaxDamage() - item.getItemDamage()) / (double) item.getMaxDamage() * 100;
            this.mc.fontRendererObj.drawString(String.format("%.2f%%", currentDmg), (position.getX() / this.getPosition().getScale()) + 20, (position.getY() / this.getPosition().getScale()) + 5 + offset, this.colour.getHex(), this.getTextShadow());
        }
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, position.getX() / this.getPosition().getScale(), position.getY() / this.getPosition().getScale() + offset);
        GL11.glPopMatrix();
    }

}