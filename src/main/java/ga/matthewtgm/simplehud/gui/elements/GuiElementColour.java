package ga.matthewtgm.simplehud.gui.elements;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.lib.gui.GuiTransSlider;
import ga.matthewtgm.lib.util.GuiScreenUtils;
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

public class GuiElementColour extends GuiScreen {

    protected GuiButton chromaToggle;
    protected GuiSlider rSlider;
    protected GuiSlider gSlider;
    protected GuiSlider bSlider;
    private final Element element;
    private GuiScreen parent;

    public GuiElementColour(Element element, GuiScreen parent) {
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
        this.buttonList.add(chromaToggle = new GuiTransButton(1, this.width / 2 - 105, this.height / 2 - 100, 100, 20, "Chroma: " + (this.element.isChroma() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(rSlider = new GuiTransSlider(2, this.width / 2 + 5, this.height / 2 - 100, 100, 20, "Red: ", "", 1, 255, this.element.colour.getR(), false, true));
        this.buttonList.add(gSlider = new GuiTransSlider(3, this.width / 2 - 105, this.height / 2 - 70, 100, 20, "Green: ", "", 1, 255, this.element.colour.getG(), false, true));
        this.buttonList.add(bSlider = new GuiTransSlider(4, this.width / 2 + 5, this.height / 2 - 70, 100, 20, "Blue: ", "", 1, 255, this.element.colour.getB(), false, true));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.getParent());
                break;
            case 1:
                this.element.setChroma(!this.element.isChroma());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Chroma: " + (this.element.isChroma() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                break;
            case 2:
                this.element.colour.setR(this.rSlider.getValueInt());
                break;
            case 3:
                this.element.colour.setG(this.gSlider.getValueInt());
                break;
            case 4:
                this.element.colour.setB(this.bSlider.getValueInt());
                break;
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.element.colour.setR(this.rSlider.getValueInt());
        this.element.colour.setG(this.gSlider.getValueInt());
        this.element.colour.setB(this.bSlider.getValueInt());
    }

    @Override
    public void onGuiClosed() {
        for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
            element.onSave(new JSONObject());
            element.onLoad();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.element.onRendered(new ElementPosition((int) (this.width / 2 - 30 / this.element.getPosition().getScale()), 0, this.element.getPosition().getScale()));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}