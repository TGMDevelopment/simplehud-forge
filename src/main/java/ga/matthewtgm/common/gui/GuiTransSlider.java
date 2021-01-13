package ga.matthewtgm.common.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.awt.*;

public class GuiTransSlider extends GuiSlider {

    public GuiTransSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr);
    }

    public GuiTransSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, par);
    }

    public GuiTransSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        super(id, xPos, yPos, displayStr, minVal, maxVal, currentVal, par);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible)
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(buttonTextures);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(0, 0, 0, 55).getRGB());
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;
            if (packedFGColour != 0)
            {
                color = packedFGColour;
            }
            else if (!this.enabled)
            {
                color = 10526880;
            }
            else if (this.hovered)
            {
                color = 16777120;
            }
            String buttonText = this.displayString;
            int strWidth = mc.fontRendererObj.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRendererObj.getStringWidth("...");
            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRendererObj.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";
            this.drawCenteredString(mc.fontRendererObj, buttonText, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
        }
    }

    @Override
    public void updateSlider() {
        super.updateSlider();
    }

    @Override
    public double getValue() {
        return super.getValue();
    }

    @Override
    public int getValueInt() {
        return super.getValueInt();
    }

}