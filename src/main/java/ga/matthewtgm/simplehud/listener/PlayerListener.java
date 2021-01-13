package ga.matthewtgm.simplehud.listener;

import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class PlayerListener {

    private boolean hasCheckedForUpdate = false;

    @SubscribeEvent
    protected void checkForUpdates(EntityJoinWorldEvent event) {
        if(!hasCheckedForUpdate) {
            this.hasCheckedForUpdate = true;
            Thread updateCheckThread = new Thread(() -> {
                if(SimpleHUD.getInstance().getVersionChecker().getVersion().equalsIgnoreCase(Constants.VER)) return;
                EntityPlayerSP player;
                ChatComponentText updateMessage = new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "[" + SimpleHUD.getInstance().getVersionChecker().getVersion() + "]");
                updateMessage.setChatStyle(updateMessage.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SimpleHUD.getInstance().getVersionChecker().getDownloadURL())));
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                player = Minecraft.getMinecraft().thePlayer;
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Your version of " + EnumChatFormatting.GREEN + Constants.NAME + EnumChatFormatting.YELLOW +  " is out of date!\n" + EnumChatFormatting.RED + "Please update: ").appendSibling(updateMessage));
            });
            updateCheckThread.start();
        }
    }

    @SubscribeEvent
    protected void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (SimpleHUD.getInstance().openGuiKeyBinding.isPressed()) Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
    }

}