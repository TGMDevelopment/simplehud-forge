package ga.matthewtgm.common.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiTransImageButton extends GuiTransButton {

    private final ResourceLocation rlImage;

    public GuiTransImageButton(int buttonId, int x, int y, ResourceLocation image) {
        super(buttonId, x, y, 20, 20, "");
        this.rlImage = image;
    }

    public GuiTransImageButton(int buttonId, int x, int y, int width, int height, ResourceLocation image) {
        super(buttonId, x, y, width, height, "");
        this.rlImage = image;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(rlImage);
        Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, this.width, this.height, this.width, this.height);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        this.mouseDragged(mc, mouseX, mouseY);
    }

}