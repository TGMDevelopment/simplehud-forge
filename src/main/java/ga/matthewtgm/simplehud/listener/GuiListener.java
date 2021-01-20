package ga.matthewtgm.simplehud.listener;

import ga.matthewtgm.simplehud.SimpleHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
<<<<<<< Updated upstream
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
=======
import net.minecraftforge.client.event.GuiOpenEvent;
>>>>>>> Stashed changes
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiListener {

    private static final GuiListener INSTANCE = new GuiListener();
    private boolean addPauseButton;

    public static GuiListener getInstance() {
        return INSTANCE;
    }

    public boolean mustAddPauseButton() {
        return addPauseButton;
    }

    public void setAddPauseButton(boolean addPauseButton) {
        this.addPauseButton = addPauseButton;
    }

    @SubscribeEvent
<<<<<<< Updated upstream
    protected void onGuiInit(GuiScreenEvent.InitGuiEvent event) {
        if(!(event.gui instanceof GuiIngameMenu)) return;
        if(!this.addPauseButton) return;
        event.buttonList.add(new GuiButton(100001, event.gui.width - 105, (Loader.isModLoaded("skyblockaddons") ? event.gui.height - 50 : event.gui.height - 25), 100, 20, "SimpleHUD"));
    }
=======
    protected void onGuiOpen(GuiOpenEvent event) {
        if (!(event.gui instanceof GuiIngameMenu)) return;
        if (!this.addPauseButton) return;
        GuiAppendingManager.getInstance().appendButton(event.gui, new GuiAppendedButton() {

            @Override
            public int getId() {
                return 100001;
            }

            @Override
            public int getX() {
                return this.parent.width / 2 - 50;
            }

            @Override
            public int getY() {
                return this.parent.height - 25;
            }

            @Override
            public String getText() {
                return "SimpleHUD";
            }

            @Override
            public int getWidth() {
                return 100;
            }

            @Override
            public void onClicked() {
                Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
            }
>>>>>>> Stashed changes

    @SubscribeEvent
    protected void onGuiActionPerformed(GuiScreenEvent.ActionPerformedEvent event) {
        if(!(event.gui instanceof GuiIngameMenu)) return;
        if(event.button.id == 100001) Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
    }

}