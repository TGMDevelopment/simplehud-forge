package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.listener.GuiListener;
import ga.matthewtgm.simplehud.utils.GuiScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.json.simple.JSONObject;

import java.io.IOException;

public class GuiModConfiguration extends GuiScreen {

    private GuiScreen parent;

    public GuiModConfiguration(GuiScreen parent) {
        this.parent = parent;
    }

    public GuiScreen getParent() {
        return parent;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Save and go back"));
        this.buttonList.add(new GuiTransButton(1, this.width / 2 - 50, this.height / 2 - 60, 100, 20, "Toggle: " + (SimpleHUD.getInstance().isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(2, this.width / 2 - 50, this.height / 2 - 40, 100, 20, "Pause button: " + (GuiListener.getInstance().mustAddPauseButton() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(3, this.width / 2 - 50, this.height / 2 - 20, 100, 20, "Show in chat: " + (SimpleHUD.getInstance().getElementManager().isShowInChat() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        final JSONObject mainConfigObj = new JSONObject();
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.getParent());
                break;
            case 1:
                SimpleHUD.getInstance().setToggled(!SimpleHUD.getInstance().isToggled());

                mainConfigObj.put("full_toggle", SimpleHUD.getInstance().isToggled());
                mainConfigObj.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
                mainConfigObj.put("show_in_chat", SimpleHUD.getInstance().getElementManager().isShowInChat());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, mainConfigObj);

                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
            case 2:
                GuiListener.getInstance().setAddPauseButton(!GuiListener.getInstance().mustAddPauseButton());

                mainConfigObj.put("full_toggle", SimpleHUD.getInstance().isToggled());
                mainConfigObj.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
                mainConfigObj.put("show_in_chat", SimpleHUD.getInstance().getElementManager().isShowInChat());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, mainConfigObj);

                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
            case 3:
                SimpleHUD.getInstance().getElementManager().setShowInChat(!SimpleHUD.getInstance().getElementManager().isShowInChat());

                mainConfigObj.put("full_toggle", SimpleHUD.getInstance().isToggled());
                mainConfigObj.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
                mainConfigObj.put("show_in_chat", SimpleHUD.getInstance().getElementManager().isShowInChat());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, mainConfigObj);

                Minecraft.getMinecraft().displayGuiScreen(this);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GuiScreenUtils.getInstance().slideGuiTitleIntoScreen(this, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD " + EnumChatFormatting.WHITE + "- " + EnumChatFormatting.DARK_AQUA + "Config");
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}