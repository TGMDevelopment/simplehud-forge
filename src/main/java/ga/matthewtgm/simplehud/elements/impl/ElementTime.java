package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ElementTime extends Element {

    public ElementTime() {
        super("Time");
        if (this.prefix == null) prefix = "Time";
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        this.height = (int) (10 * this.getPosition().getScale());
        super.onRendered(position);
    }

}