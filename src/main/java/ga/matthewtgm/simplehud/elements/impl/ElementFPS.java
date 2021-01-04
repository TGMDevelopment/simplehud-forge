package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import net.minecraft.client.Minecraft;

public class ElementFPS extends Element {

    public ElementFPS() {
        super("FPS");
        this.height = 10;
        if(this.prefix == null) this.prefix = "FPS";
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public String getRenderedString() {
        StringBuilder sb = new StringBuilder();
        if(this.shouldRenderBrackets()) sb.append("[");
        sb.append(this.prefix);
        sb.append(": ");
        sb.append(this.getRenderedValue());
        if(this.shouldRenderBrackets()) sb.append("]");
        return sb.toString();
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(String.valueOf(Minecraft.getDebugFPS()));
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().x, this.getPosition().y, this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

}