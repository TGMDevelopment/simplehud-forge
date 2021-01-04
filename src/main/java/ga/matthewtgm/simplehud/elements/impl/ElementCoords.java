package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

public class ElementCoords extends Element {

    public ElementCoords() {
        super("Coords");
        this.height = 10;
        this.elementScreen = new ElementGUI(this);
        if (this.prefix == null) prefix = "Coords";
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
        this.setRenderedValue(Math.round(this.mc.thePlayer.posX) + ", " + Math.round(this.mc.thePlayer.posY) + ", " + Math.round(this.mc.thePlayer.posZ));
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

}