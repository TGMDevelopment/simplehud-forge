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
    public void onRendered() {
        try {
            this.setRenderedValue(String.valueOf(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime()));
        } catch(Exception e) {
            this.setRenderedValue("Unknown");
        }
        this.height = 10 * this.getPosition().getScale();
        super.onRendered();
    }

}