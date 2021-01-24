package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;

public class ElementDay extends Element {

    public ElementDay() {
        super("Day", "General");
        if (this.prefix == null) this.prefix = "Day";
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(String.valueOf(Long.valueOf(this.mc.theWorld.getWorldTime() / 24000L)));
        this.height = Math.round(10 * this.getPosition().getScale());
        super.onRendered(position);
    }

}