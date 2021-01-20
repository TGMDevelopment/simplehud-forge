package ga.matthewtgm.simplehud.command;

import club.sk1er.mods.core.gui.notification.Notifications;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.utils.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class SimpleHUDCommand extends CommandBase {

    private final String lineDivider = "------------------------------------";

    @Override
    public String getCommandName() {
        return "simplehud";
    }

    public String getFullCommand() {
        return "/" + this.getCommandName();
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
                ChatUtils.getInstance().sendMessage(
                        "\n" +
                                EnumChatFormatting.GOLD +
                                lineDivider +
                                "\n" +
                                EnumChatFormatting.GREEN +
                                this.getFullCommand() + " - Opens GUI\n" +
                                EnumChatFormatting.GREEN +
                                this.getFullCommand() + " info - Copies dev info to your clipboard.\n" +
                                EnumChatFormatting.GREEN +
                                this.getFullCommand() + " saveall - Saves all elements.\n" +
                                EnumChatFormatting.GOLD +
                                lineDivider);
                return;
            }
            if (args[0].equalsIgnoreCase("info")) {
                StringBuilder builder = new StringBuilder();
                builder.append("```md\n");
                builder.append("# Elements\n");
                for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
                    builder.append("[").append(element.getName()).append("][").append("(").append(element.isToggled()).append(")").append(",(X:").append(element.getPosition().getX()).append(",Y:").append(element.getPosition().getY()).append(",S:").append(element.getPosition().getScale()).append(")").append(",(Colour:").append(element.colour.getRGB()).append(")").append("]").append("\n");
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
                Notifications.INSTANCE.pushNotification("SimpleHUD - Misc", "Developer info copied!");
                ChatUtils.getInstance().sendModMessage(EnumChatFormatting.GOLD + "Developer info copied to clipboard!");
                return;
            }
        } catch (Exception ignored) {}
        GuiScreenUtils.getInstance().open(SimpleHUD.getInstance().configGui);
    }

}