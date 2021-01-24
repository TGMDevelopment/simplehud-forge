package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.util.RenderUtils;
import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.utils.GuiScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class GuiConfiguration extends GuiScreen {

    private final Logger logger = LogManager.getLogger(Constants.NAME + " (" + this.getClass().getSimpleName() + ")");
    private final GuiScreen parent;
    private boolean dragging;
    private Optional<Element> selectedElement = Optional.empty();

    private GuiButton selectedButton;

    private int prevX, prevY;

    private List<GuiButton> buttons;

    public GuiConfiguration(GuiScreen parent) {
        this.parent = parent;
    }

    private void setButtons() {
        this.buttons = Arrays.asList(
                new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Save and close" : "Save and go back"),
                new GuiTransButton(1, this.width / 2 - 50, this.height / 2 - 30, 100, 20, "PvP"),
                new GuiTransButton(2, this.width / 2 - 50, this.height / 2, 100, 20, "General")
        );
    }

    @Override
    public void initGui() {
        this.setButtons();
        this.buttonList.addAll(buttons);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
        if (button.id == 1) Minecraft.getMinecraft().displayGuiScreen(new GuiConfigurationCategories.GuiConfigurationPvP(this));
        if (button.id == 2) Minecraft.getMinecraft().displayGuiScreen(new GuiConfigurationCategories.GuiConfigurationGeneral(this));
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.updateElementPosition(mouseX, mouseY);
        for (Element e : SimpleHUD.getInstance().getElementManager().getElements()) {
            if (e.isToggled()) {
                this.fontRendererObj.drawString(e.getName(), e.getPosition().getX(), e.getPosition().getY() - 10, -1);
                final RenderUtils utils = new RenderUtils();
                utils.drawHollowRect(e.getPosition().getX() - 1, e.getPosition().getY() - 2, e.width, e.height, -1);
                Gui.drawRect(e.getPosition().getX() - 1, e.getPosition().getY() - 2, e.getPosition().getX() + e.width, e.getPosition().getY() + e.height, new Color(255, 255, 255, 100).getRGB());
                e.onRendered(e.getPosition());
            }
        }
        GuiScreenUtils.getInstance().slideGuiTitleIntoScreen(this, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD" + EnumChatFormatting.WHITE + " - " + EnumChatFormatting.RED + "HUD Editor");
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
            element.onSave(new JSONObject());
            element.onLoad();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;
        this.selectedElement = SimpleHUD.getInstance().getElementManager().getElements().stream().filter(new MouseHoveringElement(mouseX, mouseY)).findFirst();

        if (selectedElement.isPresent()) {
            if (!selectedElement.get().isToggled()) return;
            this.dragging = true;
        }

        if (mouseButton == 0 && !this.selectedElement.isPresent()) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                        break;
                    guibutton = event.button;
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }

    protected void updateElementPosition(int x, int y) {
        if (selectedElement.isPresent() && this.dragging) {
            Element element = selectedElement.get();
            ElementPosition position = element.getPosition();
            position.setPosition(position.getX() + x - this.prevX, position.getY() + y - this.prevY);
        }
        this.prevX = x;
        this.prevY = y;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static class MouseHoveringElement implements Predicate<Element> {

        private final int x, y;

        public MouseHoveringElement(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public boolean test(Element element) {
            ElementPosition position = element.getPosition();
            int posX = position.x;
            int posY = position.y;
            if (x >= posX && x <= posX + element.width) {
                if (y >= posY && y <= posY + element.height) {
                    return element.isToggled();
                }
            }
            return false;
        }
    }

}