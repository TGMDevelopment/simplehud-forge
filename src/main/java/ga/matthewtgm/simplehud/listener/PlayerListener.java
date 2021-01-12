package ga.matthewtgm.simplehud.listener;

import ga.matthewtgm.simplehud.SimpleHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class PlayerListener {

    @SubscribeEvent
    protected void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (SimpleHUD.getInstance().openGuiKeyBinding.isPressed()) Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
    }

}