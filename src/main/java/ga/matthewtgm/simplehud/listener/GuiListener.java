package ga.matthewtgm.simplehud.listener;

import ga.matthewtgm.simplehud.SimpleHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiListener {

    private static GuiListener INSTANCE = new GuiListener();
    public static GuiListener getInstance() {
        return INSTANCE;
    }

    private boolean addPauseButton;
    public boolean mustAddPauseButton() {
        return addPauseButton;
    }
    public void setAddPauseButton(boolean addPauseButton) {
        this.addPauseButton = addPauseButton;
    }

    @SubscribeEvent
    protected void onGuiInit(GuiScreenEvent.InitGuiEvent event) {
        if(!(event.gui instanceof GuiIngameMenu)) return;
        if(!this.addPauseButton) return;
        event.buttonList.add(new GuiButton(100001, event.gui.width - 105, (Loader.isModLoaded("skyblockaddons") ? event.gui.height - 70 : event.gui.height - 25), 100, 20, "SimpleHUD"));
    }

    @SubscribeEvent
    protected void onGuiActionPerformed(GuiScreenEvent.ActionPerformedEvent event) {
        if(!(event.gui instanceof GuiIngameMenu)) return;
        if(event.button.id == 100001) Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
    }

}