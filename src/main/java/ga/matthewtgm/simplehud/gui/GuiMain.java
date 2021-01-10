package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.json.simple.JSONObject;

import java.io.IOException;

public class GuiMain extends GuiScreen {

    @Override
    public void initGui() {
        this.buttonList.add(new GuiSimpleButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Close"));
        this.buttonList.add(new GuiSimpleButton(1, this.width - 80, 0, 80, 20, "HUD Editor"));
        this.buttonList.add(new GuiSimpleButton(2, this.width / 2 - 50, this.height / 2 - 60, 100, 20, "Toggle: " + (SimpleHUD.getInstance().isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiSimpleButton(3, this.width / 2 - 50, this.height / 2 - 40, 100, 20, "Credits"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new GuiConfiguration(this));
                break;
            case 2:
                SimpleHUD.getInstance().setToggled(!SimpleHUD.getInstance().isToggled());

                final JSONObject object = new JSONObject();
                object.put("full_toggle", SimpleHUD.getInstance().isToggled());
                SimpleHUD.getFileHandler().save("main", SimpleHUD.getFileHandler().modDir, object);

                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
            case 3:
                Minecraft.getMinecraft().displayGuiScreen(new GuiCredits(this));
        }
        super.actionPerformed(button);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GlStateManager.pushMatrix();
        int scale = 3;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(this.fontRendererObj, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD", width / 2 / scale, 5 / scale + 10, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}