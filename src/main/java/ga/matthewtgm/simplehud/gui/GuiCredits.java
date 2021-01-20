package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GuiCredits extends GuiScreen {

    private GuiScreen parent;
    public GuiScreen getParent() {
        return parent;
    }

    public GuiCredits(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.getParent() == null ? "Close" : "Back"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            Minecraft.getMinecraft().displayGuiScreen(this.getParent());
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD", width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        int authorScale = 2;
        GlStateManager.scale(authorScale, authorScale, 0);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.YELLOW + "Wyvest", this.width / 2 / authorScale, this.height / 3 / authorScale, -1);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + "Shoddy", this.width / 2 / authorScale, this.height / 3 / authorScale + 20, -1);
	      this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.LIGHT_PURPLE + "Moulberry", this.width / 2 / authorScale, this.height / 3 / authorScale + 40, -1);
        GlStateManager.popMatrix();
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Combo Display", this.width / 2, height / 3 + 20, -1);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Pointing out large bugs and inconsistencies", this.width / 2, this.height / 3 + 60, -1);
	      this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Fixing bugs regarding rendering", this.width / 2, this.height / 3 + 100, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
