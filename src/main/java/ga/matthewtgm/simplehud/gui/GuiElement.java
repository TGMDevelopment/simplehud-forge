package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransSlider;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.json.simple.JSONObject;

import java.io.IOException;

public abstract class GuiElement extends GuiScreen {

    protected final Element element;
    protected final GuiScreen parent;

    public GuiElement(GuiScreen parent, Element element) {
        this.parent = parent;
        this.element = element;
    }

    public GuiElement(Element element) {
        this.parent = null;
        this.element = element;
    }

    private GuiSlider scaleSlider;

    private GuiSlider rSlider;
    private GuiSlider gSlider;
    private GuiSlider bSlider;

    private GuiSlider bgRSlider;
    private GuiSlider bgGSlider;
    private GuiSlider bgBSlider;
    private GuiSlider bgASlider;


    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Save and go back"));
        this.buttonList.add(new GuiTransButton(1, this.width / 2 - 105, this.height / 2 - 100, 100, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(2, this.width / 2 + 5, this.height / 2 - 100, 100, 20,"Show Brackets: " + (this.element.shouldRenderBrackets() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(3, this.width / 2 - 105, this.height / 2 - 70, 100, 20, "Text Shadow: " + (this.element.getTextShadow() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(scaleSlider = new GuiTransSlider(4, this.width / 2 + 5, this.height / 2 - 70, 100, 20, "Scale: ", "", 1, 5, this.element.getPosition().getScale(), true, true));
        this.buttonList.add(rSlider = new GuiTransSlider(5, this.width / 2 - 105, this.height / 2 - 40, 100, 20, "Red: ", "", 1, 255, this.element.colour.getR(), false, true));
        this.buttonList.add(gSlider = new GuiTransSlider(6, this.width / 2 + 5, this.height / 2 - 40, 100, 20, "Green: ", "", 1, 255, this.element.colour.getG(), false, true));
        this.buttonList.add(bSlider = new GuiTransSlider(7, this.width / 2 - 105, this.height / 2 - 10, 100, 20, "Blue: ", "", 1, 255, this.element.colour.getB(), false, true));
        this.buttonList.add(new GuiTransButton(8, this.width / 2 + 5, this.height / 2 - 10, 100, 20, "Background: " + (this.element.getBackground() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(bgRSlider = new GuiTransSlider(9, this.width / 2 - 105, this.height / 2 + 20, 100, 20, "BG Red: ", "", 1, 255, this.element.backgroundColor.getR(), false, true));
        this.buttonList.add(bgGSlider = new GuiTransSlider(10, this.width / 2 + 5, this.height / 2 + 20, 100, 20, "BG Green: ", "", 1, 255, this.element.backgroundColor.getG(), false, true));
        this.buttonList.add(bgBSlider = new GuiTransSlider(11, this.width / 2 - 105, this.height / 2 + 50, 100, 20, "BG Blue: ", "", 1, 255, this.element.backgroundColor.getB(), false, true));
        this.buttonList.add(bgASlider = new GuiTransSlider(12, this.width / 2 + 5, this.height / 2 + 50, 100, 20, "BG Alpha: ", "", 1, 255, this.element.backgroundColor.getA(), false, true));
        this.buttonList.add(new GuiTransButton(13, this.width / 2 - 105, this.height / 2 + 80, 100, 20, "Show Prefix: " + (this.element.shouldShowPrefix() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(new GuiTransButton(14, this.width / 2 + 5, this.height / 2 + 80, 100, 20, "Chroma: " + (this.element.isChroma() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
        if (button.id == 1) {
            this.element.setToggle(!this.element.isToggled());
            Minecraft.getMinecraft().displayGuiScreen(this);
        }
        if (button.id == 2) {
            this.element.setRenderBrackets(!this.element.shouldRenderBrackets());
            Minecraft.getMinecraft().displayGuiScreen(this);
        }
        if (button.id == 3) {
            this.element.setTextShadow(!this.element.getTextShadow());
            Minecraft.getMinecraft().displayGuiScreen(this);
        }
        if(button.id == 4) this.element.getPosition().setScale((float) this.scaleSlider.getValue());
        if(button.id == 5) this.element.colour.setR(this.rSlider.getValueInt());
        if(button.id == 6) this.element.colour.setG(this.gSlider.getValueInt());
        if(button.id == 7) this.element.colour.setB(this.bSlider.getValueInt());
        if (button.id == 8) {
            this.element.setBackgroundToggle(!this.element.getBackground());
            Minecraft.getMinecraft().displayGuiScreen(this);
        }
        if(button.id == 9) this.element.backgroundColor.setR(this.bgRSlider.getValueInt());
        if(button.id == 10) this.element.backgroundColor.setG(this.bgGSlider.getValueInt());
        if(button.id == 11) this.element.backgroundColor.setB(this.bgBSlider.getValueInt());
        if(button.id == 12) this.element.backgroundColor.setA(this.bgASlider.getValueInt());
        if(button.id == 13) {
            this.element.setShowPrefix(!this.element.shouldShowPrefix());
            Minecraft.getMinecraft().displayGuiScreen(this);
        }
        if(button.id == 14) {
            this.element.setChroma(!this.element.isChroma());
            Minecraft.getMinecraft().displayGuiScreen(this);
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
        this.element.onRendered(new ElementPosition((int) (this.width / 2 - 30 / this.element.getPosition().getScale()), 0, this.element.getPosition().getScale()));
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
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.element.getPosition().setScale((float) this.scaleSlider.getValue());
        this.element.colour.setR(this.rSlider.getValueInt());
        this.element.colour.setG(this.gSlider.getValueInt());
        this.element.colour.setB(this.bSlider.getValueInt());
        this.element.backgroundColor.setR(this.bgRSlider.getValueInt());
        this.element.backgroundColor.setG(this.bgGSlider.getValueInt());
        this.element.backgroundColor.setB(this.bgBSlider.getValueInt());
        this.element.backgroundColor.setA(this.bgASlider.getValueInt());
    }

}