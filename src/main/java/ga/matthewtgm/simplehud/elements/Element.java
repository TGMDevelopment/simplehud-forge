package ga.matthewtgm.simplehud.elements;

import ga.matthewtgm.lib.util.ColourUtils;
import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiConfigurationCategories;
import ga.matthewtgm.simplehud.gui.elements.GuiElement;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

@SuppressWarnings("all")
public class Element {

    private String name;
    public String category;

    //CONFIG
    private ElementPosition position;
    private boolean toggle;
    public ElementColour colour;
    private boolean renderBrackets;
    private String renderedValue;
    private boolean textShadow;
    protected boolean background;
    public ElementColour backgroundColor;
    private boolean showPrefix;
    private boolean chroma;

    //INDIVIDUAL ELEMENT VARIABLES
    public String prefix;
    public GuiElement elementScreen;

    //REQUIRED FOR DRAGGABLE HUD
    public int width, height;

    //VARIABLES USED ACROSS ELEMENTS
    protected Logger logger;
    protected final Minecraft mc = Minecraft.getMinecraft();

    public Element(String name, String category) {
        this.name = name;
        this.category = category;
        this.logger = LogManager.getLogger(Constants.NAME + " (" + name + ")");

        if(this.category.equals("PvP")) {
            this.elementScreen = new GuiElement(new GuiConfigurationCategories.GuiConfigurationPvP(new GuiConfiguration(SimpleHUD.getInstance().configGui)), this) {};
        } else if(this.category.equals("General")) {
            this.elementScreen = new GuiElement(new GuiConfigurationCategories.GuiConfigurationGeneral(new GuiConfiguration(SimpleHUD.getInstance().configGui)), this) {};
        }

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(name, SimpleHUD.getFileHandler().elementDir) == null;
        final FileHandler handler = SimpleHUD.getFileHandler();

        if (isConfigFileNull) this.toggle = false;
        else this.toggle = (boolean) handler.load(name, SimpleHUD.getFileHandler().elementDir).get("toggle");

        if (isConfigFileNull) this.textShadow = true;
        else this.textShadow = (boolean) handler.load(name, handler.elementDir).get("text_shadow");

        if(isConfigFileNull) this.renderBrackets = false;
        else this.renderBrackets = (boolean) handler.load(name, handler.elementDir).get("show_brackets");

        if (isConfigFileNull) this.background = false;
        else
            this.background = (boolean) ((JSONObject) handler.load(name, handler.elementDir).get("background")).get("toggle");

        if (isConfigFileNull) this.backgroundColor = new ElementColour(255, 255, 255, 50);
        else {
            JSONObject colourObj = (JSONObject) ((JSONObject) handler.load(name, handler.elementDir).get("background")).get("colour");
            this.backgroundColor = new ElementColour(
                    Integer.parseInt(String.valueOf(colourObj.get("r"))),
                    Integer.parseInt(String.valueOf(colourObj.get("g"))),
                    Integer.parseInt(String.valueOf(colourObj.get("b"))),
                    Integer.parseInt(String.valueOf(colourObj.get("a")))
            );
        }

        if (isConfigFileNull) this.chroma = false;
        else this.chroma = (boolean) handler.load(name, handler.elementDir).get("chroma");

        if (this.position == null) this.position = new ElementPosition(
                isConfigFileNull ?
                        10 :
                        Integer.parseInt(String.valueOf(((JSONObject) handler.load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("x"))),
                isConfigFileNull ?
                        10 :
                        Integer.parseInt(String.valueOf(((JSONObject) handler.load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("y"))),
                isConfigFileNull ?
                        1 :
                        Math.round(Float.parseFloat(String.valueOf(((JSONObject) handler.load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("scale")))));

        try {
            if (this.colour == null && isConfigFileNull) this.colour = new ElementColour(255, 255, 255);
            else {
                JSONObject colourObj = (JSONObject) handler.load(name, handler.elementDir).get("colour");
                this.colour = new ElementColour(
                        Integer.parseInt(String.valueOf(colourObj.get("r"))),
                        Integer.parseInt(String.valueOf(colourObj.get("g"))),
                        Integer.parseInt(String.valueOf(colourObj.get("b")))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isConfigFileNull) this.showPrefix = true;
        else this.showPrefix = (boolean) handler.load(name, handler.elementDir).get("show_prefix");

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
        if (this.prefix != null && this.showPrefix)
            value += this.prefix + ": ";
        value += this.getRenderedValue();
        if (this.shouldRenderBrackets()) value += "]";
        return value;
    }

    public void onRendered(ElementPosition position) {
        if (this.background && this.backgroundColor != null)
            Gui.drawRect(position.getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.getRGBA());
        GlStateManager.pushMatrix();
        GlStateManager.scale(this.getPosition().getScale(), this.getPosition().getScale(), 1);
        this.mc.fontRendererObj.drawString(this.getRenderedString(), position.getX() / position.getScale(), position.getY() / position.getScale(), this.chroma ? ColourUtils.getInstance().chroma() : this.colour.getRGB(), this.getTextShadow());
        this.width = Math.round(this.mc.fontRendererObj.getStringWidth(this.getRenderedString()) * this.getPosition().getScale());
        GlStateManager.popMatrix();
    }

    public void onSave(JSONObject obj) {
        obj.put("toggle", this.toggle);
        final JSONObject posObj = new JSONObject();
        posObj.put("x", this.position.getX());
        posObj.put("y", this.position.getY());
        posObj.put("scale", this.position.getScale());
        obj.put("position", posObj);
        final JSONObject colourObj = new JSONObject();
        colourObj.put("r", this.colour.getR());
        colourObj.put("g", this.colour.getG());
        colourObj.put("b", this.colour.getB());
        obj.put("colour", colourObj);
        obj.put("show_brackets", this.shouldRenderBrackets());
        obj.put("text_shadow", this.getTextShadow());
        final JSONObject backgroundObj = new JSONObject();
        backgroundObj.put("toggle", this.background);
        final JSONObject backgroundColourObj = new JSONObject();
        backgroundColourObj.put("r", this.backgroundColor.getR());
        backgroundColourObj.put("g", this.backgroundColor.getG());
        backgroundColourObj.put("b", this.backgroundColor.getB());
        backgroundColourObj.put("a", this.backgroundColor.getA());
        backgroundObj.put("colour", backgroundColourObj);
        obj.put("background", backgroundObj);
        obj.put("show_prefix", this.showPrefix);
        obj.put("chroma", chroma);
        SimpleHUD.getFileHandler().save(this.getName(), SimpleHUD.getFileHandler().elementDir, obj);
    }

    public void onLoad() {
        final JSONObject obj = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir);
        final JSONObject colourObj = (JSONObject) obj.get("colour");
        final JSONObject posObj = (JSONObject) obj.get("position");
        final JSONObject backgroundObj = (JSONObject) obj.get("background");
        final JSONObject backgroundColourObj = (JSONObject) backgroundObj.get("colour");
        this.toggle = (boolean) obj.get("toggle");
        this.position.setPosition(Integer.parseInt(String.valueOf(posObj.get("x"))), Integer.parseInt(String.valueOf(posObj.get("y"))));
        this.colour = new ElementColour(
                Integer.parseInt(String.valueOf(colourObj.get("r"))),
                Integer.parseInt(String.valueOf(colourObj.get("g"))),
                Integer.parseInt(String.valueOf(colourObj.get("b")))
        );
        this.renderBrackets = (boolean) obj.get("show_brackets");
        this.textShadow = (boolean) obj.get("text_shadow");
        this.background = (boolean) ((JSONObject) obj.get("background")).get("toggle");
        this.backgroundColor = new ElementColour(
                Integer.parseInt(String.valueOf(backgroundColourObj.get("r"))),
                Integer.parseInt(String.valueOf(backgroundColourObj.get("g"))),
                Integer.parseInt(String.valueOf(backgroundColourObj.get("b"))),
                Integer.parseInt(String.valueOf(backgroundColourObj.get("a")))
        );
        this.showPrefix = (boolean) obj.get("show_prefix");
        this.chroma = (boolean) obj.get("chroma");
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

    public boolean getBackground() {
        return background;
    }

    public boolean shouldShowPrefix() {
        return showPrefix;
    }

    public boolean isChroma() {
        return chroma;
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

    public void setShowPrefix(boolean showPrefix) {
        this.showPrefix = showPrefix;
    }

    public void setChroma(boolean chroma) {
        this.chroma = chroma;
    }

}