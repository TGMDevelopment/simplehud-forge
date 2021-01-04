package ga.matthewtgm.simplehud.command;

import ga.matthewtgm.simplehud.SimpleHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SimpleHudCommand extends CommandBase {

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
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
    }

}
