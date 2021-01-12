package ga.matthewtgm.simplehud.command;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class SimpleHUDCommand extends CommandBase {

    private final String lineDivider = "------------------------------------";

    @Override
    public String getCommandName() {
        return "simplehud";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("help")) {
                ChatUtils.getInstance().sendMessage("\n" + EnumChatFormatting.GOLD + lineDivider + "\n" + EnumChatFormatting.GREEN + "default - Opens GUI\n" + EnumChatFormatting.GREEN + "/info - Copies dev info to your clipboard.\n" + EnumChatFormatting.GOLD + lineDivider);
                return;
            }
            if (args[0].equalsIgnoreCase("info")) {
                StringBuilder builder = new StringBuilder();
                builder.append("```md\n");
                builder.append("# Elements\n");
                for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
                    builder.append("[").append(element.getName()).append("][").append("(").append(element.isToggled()).append(")").append(",(X:").append(element.getPosition().getX()).append(",Y:").append(element.getPosition().getY()).append(",S:").append(element.getPosition().getScale()).append(")").append(",(Colour:").append(element.colour.getHex()).append(")").append("]").append("\n");
                }
                if (Loader.instance().getActiveModList().size() <= 15) {
                    builder.append("\n# Mods Loaded").append("\n");
                    for (ModContainer modContainer : Loader.instance().getActiveModList()) {
                        builder.append("[").append(modContainer.getName()).append("]")
                                .append("[").append(modContainer.getSource().getName()).append("]\n");
                    }
                }
                builder.append("```");
                StringSelection clipboard = new StringSelection(builder.toString());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard, clipboard);
                ChatUtils.getInstance().sendModMessage(EnumChatFormatting.GOLD + "Developer info copied to clipboard!");
                return;
            }
        } catch (Exception ignored) {
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    protected void onTick(TickEvent.ClientTickEvent event) {
        Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
