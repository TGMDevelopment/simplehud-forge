package ga.matthewtgm.simplehud.listener;

import ga.matthewtgm.lib.util.guiscreens.GuiAppendedButton;
import ga.matthewtgm.lib.util.guiscreens.GuiAppendingManager;
import ga.matthewtgm.simplehud.SimpleHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
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
    protected void onGuiOpen(GuiOpenEvent event) {
        if(!(event.gui instanceof GuiIngameMenu)) return;
        if(!this.addPauseButton) return;
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

        });
    }

}