package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import net.minecraft.client.Minecraft;

public class ElementPing extends Element {

    public ElementPing() {
        super("Ping");
        this.height = 10;
        if(this.prefix == null) this.prefix = "Ping";
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
        boolean isPingNull = String.valueOf(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime()) == null;
        this.setRenderedValue(String.valueOf(isPingNull ? "Unknown" : Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime()));
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

}