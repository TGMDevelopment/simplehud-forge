package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

public class ElementMemory extends Element {

    public ElementMemory() {
        super("Memory");
        this.height = 10;
        if(this.prefix == null) this.prefix = "Memory";
        this.elementScreen = new ElementGUI(this);
    }


    @Override
    public void onRendered() {
        this.setRenderedValue(this.bytesToMb(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + "/" + this.bytesToMb(Runtime.getRuntime().maxMemory()) + "MB");
        this.height = 10 * this.getPosition().getScale();
        super.onRendered();
    }

    private long bytesToMb(long bytes) {
        return bytes / 1024L / 1024L;
    }

}