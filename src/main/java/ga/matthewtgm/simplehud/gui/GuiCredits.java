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
        switch(button.id) {
            case 0:
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
        int scale2 = 2;
        GlStateManager.scale(scale2, scale2, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.GREEN + "Credits", width / 2 / scale2,  5 / scale2 + 30, -1);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        int authorScale = 2;
        GlStateManager.scale(authorScale, authorScale, 0);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + "Shoddy", this.width / 2 / authorScale, this.height / 3 / authorScale + 10, -1);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.LIGHT_PURPLE + "Moulberry", this.width / 2 / authorScale, this.height / 3 / authorScale + 30, -1);
        this.drawCenteredString(this.fontRendererObj, "Filip", this.width / 2 / authorScale, this.height / 3 / authorScale + 50, -1);
        GlStateManager.popMatrix();
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Pointing out large bugs and inconsistencies", this.width / 2, height / 3 + 40, -1);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Fixing bugs regarding rendering", this.width / 2, this.height / 3 + 80, -1);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Player View element", this.width / 2, this.height / 3 + 120, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}