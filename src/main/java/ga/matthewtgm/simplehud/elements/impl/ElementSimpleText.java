package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiElement;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import net.minecraft.client.gui.GuiTextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ElementSimpleText extends Element {

    protected String text = "Placeholder text";
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public ElementSimpleText() {
        super("Simple Text");

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir) == null;
        final FileHandler handler = SimpleHUD.getFileHandler();

        if(isConfigFileNull) this.setText("");
        else this.setText(String.valueOf(handler.load(this.getName(), handler.elementDir).get("text")));
        this.onSave(new JSONObject());

        this.elementScreen = new GuiElement(new GuiConfiguration(SimpleHUD.getInstance().configGui), this) {

            private GuiTextField inputField;

            @Override
            public void initGui() {
                super.initGui();
                this.inputField = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 105, this.height / 2 + 110, 210, 20);
                this.inputField.setEnableBackgroundDrawing(true);
                this.inputField.setMaxStringLength(128);
                this.inputField.setVisible(true);
                this.inputField.setEnabled(true);
                this.inputField.setCanLoseFocus(false);
                this.inputField.setFocused(true);

            }

            @Override
            public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                super.drawScreen(mouseX, mouseY, partialTicks);
                this.inputField.drawTextBox();
            }

            @Override
            protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
                this.inputField.mouseClicked(mouseX, mouseX, mouseButton);
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }

            @Override
            protected void keyTyped(char typedChar, int keyCode) throws IOException {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
                ((ElementSimpleText) this.element).setText(this.inputField.getText());
                super.keyTyped(typedChar, keyCode);
            }

        };

    }

    @Override
    public void onSetup() {

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir) == null;
        final FileHandler handler = SimpleHUD.getFileHandler();

        if(isConfigFileNull) this.setText("");
        else this.setText(String.valueOf(handler.load(this.getName(), handler.elementDir).get("text")));
        this.onSave(new JSONObject());

        super.onSetup();
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(this.getText());
        this.height = 10 * this.getPosition().getScale();
        super.onRendered(position);
    }

    @Override
    public void onSave(JSONObject obj) {
        obj.put("text", this.text);
        super.onSave(obj);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        final JSONObject obj = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir);
        this.setText(String.valueOf(obj.get("text")));
    }

}