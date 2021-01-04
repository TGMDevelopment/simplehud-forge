package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

public class ElementBiome extends Element {

    public ElementBiome() {
        super("Biome");
        this.height = 10;
        if(this.prefix == null) this.prefix = "Biome";
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public String getRenderedString() {
        StringBuilder sb = new StringBuilder();
        if(this.shouldRenderBrackets()) sb.append("[");
        sb.append(prefix);
        sb.append(": ");
        sb.append(this.getRenderedValue());
        if(this.shouldRenderBrackets()) sb.append("]");
        return sb.toString();
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(this.mc.thePlayer.worldObj.getBiomeGenForCoords(this.mc.thePlayer.getPosition()).biomeName);
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

}