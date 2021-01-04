package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ElementTime extends Element {

    public ElementTime() {
        super("Time");
        this.height = 10;
        this.elementScreen = new ElementGUI(this);
        if (this.prefix == null) prefix = "Time";
    }

    @Override
    public String getRenderedString() {
        StringBuilder sb = new StringBuilder();
        if (shouldRenderBrackets()) sb.append("[");
        if (this.prefix != null) sb.append(this.prefix).append(": ");
        sb.append(getRenderedValue());
        if (shouldRenderBrackets()) sb.append("]");
        return sb.toString();
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

}