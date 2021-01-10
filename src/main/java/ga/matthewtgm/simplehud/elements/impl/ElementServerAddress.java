package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;

public class ElementServerAddress extends Element {

    public ElementServerAddress() {
        super("Server Address");
        if(this.prefix == null) this.prefix = "Server";
        this.elementScreen = new ElementGUI(this) {};
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(mc.getCurrentServerData() == null ? "Singleplayer" : mc.getCurrentServerData().serverIP);
        this.height = 10;
        super.onRendered(position);
    }

}