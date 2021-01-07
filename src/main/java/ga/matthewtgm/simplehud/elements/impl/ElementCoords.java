package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

public class ElementCoords extends Element {

    public ElementCoords() {
        super("Coords");
        this.elementScreen = new ElementGUI(this);
        if (this.prefix == null) prefix = "Coords";
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(Math.round(this.mc.thePlayer.posX) + ", " + Math.round(this.mc.thePlayer.posY) + ", " + Math.round(this.mc.thePlayer.posZ));
        this.height = 10 * this.getPosition().getScale();
        super.onRendered();
    }

}