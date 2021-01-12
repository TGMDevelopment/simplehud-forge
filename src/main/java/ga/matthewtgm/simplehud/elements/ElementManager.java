package ga.matthewtgm.simplehud.elements;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ElementManager {

    private final List<Element> elements = new ArrayList<>();

    public void init() {
        this.getElements().add(new ElementBiome());
        this.getElements().add(new ElementCoords());
        this.getElements().add(new ElementCPS());
        this.getElements().add(new ElementDay());
        this.getElements().add(new ElementFPS());
        this.getElements().add(new ElementMemory());
        this.getElements().add(new ElementPing());
        this.getElements().add(new ElementReachDisplay());
        this.getElements().add(new ElementTime());
        this.getElements().add(new ElementServerAddress());
        this.getElements().add(new ElementArmourHUD());
        this.getElements().add(new ElementPotionEffects());
        this.getElements().add(new ElementSimpleText());
        this.getElements().add(new ElementComboDisplay());

        MinecraftForge.EVENT_BUS.register(this);
        for(Element element : this.getElements()) {
            if(element.isToggled()) element.onEnabled();
            else element.onDisabled();
        }
    }

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
        if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
            for (Element element : this.getElements()) {
                if (SimpleHUD.getInstance().isToggled() && Minecraft.getMinecraft().currentScreen == null && element.isToggled() && Minecraft.getMinecraft().thePlayer != null)
                    element.onRendered(element.getPosition());
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
        }
    }

    public List<Element> getElements() {
        return elements;
    }

}