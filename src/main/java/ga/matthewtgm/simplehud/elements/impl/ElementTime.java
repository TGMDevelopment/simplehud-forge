package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ElementTime extends Element {

    public ElementTime() {
        super("Time");
        this.height = 10;
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public String getRenderedString() {
        StringBuilder sb = new StringBuilder();
        if(this.shouldRenderBrackets()) sb.append("[");
        if(this.prefix != null) {
            sb.append(this.prefix);
            sb.append(": ");
        }
        sb.append(this.getRenderedValue());
        if(this.shouldRenderBrackets()) sb.append("]");
        return sb.toString();
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(this.getTime());
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

    private String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        return df.format(new Date());
    }

}
