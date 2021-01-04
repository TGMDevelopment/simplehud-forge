package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

public class ElementMemory extends Element {

    public ElementMemory() {
        super("Memory");
        this.height = 10;
        if(this.prefix == null) this.prefix = "Memory";
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
        this.setRenderedValue(this.bytesToMb(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + "/" + this.bytesToMb(Runtime.getRuntime().maxMemory()) + "MB");
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().x, this.getPosition().y, this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

    private long bytesToMb(long bytes) {
        return bytes / 1024L / 1024L;
    }

}