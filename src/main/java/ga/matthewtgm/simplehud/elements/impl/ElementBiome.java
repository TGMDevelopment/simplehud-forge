package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;

public class ElementBiome extends Element {

    public ElementBiome() {
        super("Biome");
        if(this.prefix == null) this.prefix = "Biome";
    }

    @Override
    public void onRendered(ElementPosition position) {
        this.setRenderedValue(this.mc.thePlayer.worldObj.getBiomeGenForCoords(this.mc.thePlayer.getPosition()).biomeName);
        this.height = Math.round(10 * this.getPosition().getScale());
        super.onRendered(position);
    }

}