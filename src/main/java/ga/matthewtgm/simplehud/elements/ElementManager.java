package ga.matthewtgm.simplehud.elements;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.impl.*;
import ga.matthewtgm.simplehud.elements.impl.ElementFPS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ElementManager {

    private final List<Element> elements = new ArrayList<>();

    private boolean showInChat;

    public boolean isShowInChat() {
        return showInChat;
    }

    public void setShowInChat(boolean showInChat) {
        this.showInChat = showInChat;
    }

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
        this.getElements().add(new ElementPlayerView());

        MinecraftForge.EVENT_BUS.register(this);
        for (Element element : this.getElements()) {
            if (element.isToggled()) element.onEnabled();
            else element.onDisabled();
        }
    }

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            for (Element element : this.getElements()) {
                if (SimpleHUD.getInstance().isToggled() && (Minecraft.getMinecraft().currentScreen == null || this.showInChat()) && element.isToggled() && Minecraft.getMinecraft().thePlayer != null)
                    element.onRendered(element.getPosition());
            }
        }
    }

    private boolean showInChat() {
        return (this.isShowInChat() && Minecraft.getMinecraft().currentScreen instanceof GuiChat);
    }

    public <T extends Element> T getElement(Class<T> elementClass) {
        for (Element element : this.getElements()) {
            if (elementClass.isAssignableFrom(element.getClass())) {
                return (T) element;
            }
        }
        return null;
    }

    public List<Element> getElements() {
        return elements;
    }

}