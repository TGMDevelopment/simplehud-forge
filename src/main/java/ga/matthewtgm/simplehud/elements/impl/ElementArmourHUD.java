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
    }

    @Override
    public void onRendered(ElementPosition position) {
<<<<<<< Updated upstream
        if(this.background && this.backgroundColor != null)
            Gui.drawRect(position.getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.setTransparency(backgroundTransparent ? 10 : 255));
=======
        if (this.background && this.backgroundColor != null)
            Gui.drawRect(position.getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.getRGBA());
>>>>>>> Stashed changes
        GlStateManager.pushMatrix();
        GlStateManager.scale(position.getScale(), position.getScale(), 1);
        for (int item = 0; item < mc.thePlayer.inventory.armorInventory.length; item++) {
            renderItemStack(position, item, mc.thePlayer.inventory.armorInventory[item]);
        }
<<<<<<< Updated upstream
        this.width = 64 * position.getScale();
        this.height = 64 * position.getScale();
=======
        if (this.getType() == RenderType.NONE || this.getType() == RenderType.BARS)
            this.width = (int) (17 * position.getScale());
        else this.width = Math.round(64 * position.getScale());
        this.height = Math.round(64 * position.getScale());
>>>>>>> Stashed changes
        GlStateManager.popMatrix();
    }

    public void renderItemStack(ElementPosition position, int index, ItemStack item) {
        if(item == null) return;
        GL11.glPushMatrix();
        int offset = (-16 * index) + 48;
        if(item.getItem().isDamageable()) {
            double currentDmg = (item.getMaxDamage() - item.getItemDamage()) / (double) item.getMaxDamage() * 100;
            this.mc.fontRendererObj.drawString(String.format("%.2f%%", currentDmg), (position.getX() / position.getScale()) + 20, (position.getY() / position.getScale()) + 5 + offset, this.colour.getHex(), this.getTextShadow());
        }
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, position.getX() / position.getScale(), position.getY() / position.getScale() + offset);
        GL11.glPopMatrix();
    }

}