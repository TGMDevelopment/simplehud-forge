package ga.matthewtgm.simplehud.listener;

import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.json.simple.JSONObject;
import org.lwjgl.input.Keyboard;

import java.io.File;

//TODO: Properly implement (I'm too tired for this shit lmao)
public class PlayerListener {

    private ChatUtils chatUtils = new ChatUtils();

    public boolean hasLaunched = false;

    @SubscribeEvent
    protected void checkForFirstLaunch(PlayerEvent.PlayerLoggedInEvent event) {

        if(!hasLaunched) {

            boolean isConfigNull = SimpleHUD.getFileHandler().load("Other_Data", SimpleHUD.getFileHandler().otherDir) == null;
            this.hasLaunched = !isConfigNull || (boolean) SimpleHUD.getFileHandler().load("Other_Data", SimpleHUD.getFileHandler().otherDir).get("has_launched");

            this.hasLaunched = true;

            final JSONObject object = new JSONObject();
            object.put("has_launched", this.hasLaunched);
            SimpleHUD.getFileHandler().save("Other_Data", SimpleHUD.getFileHandler().otherDir, object);

            if (!isConfigNull) {
                this.hasLaunched = (boolean) SimpleHUD.getFileHandler().load("Other_Data", SimpleHUD.getFileHandler().otherDir).get("has_launched");
            }

        }

    }

    public void sendHelpMessage() {

        Thread thread = new Thread(() -> {

            try {

                Thread.sleep(5000);

                if(!hasLaunched && Minecraft.getMinecraft().thePlayer != null) {
                    ChatComponentText component1 = new ChatComponentText("It seems this is your first time using " + Constants.NAME + "! To get started press " + Keyboard.getKeyName(SimpleHUD.getInstance().openGuiKeyBinding.getKeyCode()) + " or click ");
                    ChatComponentText component2 = new ChatComponentText(EnumChatFormatting.GREEN + "here!");
                    component2.setChatStyle(component2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/simplehud")));
                    component1.appendSibling(component2);
                    chatUtils.sendModMessage(component1);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

        });
        thread.start();

    }

}