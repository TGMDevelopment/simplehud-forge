package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransImageButton;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.listener.GuiListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class GuiMain extends GuiScreen {

    private GuiButton patreon;
    private GuiButton youtube;
    private GuiButton discord;

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Close"));
        this.buttonList.add(new GuiTransButton(1, this.width - 80, 0, 80, 20, "HUD Editor"));
        this.buttonList.add(new GuiTransButton(2, this.width / 2 - 50, this.height / 2 - 60, 100, 20, "Toggle: " + (SimpleHUD.getInstance().isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(3, this.width / 2 - 50, this.height / 2, 100, 20, "Credits"));
        this.buttonList.add(new GuiTransButton(4, this.width / 2 - 50, this.height / 2 - 40, 100, 20, "Pause button: " + (GuiListener.getInstance().mustAddPauseButton() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(5, this.width / 2 - 50, this.height / 2 - 20, 100, 20, "Show in chat: " + (SimpleHUD.getInstance().getElementManager().isShowInChat() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));

        this.buttonList.add(patreon = new GuiTransImageButton(100, 10, this.height - 30, 30, 30, new ResourceLocation("simplehud", "textures/patreon.png")));
        this.buttonList.add(youtube = new GuiTransImageButton(101, 40, this.height - 30, 30, 30, new ResourceLocation("simplehud", "textures/youtube.png")));
        this.buttonList.add(discord = new GuiTransImageButton(102, 70, this.height - 30, 30, 30, new ResourceLocation("simplehud", "textures/discord.png")));

        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        final JSONObject mainConfigObj = new JSONObject();
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new GuiConfiguration(this));
                break;
            case 2:
                SimpleHUD.getInstance().setToggled(!SimpleHUD.getInstance().isToggled());

                mainConfigObj.put("full_toggle", SimpleHUD.getInstance().isToggled());
                mainConfigObj.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
                mainConfigObj.put("show_in_chat", SimpleHUD.getInstance().getElementManager().isShowInChat());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, mainConfigObj);

                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
            case 3:
                Minecraft.getMinecraft().displayGuiScreen(new GuiCredits(this));
                break;
            case 4:
                GuiListener.getInstance().setAddPauseButton(!GuiListener.getInstance().mustAddPauseButton());

                mainConfigObj.put("full_toggle", SimpleHUD.getInstance().isToggled());
                mainConfigObj.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
                mainConfigObj.put("show_in_chat", SimpleHUD.getInstance().getElementManager().isShowInChat());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, mainConfigObj);

                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
            case 5:
                SimpleHUD.getInstance().getElementManager().setShowInChat(!SimpleHUD.getInstance().getElementManager().isShowInChat());

                mainConfigObj.put("full_toggle", SimpleHUD.getInstance().isToggled());
                mainConfigObj.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
                mainConfigObj.put("show_in_chat", SimpleHUD.getInstance().getElementManager().isShowInChat());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, mainConfigObj);

                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
            case 100:
                Desktop.getDesktop().browse(this.URLtoURI(new URL("https://patreon.com/MatthewTGM")));
                break;
            case 101:
                Desktop.getDesktop().browse(this.URLtoURI(new URL("https://youtube.com/MatthewTGM")));
                break;
            case 102:
                Desktop.getDesktop().browse(this.URLtoURI(new URL("https://discord.gg/7BUb7Qu")));
        }
        super.actionPerformed(button);
    }

    private URI URLtoURI(URL url) {
        try {
            return url.toURI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.renderHoverText(mouseX, mouseY);
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD", width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void renderHoverText(int mouseX, int mouseY) {
        if ((mouseX >= patreon.xPosition && mouseX <= patreon.width + patreon.xPosition) && (mouseY >= patreon.yPosition && mouseY <= patreon.height + patreon.yPosition)) {
            this.fontRendererObj.drawString(EnumChatFormatting.GOLD + "Patreon", patreon.xPosition - 3, patreon.yPosition - 10, -1);
        }
        if ((mouseX >= youtube.xPosition && mouseX <= youtube.width + youtube.xPosition) && (mouseY >= youtube.yPosition && mouseY <= youtube.height + youtube.yPosition)) {
            this.fontRendererObj.drawString(EnumChatFormatting.RED + "YouTube", youtube.xPosition - 4, youtube.yPosition - 10, -1);
        }
        if ((mouseX >= discord.xPosition && mouseX <= discord.width + discord.xPosition) && (mouseY >= discord.yPosition && mouseY <= discord.height + discord.yPosition)) {
            this.fontRendererObj.drawString(EnumChatFormatting.LIGHT_PURPLE + "Discord", discord.xPosition - 3, discord.yPosition - 10, -1);
        }
    }

}