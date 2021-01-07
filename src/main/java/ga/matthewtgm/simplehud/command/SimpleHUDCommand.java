package ga.matthewtgm.simplehud.command;

import ga.matthewtgm.simplehud.utils.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class SimpleHUDCommand extends CommandBase {

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
        if(!(sender instanceof EntityPlayer)) return;
        new ChatUtils().sendModMessage("Please use the keybind, N");
    }

}
