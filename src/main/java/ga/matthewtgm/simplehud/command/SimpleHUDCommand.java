package ga.matthewtgm.simplehud.command;

import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

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
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    protected void onTick(TickEvent.ClientTickEvent event) {
        Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
