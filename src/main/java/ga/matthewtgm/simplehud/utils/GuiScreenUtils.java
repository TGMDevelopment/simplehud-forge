package ga.matthewtgm.simplehud.utils;

import ga.matthewtgm.lib.util.FontRendererUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class GuiScreenUtils {

    private static GuiScreenUtils INSTANCE;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fontRendererObj = mc.fontRendererObj;
    private double slideYPos = 2D;
    private GuiScreen oldScreen;

    public static GuiScreenUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GuiScreenUtils();
        return INSTANCE;
    }

    public void slideGuiTitleIntoScreen(GuiScreen screen, String title) {
        if (this.oldScreen != screen) {
            this.oldScreen = screen;
            this.slideYPos = 2D;
        }
        int scale = 3;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        FontRendererUtils.getInstance().drawCenteredString(this.fontRendererObj, title, screen.width / 2 / scale, slideYPos, -1);
        GlStateManager.popMatrix();
        if (this.slideYPos < (5 / scale + 10)) {
            slideYPos = slideYPos + 2D;
        }
    }

}