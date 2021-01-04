package ga.matthewtgm.simplehud.elements;

import ga.matthewtgm.simplehud.elements.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class ElementManager {

    private final List<Element> elements = Arrays.asList(
            new ElementFPS(),
            new ElementBiome(),
            new ElementCPS(),
            new ElementPing(),
            new ElementDay(),
            new ElementMemory(),
            new ElementReachDisplay(),
            new ElementTime()
    );

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        for(Element element : this.getElements()) {
            if(element.isToggled()) element.onEnabled();
            else element.onDisabled();
        }
    }

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent event) {
        for(Element element : this.getElements()) {
            if(Minecraft.getMinecraft().currentScreen == null && element.isToggled()) element.onRendered();
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
    }

    public List<Element> getElements() {
        return elements;
    }

}