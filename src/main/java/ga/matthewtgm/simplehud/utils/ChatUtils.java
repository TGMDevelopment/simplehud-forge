package ga.matthewtgm.simplehud.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils {

    // This is not how you make an instance
    //private static ChatUtils INSTANCE = new ChatUtils();
    private static ChatUtils INSTANCE;
    public String PREFIX = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.RED + "SimpleHUD" + EnumChatFormatting.GRAY + "]";

    public static ChatUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatUtils();
        }
        return INSTANCE;
    }

    public void sendMessage(String msg) {
        if (msg == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    public void sendModMessage(String msg) {
        if (msg == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(PREFIX + EnumChatFormatting.RESET + " " + msg));
    }

    public void sendModMessage(ChatComponentText text) {
        if (text == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        ChatComponentText chatComponent = new ChatComponentText(PREFIX + EnumChatFormatting.RESET + " ");
        chatComponent.appendSibling(text);
        Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
    }

}