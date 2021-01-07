package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;

public class ElementBiome extends Element {

    public ElementBiome() {
        super("Biome");
        if(this.prefix == null) this.prefix = "Biome";
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(this.mc.thePlayer.worldObj.getBiomeGenForCoords(this.mc.thePlayer.getPosition()).biomeName);
        this.height = 10 * this.getPosition().getScale();
        super.onRendered();
    }

}