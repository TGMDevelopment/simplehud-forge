package ga.matthewtgm.simplehud.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class GuiScreenUtils {

    private static GuiScreenUtils INSTANCE;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fontRendererObj = mc.fontRendererObj;
    private double slideYPos = 0.0001D;
    private GuiScreen oldScreen;

    public static GuiScreenUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GuiScreenUtils();
        return INSTANCE;
    }

    public void slideGuiTitleIntoScreen(GuiScreen screen, String title) {
        if (this.oldScreen != screen) {
            this.oldScreen = screen;
            this.slideYPos = 0.0001D;
        }
        int scale = 3;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        screen.drawCenteredString(this.fontRendererObj, title, screen.width / 2 / scale, (int) Math.round(slideYPos), -1);
        while (this.slideYPos < (5 / scale + 10)) {
            slideYPos = slideYPos + 0.0001D;
        }
        //if (this.slideYPos >= (5 / scale + 10)) this.slideYPos = 1;
        GlStateManager.popMatrix();
    }

}