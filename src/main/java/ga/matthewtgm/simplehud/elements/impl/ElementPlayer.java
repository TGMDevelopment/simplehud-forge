package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import ga.matthewtgm.simplehud.gui.GuiElement;
import net.minecraft.client.gui.inventory.GuiInventory;

/**
 * @author Filip
 */
public class ElementPlayer extends Element {

    public ElementPlayer() {
        super("Player");
        this.width = 64;
        this.height = 64;
        this.elementScreen = new GuiElement(new GuiConfiguration(SimpleHUD.getInstance().configGui), this) {

            @Override
            public void initGui() {
                super.initGui();
                this.buttonList.remove(this.showBrackets);
                this.buttonList.remove(this.showPrefix);
                this.buttonList.remove(this.chromaToggle);
                this.buttonList.remove(this.rSlider);
                this.buttonList.remove(this.gSlider);
                this.buttonList.remove(this.bSlider);
            }

        };
    }

    //TODO Fix gui saying null
    @Override
    public void onRendered(ElementPosition position) {
        if (mc.thePlayer != null) {
            GuiInventory.drawEntityOnScreen(position.x, position.y, (int) position.scale * 5, mc.thePlayer.rotationYaw,
                mc.thePlayer.rotationPitch, mc.thePlayer);
        }
    }
}
