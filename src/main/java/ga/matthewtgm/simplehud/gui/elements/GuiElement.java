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
import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class GuiElement extends GuiScreen {

    protected final Element element;
    protected final GuiScreen parent;
    protected GuiButton backgroundButton;
    protected GuiButton colourButton;
    protected GuiButton showBrackets;
    protected GuiButton textShadow;
    protected GuiButton showPrefix;
    protected GuiSlider scaleSlider;
    public GuiElement(GuiScreen parent, Element element) {
        this.parent = parent;
        this.element = element;
    }
    public GuiElement(Element element) {
        this.parent = null;
        this.element = element;
    }

    @Override
    public void initGui() {
        final DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, "Save and go back"));
        this.buttonList.add(new GuiTransButton(1, this.width / 2 - 105, this.height / 2 - 100, 100, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(showBrackets = new GuiTransButton(2, this.width / 2 + 5, this.height / 2 - 100, 100, 20, "Show Brackets: " + (this.element.shouldRenderBrackets() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(textShadow = new GuiTransButton(3, this.width / 2 - 105, this.height / 2 - 70, 100, 20, "Text Shadow: " + (this.element.getTextShadow() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
        this.buttonList.add(scaleSlider = new GuiTransSlider(4, this.width / 2 + 5, this.height / 2 - 70, 100, 20, "Scale: ", "", 1, 5, Double.parseDouble(df.format((this.element.getPosition().getScale() * 10) / 10f)), true, true));
        this.buttonList.add(colourButton = new GuiTransButton(5, this.width / 2 - 105, this.height / 2 - 40, 100, 20, "Colour"));
        this.buttonList.add(backgroundButton = new GuiTransButton(6, this.width / 2 + 5, this.height / 2 - 40, 100, 20, "Background"));
        this.buttonList.add(showPrefix = new GuiTransButton(7, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "Show Prefix: " + (this.element.shouldShowPrefix() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(this.parent);
                break;
            case 1:
                this.element.setToggle(!this.element.isToggled());
                if(this.element.isToggled()) this.element.onEnabled();
                else this.element.onDisabled();
                GuiScreenUtils.getInstance().fixDisplayText(button, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                break;
            case 2:
                this.element.setRenderBrackets(!this.element.shouldRenderBrackets());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Show Brackets: " + (this.element.shouldRenderBrackets() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                break;
            case 3:
                this.element.setTextShadow(!this.element.getTextShadow());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Text Shadow: " + (this.element.getTextShadow() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
                break;
            case 4:
                this.element.getPosition().setScale((float) this.scaleSlider.getValue());
                break;
            case 5:
                Minecraft.getMinecraft().displayGuiScreen(new GuiElementColour(this.element, this));
                break;
            case 6:
                Minecraft.getMinecraft().displayGuiScreen(new GuiElementBackground(this.element, this));
                break;
            case 7:
                this.element.setShowPrefix(!this.element.shouldShowPrefix());
                GuiScreenUtils.getInstance().fixDisplayText(button, "Show Prefix: " + (this.element.shouldShowPrefix() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
        }
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
    }

}