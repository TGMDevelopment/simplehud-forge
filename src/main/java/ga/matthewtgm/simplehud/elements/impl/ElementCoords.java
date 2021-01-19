package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;

public class ElementCoords extends Element {

    public ElementCoords() {
        super("Coords");
        if (this.prefix == null) prefix = "Coords";
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(Math.round(this.mc.thePlayer.posX) + ", " + Math.round(this.mc.thePlayer.posY) + ", " + Math.round(this.mc.thePlayer.posZ));
        this.height = (int) (10 * this.getPosition().getScale());
        super.onRendered(position);
    }

}