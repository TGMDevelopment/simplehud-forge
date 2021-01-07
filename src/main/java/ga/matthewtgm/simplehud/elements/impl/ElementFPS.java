package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import net.minecraft.client.Minecraft;

public class ElementFPS extends Element {

    public ElementFPS() {
        super("FPS");
        if(this.prefix == null) this.prefix = "FPS";
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(String.valueOf(Minecraft.getDebugFPS()));
        this.height = 10 * this.getPosition().getScale();
        super.onRendered();
    }

}