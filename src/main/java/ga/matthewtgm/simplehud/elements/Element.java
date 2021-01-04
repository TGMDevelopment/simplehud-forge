package ga.matthewtgm.simplehud.elements;

import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.enums.Colour;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Element {

    private String name;
    private ElementPosition position;
    private boolean toggle;
    public Colour colour;
    private boolean renderBrackets;
    private String renderedValue;

    //INDIVIDUAL ELEMENT VARIABLES
    public String prefix;
    public GuiScreen elementScreen;

    //REQUIRED FOR DRAGGABLE HUD
    public int width, height;

    //VARIABLES USED ACROSS ELEMENTS
    protected Logger logger;
    protected final Minecraft mc = Minecraft.getMinecraft();

    public Element(String name) {
        this.name = name;
        this.logger = LogManager.getLogger(Constants.NAME + " (" + name + ")");

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(name, SimpleHUD.getFileHandler().elementDir) == null;

        if(isConfigFileNull) this.toggle = false;
        else this.toggle = (boolean) SimpleHUD.getFileHandler().load(name, SimpleHUD.getFileHandler().elementDir).get("toggle");

        if(this.position == null) this.position = new ElementPosition(
                isConfigFileNull ?
                        10 :
                        Integer.parseInt(String.valueOf(((JSONObject) SimpleHUD.getFileHandler().load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("x"))),
                isConfigFileNull ?
                        10 :
                        Integer.parseInt(String.valueOf(((JSONObject) SimpleHUD.getFileHandler().load(this.name, SimpleHUD.getFileHandler().elementDir).get("position")).get("y"))));

        try {
            if(this.colour == null && isConfigFileNull) this.colour = Colour.WHITE;
            else {
                String colourAsString = String.valueOf(SimpleHUD.getFileHandler().load(name, SimpleHUD.getFileHandler().elementDir).get("colour"));
                this.colour = Colour.valueOf(colourAsString.toUpperCase().replaceAll(" ", "_"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.setup();
    }

    private void setup() {
        try {
            this.onSave();
            this.onSetup();
            this.onLoad();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void onSetup() {}

    public void onEnabled() {}

    public void onDisabled() {}

    public String getRenderedString() {return null;}

    public void onRendered() {}

    public void onSave() {
        final JSONObject obj = new JSONObject();
        obj.put("toggle", this.toggle);
        final JSONObject posObj = new JSONObject();
        posObj.put("x", this.position.getX());
        posObj.put("y", this.position.getY());
        obj.put("position", posObj);
        obj.put("colour", this.colour.getName());
        obj.put("show_brackets", this.shouldRenderBrackets());
        SimpleHUD.getFileHandler().save(this.getName(), SimpleHUD.getFileHandler().elementDir, obj);
    }

    public void onLoad() {
        final JSONObject obj = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir);
        final JSONObject posObj = (JSONObject) obj.get("position");
        this.toggle = (boolean) obj.get("toggle");
        this.position.setPosition(Integer.parseInt(String.valueOf(posObj.get("x"))), Integer.parseInt(String.valueOf(posObj.get("y"))));
        this.colour = Colour.valueOf(String.valueOf(obj.get("colour")).toUpperCase().replaceAll(" ", "_"));
        this.renderBrackets = (boolean) obj.get("show_brackets");
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

    protected void setRenderedValue(String renderedValue) {
        this.renderedValue = renderedValue;
    }

    public void setRenderBrackets(boolean renderBrackets) {
        this.renderBrackets = renderBrackets;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public static class ElementGUI extends GuiScreen {

        private final Element element;
        private final GuiScreen parent;

        public ElementGUI(GuiScreen parent, Element element) {
            this.parent = parent;
            this.element = element;
        }

        public ElementGUI(Element element) {
            this.parent = null;
            this.element = element;
        }

        @Override
        public void initGui() {
            this.buttonList.add(new GuiSimpleButton(0, 0, 0, "Back"));
            this.buttonList.add(new GuiSimpleButton(1, 0, 20, "Toggle: " + (this.element.isToggled() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            this.buttonList.add(new GuiSimpleButton(2, 0, 40, "Show Brackets: " + (this.element.shouldRenderBrackets() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
            this.buttonList.add(new GuiSimpleButton(3, 0, 60, "Change Colour: " + this.element.colour.getAsMCColour(this.element.colour) + this.element.colour.getName().toLowerCase()));
            super.initGui();
        }

        @Override
        protected void actionPerformed(GuiButton button) throws IOException {
            if(button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
            if(button.id == 1) {
                this.element.setToggle(!this.element.isToggled());
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            if(button.id == 2) {
                this.element.setRenderBrackets(!this.element.shouldRenderBrackets());
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            if(button.id == 3) {
                this.element.colour = this.element.colour.getNextColor(this.element.colour);
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
            this.fontRendererObj.drawString(this.element.getRenderedString(), this.width / 2, 10, this.element.colour.getHex());
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        @Override
        public void onGuiClosed() {
            for(Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
                element.onSave();
                element.onLoad();
            }
        }
    }

}