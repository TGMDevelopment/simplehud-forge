package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ElementTime extends Element {

    public ElementTime() {
        super("Time");
        this.height = 10;
        this.elementScreen = new ElementGUI(this);
        if (this.prefix == null) prefix = "Time";
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        this.height = 10 * this.getPosition().getScale();
        super.onRendered();
    }

}