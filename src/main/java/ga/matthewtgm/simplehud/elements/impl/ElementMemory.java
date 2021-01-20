package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;

public class ElementMemory extends Element {

    public ElementMemory() {
        super("Memory");
        if(this.prefix == null) this.prefix = "Memory";
    }


    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(this.bytesToMb(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + "/" + this.bytesToMb(Runtime.getRuntime().maxMemory()) + "MB");
        this.height = Math.round(10 * this.getPosition().getScale());
        super.onRendered(position);
    }

    private long bytesToMb(long bytes) {
        return bytes / 1024L / 1024L;
    }

}