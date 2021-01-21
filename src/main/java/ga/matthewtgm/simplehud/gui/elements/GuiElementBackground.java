package ga.matthewtgm.simplehud.gui.elements;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransSlider;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiElementBackground extends GuiScreen {

    protected GuiSlider bgRSlider;
    protected GuiSlider bgGSlider;
    protected GuiSlider bgBSlider;
    protected GuiSlider bgASlider;
    private final Element element;
    private GuiScreen parent;

    public GuiElementBackground(Element element, GuiScreen parent) {
        this.element = element;
        this.parent = parent;
    }

    public Element getElement() {
        return element;
    }

    public GuiScreen getParent() {
        return parent;
    }

    public void setParent(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Save and go back"));
        this.buttonList.add(new GuiTransButton(1, this.width / 2 - 105, this.height / 2 - 100, 100, 20, "Background: " + (this.element.getBackground() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(bgRSlider = new GuiTransSlider(2, this.width / 2 + 5, this.height / 2 - 100, 100, 20, "BG Red: ", "", 1, 255, this.element.backgroundColor.getR(), false, true));
        this.buttonList.add(bgGSlider = new GuiTransSlider(3, this.width / 2 - 105, this.height / 2 - 70, 100, 20, "BG Green: ", "", 1, 255, this.element.backgroundColor.getG(), false, true));
        this.buttonList.add(bgBSlider = new GuiTransSlider(4, this.width / 2 + 5, this.height / 2 - 70, 100, 20, "BG Blue: ", "", 1, 255, this.element.backgroundColor.getB(), false, true));
        this.buttonList.add(bgASlider = new GuiTransSlider(5, this.width / 2 - 50, this.height / 2 - 40, 100, 20, "BG Alpha: ", "", 1, 255, this.element.backgroundColor.getA(), false, true));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.getParent());
                break;
            case 1:
                this.element.setBackgroundToggle(!this.element.getBackground());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Background: " + (this.element.getBackground() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                break;
            case 2:
                this.element.backgroundColor.setR(this.bgRSlider.getValueInt());
                break;
            case 3:
                this.element.backgroundColor.setG(this.bgGSlider.getValueInt());
                break;
            case 4:
                this.element.backgroundColor.setB(this.bgBSlider.getValueInt());
                break;
            case 5:
                this.element.backgroundColor.setA(this.bgASlider.getValueInt());
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.element.onRendered(new ElementPosition((int) (this.width / 2 - 30 / this.element.getPosition().getScale()), 0, this.element.getPosition().getScale()));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.element.backgroundColor.setR(this.bgRSlider.getValueInt());
        this.element.backgroundColor.setG(this.bgGSlider.getValueInt());
        this.element.backgroundColor.setB(this.bgBSlider.getValueInt());
        this.element.backgroundColor.setA(this.bgASlider.getValueInt());
    }

}