package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import net.minecraft.client.Minecraft;

public class ElementFPS extends Element {

    public ElementFPS() {
        super("FPS");
        if(this.prefix == null) this.prefix = "FPS";
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(String.valueOf(Minecraft.getDebugFPS()));
        this.height = (int) (10 * this.getPosition().getScale());
        super.onRendered(position);
    }

}