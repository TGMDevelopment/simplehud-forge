package ga.matthewtgm.simplehud.elements;

import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.enums.Colour;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleButton;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("all")
public class Element {

    private String name;

    //CONFIG
    private ElementPosition position;
    private boolean toggle;
    public Colour colour;
    private boolean renderBrackets;
    private String renderedValue;
    private boolean textShadow;
    protected boolean background;
    public Colour backgroundColor;
    public boolean backgroundTransparent;

    //INDIVIDUAL ELEMENT VARIABLES
    public String prefix;
    public ElementGUI elementScreen = new ElementGUI(new GuiConfiguration(SimpleHUD.getInstance().configGui), this){};

    //REQUIRED FOR DRAGGABLE HUD
    public int width, height;

    //VARIABLES USED ACROSS ELEMENTS
    protected Logger logger;
    protected final Minecraft mc = Minecraft.getMinecraft();

    public Element(String name) {
        this.name = name;
        this.logger = LogManager.getLogger(Constants.NAME + " (" + name + ")");

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(name, SimpleHUD.getFileHandler().elementDir) == null;
        final FileHandler handler = SimpleHUD.getFileHandler();

        if (isConfigFileNull) this.toggle = false;
        else this.toggle = (boolean) handler.load(name, SimpleHUD.getFileHandler().elementDir).get("toggle");

        if (isConfigFileNull) this.textShadow = true;
        else this.textShadow = (boolean) handler.load(name, handler.elementDir).get("text_shadow");

        if (isConfigFileNull) this.background = false;
        else
            this.background = (boolean) ((JSONObject) handler.load(name, handler.elementDir).get("background")).get("toggle");

        if (isConfigFileNull) this.backgroundTransparent = true;
        else
            this.backgroundTransparent = (boolean) ((JSONObject) handler.load(name, handler.elementDir).get("background")).get("transparent");

        if (isConfigFileNull) this.backgroundColor = Colour.WHITE;
        else {
            String colourAsString = String.valueOf(((JSONObject) handler.load(name, SimpleHUD.getFileHandler().elementDir).get("background")).get("colour"));
            this.colour = Colour.valueOf(colourAsString.toUpperCase().replaceAll(" ", "_"));
        }

        if (this.position == null) this.position = new ElementPosition(
                isConfigFileNull ?
                        10 :
                        Integer.parseInt(String.valueOf(((JSONObject) handler.load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("x"))),
                isConfigFileNull ?
                        10 :
                        Integer.parseInt(String.valueOf(((JSONObject) handler.load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("y"))),
                isConfigFileNull ?
                        1 :
                        Integer.parseInt(String.valueOf(((JSONObject) handler.load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("scale"))));

        try {
            if (this.colour == null && isConfigFileNull) this.colour = Colour.WHITE;
            else {
                String colourAsString = String.valueOf(handler.load(name, SimpleHUD.getFileHandler().elementDir).get("colour"));
                this.colour = Colour.valueOf(colourAsString.toUpperCase().replaceAll(" ", "_"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setup();
    }

    private void setup() {
        try {
            this.onSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSetup() {
        try {
            this.onSave(new JSONObject());
            this.onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEnabled() {
    }

    public void onDisabled() {
    }

    public String getRenderedString() {
        String value = new String();
        if (this.shouldRenderBrackets()) value += "[";
        if (this.prefix != null)
            value += this.prefix + ": ";
        value += this.getRenderedValue();
        if (this.shouldRenderBrackets()) value += "]";
        return value;
    }

    public void onRendered(ElementPosition position) {
        if (this.background && this.backgroundColor != null)
            Gui.drawRect(this.getPosition().getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.setTransparency(backgroundTransparent ? 35 : 255));
        GlStateManager.pushMatrix();
        GlStateManager.scale(this.getPosition().getScale(), this.getPosition().getScale(), 1);
        this.mc.fontRendererObj.drawString(this.getRenderedString(), position.getX() / position.getScale(), position.getY() / position.getScale(), this.colour.getHex(), this.getTextShadow());
        this.width = (int) (this.mc.fontRendererObj.getStringWidth(this.getRenderedString()) * this.getPosition().getScale());
        GlStateManager.popMatrix();
    }

    public void onSave(JSONObject obj) {
        obj.put("toggle", this.toggle);
        final JSONObject posObj = new JSONObject();
        posObj.put("x", this.position.getX());
        posObj.put("y", this.position.getY());
        posObj.put("scale", this.position.getScale());
        obj.put("position", posObj);
        obj.put("colour", this.colour.getName());
        obj.put("show_brackets", this.shouldRenderBrackets());
        obj.put("text_shadow", this.getTextShadow());
        final JSONObject backgroundObj = new JSONObject();
        backgroundObj.put("toggle", this.background);
        backgroundObj.put("colour", this.backgroundColor == null ? Colour.WHITE.getName() : this.backgroundColor.getName());
        backgroundObj.put("transparent", this.backgroundTransparent);
        obj.put("background", backgroundObj);
        SimpleHUD.getFileHandler().save(this.getName(), SimpleHUD.getFileHandler().elementDir, obj);
    }

    public void onLoad() {
        final JSONObject obj = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir);
        final JSONObject posObj = (JSONObject) obj.get("position");
        this.toggle = (boolean) obj.get("toggle");
        this.position.setPosition(Integer.parseInt(String.valueOf(posObj.get("x"))), Integer.parseInt(String.valueOf(posObj.get("y"))));
        this.colour = Colour.valueOf(String.valueOf(obj.get("colour")).toUpperCase().replaceAll(" ", "_"));
        this.renderBrackets = (boolean) obj.get("show_brackets");
        this.textShadow = (boolean) obj.get("text_shadow");
        this.background = (boolean) ((JSONObject) obj.get("background")).get("toggle");
        this.backgroundColor = Colour.valueOf(String.valueOf(((JSONObject) obj.get("background")).get("colour")).toUpperCase().replaceAll(" ", "_"));
        this.backgroundTransparent = (boolean) ((JSONObject) obj.get("background")).get("transparent");
    }

    public String getName() {
        return name;
    }

    public ElementPosition getPosition() {
        return position;
    }

    public boolean isToggled() {
        return toggle;
    }

    public boolean shouldRenderBrackets() {
        return renderBrackets;
    }

    public String getRenderedValue() {
        return renderedValue;
    }

    public boolean getTextShadow() {
        return textShadow;
    }

    protected void setRenderedValue(String renderedValue) {
        this.renderedValue = renderedValue;
    }

    public void setRenderBrackets(boolean renderBrackets) {
        this.renderBrackets = renderBrackets;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public void setTextShadow(boolean textShadow) {
        this.textShadow = textShadow;
    }

    public void setBackgroundToggle(boolean background) {
        this.background = background;
    }

    public abstract static class ElementGUI extends GuiScreen {

        protected final Element element;
        protected final GuiScreen parent;

        public ElementGUI(GuiScreen parent, Element element) {
            this.parent = parent;
            this.element = element;
        }

        public ElementGUI(Element element) {
            this.parent = null;
            this.element = element;
        }

        private GuiSlider scaleSlider;

        @Override
        public void initGui() {
            this.buttonList.add(new GuiSimpleButton(0, 0, 0, "Back"));
            this.buttonList.add(new GuiSimpleButton(1, 0, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            this.buttonList.add(new GuiSimpleButton(2, 0, 40, "Show Brackets: " + (this.element.shouldRenderBrackets() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            this.buttonList.add(new GuiSimpleButton(3, 0, 60, "Colour: " + this.element.colour.getAsMCColour(this.element.colour) + this.element.colour.getName().toLowerCase()));
            this.buttonList.add(new GuiSimpleButton(4, 0, 80, "Text Shadow: " + (this.element.getTextShadow() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            this.buttonList.add(scaleSlider = new GuiSimpleSlider(5, 0, 100, 200, 20, "Scale: ", "", 1, 5, this.element.getPosition().getScale(), false, true));
            this.buttonList.add(new GuiSimpleButton(6, 0, 120, "Background: " + (this.element.background ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            this.buttonList.add(new GuiSimpleButton(7, 0, 140, "Background Colour: " + this.element.backgroundColor.getAsMCColour(this.element.backgroundColor) + this.element.backgroundColor.getName().toLowerCase()));
            this.buttonList.add(new GuiSimpleButton(8, 0, 160, "Background Transparent: " + (this.element.backgroundTransparent ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
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
                this.element.colour = this.element.colour.getNextColor(this.element.colour);
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            if (button.id == 4) {
                this.element.setTextShadow(!this.element.getTextShadow());
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            if (button.id == 5) {
                this.element.getPosition().setScale(this.scaleSlider.getValueInt());
            }
            if (button.id == 6) {
                this.element.setBackgroundToggle(!this.element.background);
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            if (button.id == 7) {
                this.element.backgroundColor = this.element.backgroundColor.getNextColor(this.element.backgroundColor);
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            if (button.id == 8) {
                this.element.backgroundTransparent = !this.element.backgroundTransparent;
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
            this.element.onRendered(new ElementPosition(this.width / 2 / this.element.getPosition().getScale(), 0, this.element.getPosition().getScale()));
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        @Override
        public void onGuiClosed() {
            for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
                element.onSave(new JSONObject());
                element.onLoad();
            }
        }

    }

}