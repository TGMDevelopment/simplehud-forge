package ga.matthewtgm.simplehud.command;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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

        /*  fixed code in-case you don't like the GUI opening on command

            String bind = GameSettings.getKeyDisplayString(SimpleHUD.getInstance().openGuiKeyBinding.getKeyCode());
            // this also can be static if it's a utility method (not all statics are static abuse)
            new ChatUtils().sendModMessage(String.format("Please use the keybind, %s", bind));

        */

        // cause a tick delay on opening
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        // display the GUI and unregister this tick event
        Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
